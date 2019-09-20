package de.oc.munic.expertcamp.tdd;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BowlingGameCalculator {
	private static final int STRIKE_SECOND_NEXT_ROLL = 3;
	private static final int STRIKE_NEXT_ROLL = 2;
	private static final int NO_MATCH = -1;
	private static final int STRIKE_FRAME_SEPARATOR_MATCH = 1;
	private static final String SPARE_MARK = "/";
	private static final String FRAME_SEPARATOR = " ";
	private static final String STRIKE_MARK = "X";
	private static final String MISS_MARK = "-";
	private static final int FRAME_SEPARATOR_MATCH = 2;
	private static final int NEXT_ROLL_AFTER_PARE = 3;
	private static final int FIRST_ROLL_IN_SPARE = 1;
	private static final int ALL_PINS_SCORE = 10;
	private static final int INITIAL_SCORE = 0;
	private static final String NO_POINTS = "0";
	private static final Pattern SINGLE_ROLL_PATTERN = Pattern.compile("(\\d)");
	private static final Pattern SPARE_PATTERN = Pattern.compile("(\\d)/( )?(\\d)?");
	private static final Pattern STRIKE_PATTERN = Pattern.compile("X( )?(\\d|[X-])?(\\d|[/X-])?");

	static class FrameTypeScorer {
		private final BiFunction<Matcher, Integer, Boolean> frameScoreMatch;
		private final Function<Matcher, Integer> frameScoreCalculator;
		private final Function<Matcher, Integer> frameScoreReseter;
		private int matcherPosition = 0;
		private Matcher frameTypeMatcher;
		private Pattern frameTypePattern;

		public FrameTypeScorer(Pattern frameTypePattern, BiFunction<Matcher, Integer, Boolean> frameScoreMatch,
				Function<Matcher, Integer> frameScoreCalculator, Function<Matcher, Integer> frameScoreReseter) {
			super();
			this.frameTypePattern = frameTypePattern;
			this.frameScoreMatch = frameScoreMatch;
			this.frameScoreCalculator = frameScoreCalculator;
			this.frameScoreReseter = frameScoreReseter;
		}

		boolean hasNext() {
			return frameScoreMatch.apply(frameTypeMatcher, matcherPosition);
		}

		int calculateScore() {
			Integer score = frameScoreCalculator.apply(frameTypeMatcher);
			matcherPosition = frameScoreReseter.apply(frameTypeMatcher);
			return score;
		}

		int calculateFrameTypeScore(String rolls) {
			frameTypeMatcher = frameTypePattern.matcher(rolls);
			int gameScore = 0;
			while (hasNext()) {
				gameScore += calculateScore();
			}
			return gameScore;
		}
	}

	public int validate(String rolls) {
		int gameScore = INITIAL_SCORE;

		FrameTypeScorer frameTypeScorer = new FrameTypeScorer(//
				SINGLE_ROLL_PATTERN, //
				(rollScore, matchPosition) -> rollScore.find(), //
				(rollScore) -> Integer.parseInt(rollScore.group()), //
				(rollScore) -> 0);
		gameScore = frameTypeScorer.calculateFrameTypeScore(rolls);

		frameTypeScorer = new FrameTypeScorer(SPARE_PATTERN, //
				(spareScore, matchPosition1) -> spareScore.find(matchPosition1),
				(spareScore) -> Integer
						.parseInt(Optional.ofNullable(spareScore.group(NEXT_ROLL_AFTER_PARE)).orElse(NO_POINTS))
						+ (ALL_PINS_SCORE - Integer.parseInt(spareScore.group(FIRST_ROLL_IN_SPARE))),
				(spareScore) -> FRAME_SEPARATOR.equals(spareScore.group(FRAME_SEPARATOR_MATCH))
						? spareScore.start(FRAME_SEPARATOR_MATCH)
						: spareScore.end());
		gameScore += frameTypeScorer.calculateFrameTypeScore(rolls);

		Matcher strikeScore = STRIKE_PATTERN.matcher(rolls);
		int restartPos = 0;
		while (strikeScore.find(restartPos)) {
			String nextRoll = Optional.ofNullable(strikeScore.group(STRIKE_NEXT_ROLL)).orElse(NO_POINTS);
			nextRoll = STRIKE_MARK.equals(nextRoll) ? String.valueOf(ALL_PINS_SCORE) : nextRoll;
			nextRoll = MISS_MARK.equals(nextRoll) ? NO_POINTS : nextRoll;

			String secondNextRoll = Optional.ofNullable(strikeScore.group(STRIKE_SECOND_NEXT_ROLL)).orElse(NO_POINTS);
			secondNextRoll = STRIKE_MARK.equals(secondNextRoll) ? String.valueOf(ALL_PINS_SCORE) : secondNextRoll;
			secondNextRoll = MISS_MARK.equals(secondNextRoll) ? NO_POINTS : secondNextRoll;
			secondNextRoll = SPARE_MARK.equals(secondNextRoll)
					? String.valueOf(ALL_PINS_SCORE - Integer.parseInt(nextRoll))
					: nextRoll;

			gameScore += ALL_PINS_SCORE + Integer.parseInt(nextRoll) + Integer.parseInt(secondNextRoll);

			restartPos = NO_MATCH == strikeScore.start(STRIKE_FRAME_SEPARATOR_MATCH) ? strikeScore.end()
					: strikeScore.start(STRIKE_FRAME_SEPARATOR_MATCH);
		}
		return gameScore;
	}

}

package de.oc.munic.expertcamp.tdd;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BowlingGameCalculator {
	private static final String FRAME_SEPARATOR = " ";
	private static final int FRAME_SEPARATOR_MATCH = 2;
	private static final int NEXT_ROLL_AFTER_PARE = 3;
	private static final int FIRST_ROLL_IN_SPARE = 1;
	private static final int ALL_PINS_SCORE = 10;
	private static final int INITIAL_SCORE = 0;
	private static final String NO_POINTS = "0";
	private static final Pattern SINGLE_ROLL_PATTERN = Pattern.compile("(\\d)");
	private static final Pattern SPARE_PATTERN = Pattern.compile("(\\d)/( )?(\\d)?");

	
	public int validate(String rolls) {
		Matcher rollScore = SINGLE_ROLL_PATTERN.matcher(rolls);
		int gameScore = INITIAL_SCORE;
		while (rollScore.find()) {
			gameScore += Integer.parseInt(rollScore.group());
		}
		Matcher spareScore = SPARE_PATTERN.matcher(rolls);
		int restartPos = 0;
		while (spareScore.find(restartPos)) {
			gameScore += Integer.parseInt(Optional.ofNullable(spareScore.group(NEXT_ROLL_AFTER_PARE)).orElse(NO_POINTS))
					+ (ALL_PINS_SCORE - Integer.parseInt(spareScore.group(FIRST_ROLL_IN_SPARE)));
			restartPos = FRAME_SEPARATOR.equals(spareScore.group(FRAME_SEPARATOR_MATCH))
					? spareScore.start(FRAME_SEPARATOR_MATCH)
					: spareScore.end();
		}

		return gameScore;
	}

}

package de.oc.munic.expertcamp.tdd;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BowlingGameCalculator {
	private static final int INITIAL_SCORE = 0;
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
			gameScore += Integer.parseInt(Optional.ofNullable(spareScore.group(3)).orElse("0"))
					+ (10 - Integer.parseInt(spareScore.group(1)));
			restartPos = " ".equals(spareScore.group(2)) ? spareScore.start(2) : spareScore.end();
		}

		return gameScore;
	}

}

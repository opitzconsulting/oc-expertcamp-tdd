package de.oc.munic.expertcamp.tdd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BowlingGameCalculator {
	private static final int INITIAL_SCORE = 0;
	private static final Pattern SINGLE_ROLL_PATTERN = Pattern.compile("(\\d)");

	public int validate(String rolls) {
		Matcher rollScore = SINGLE_ROLL_PATTERN.matcher(rolls);
		int gameScore = INITIAL_SCORE;
		while (rollScore.find()) {
			gameScore += Integer.parseInt(rollScore.group());
		}
		return gameScore;
	}

}

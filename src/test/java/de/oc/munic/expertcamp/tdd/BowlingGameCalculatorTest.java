package de.oc.munic.expertcamp.tdd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BowlingGameCalculatorTest {

	private BowlingGameCalculator testDummy;

	@BeforeEach
	void setUp() throws Exception {
		testDummy = new BowlingGameCalculator();
	}

	@Test
	void worstGameHasNoPoints() {
		// arrange
		String rollsAllMiss = "-- -- -- -- -- -- -- -- -- --";
		int expectedSore = 0;

		// act
		int score = testDummy.validate(rollsAllMiss);

		// assert
		assertEquals(expectedSore, score, String.format("result of worst game (%s)", rollsAllMiss));

	}

	@Test
	void incpompleteFramesSumUpSingleRollResults() {
		// arrange
		String incompleteFrames = "9- 9- 9- 9- 9- 9- 9- 9- 9- 9-";
		int expectedSore = 90;

		// act
		int score = testDummy.validate(incompleteFrames);

		// assert
		assertEquals(expectedSore, score, String.format("result of incomplete frames (%s)", incompleteFrames));

	}
	@Test
	void spareCountNextRollTwice() {
		// arrange
		String allSpares = "5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5";
		int expectedScore = 155;

		// act
		int score = testDummy.validate(allSpares);

		// assert
		assertEquals(expectedScore, score, String.format("result of all spares (%s)", allSpares));
	}

}

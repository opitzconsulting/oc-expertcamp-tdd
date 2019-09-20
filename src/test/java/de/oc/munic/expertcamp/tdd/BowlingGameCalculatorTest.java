package de.oc.munic.expertcamp.tdd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BowlingGameCalculatorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void worstGameHasNoPoints() {
		// arrange
		String rollsAllMiss = "-- -- -- -- -- -- -- -- -- --";
		int expectedSore = 0;

		// act
		int score = new BowlingGameCalculator().validate(rollsAllMiss);

		// assert
		assertEquals(expectedSore, score, String.format("result of worst game (%s)", rollsAllMiss));

	}

}

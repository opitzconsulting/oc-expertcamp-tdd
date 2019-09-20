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

		// act
		int score = new BowlingGameCalculator().validate(rollsAllMiss);

		// assert
		
	}

}

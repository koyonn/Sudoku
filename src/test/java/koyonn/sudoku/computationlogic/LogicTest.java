package koyonn.sudoku.computationlogic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogicTest {
	private static final Logger logger = LoggerFactory.getLogger(LogicTest.class);
	private static final int[][] ARRAY_WITH_NULLS = { { 7, 0, 8, 0, 4, 9, 0, 5, 6 }, { 3, 0, 5, 2, 6, 1, 0, 4, 8 },
	                                                  { 6, 1, 4, 8, 5, 7, 0, 0, 3 }, { 2, 4, 3, 0, 7, 6, 8, 0, 9 },
	                                                  { 9, 7, 6, 1, 0, 8, 4, 2, 5 }, { 8, 5, 1, 9, 2, 4, 3, 6, 0 },
	                                                  { 4, 0, 9, 6, 8, 2, 0, 7, 1 }, { 5, 6, 2, 7, 1, 3, 0, 8, 4 },
	                                                  { 1, 8, 7, 0, 9, 5, 6, 3, 0 } };
	private static final int[][] ARRAY_WRONG = { { 7, 0, 8, 0, 4, 9, 0, 7, 0 }, { 3, 0, 5, 0, 6, 1, 0, 4, 0 },
	                                             { 6, 0, 4, 0, 5, 7, 0, 0, 3 }, { 2, 0, 3, 0, 7, 0, 8, 0, 9 },
	                                             { 0, 7, 0, 1, 0, 8, 0, 2, 0 }, { 0, 5, 0, 9, 0, 4, 0, 6, 0 },
	                                             { 4, 0, 9, 6, 0, 2, 0, 7, 0 }, { 5, 0, 2, 0, 1, 3, 0, 8, 0 },
	                                             { 0, 8, 7, 0, 9, 0, 6, 3, 0 } };

	@Test
	public void puzzleIsSolvableTest() {
		logger.debug("=================== puzzleIsSolvable ===================");
		assertEquals(true, SudokuSolver.puzzleIsSolvable(ARRAY_WITH_NULLS));
		logger.debug("=================== puzzleIsSolvable ===================");
		assertEquals(false, SudokuSolver.puzzleIsSolvable(ARRAY_WRONG));
	}

	@Test
	public void sudokuValidationTest() {
		logger.debug("================= sudokuValidationTest =================");
		assertEquals(false, GameLogic.sudokuValidation(2, 0, 0, 1, 0, ARRAY_WITH_NULLS));
		logger.debug("================= sudokuValidationTest =================");
		assertEquals(true, GameLogic.sudokuValidation(1, 0, 0, 1, 0, ARRAY_WITH_NULLS));
	}
}

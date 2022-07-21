package koyonn.sudoku.computationlogic;

import koyonn.sudoku.constants.GameState;
import koyonn.sudoku.problemdomain.SudokuGame;

import static koyonn.sudoku.computationlogic.GameGenerator.getSudokuIsValid;
import static koyonn.sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameLogic {

    /**
     * Получить новое поле судоку
     *
     * @return поле судоку
     */
    public static SudokuGame getNewGame() {
        int[][] array = GameGenerator.getNewGameGrid();
        return new SudokuGame(
                GameState.NEW,
                array,
                array);
    }

    /**
     * Проверка завершена ли игра
     *
     * @param grid поле судоку
     * @return состояние игры
     */
    public static GameState checkForCompletion(int[][] grid) {
        // Проверка на правильно заполнения
        if (getSudokuIsValid()) {
            return GameState.ACTIVE;
        }
        // Проверка на наличие пустых ячеек
        if (tilesAreNotFilled(grid)) {
            return GameState.ACTIVE;
        }
        return GameState.COMPLETE;
    }

    /**
     * Метод для проверки правильно заполнения судоку
     *
     * @param number       сгенерированное число
     * @param rowIndex     индекс ряда
     * @param rowOffset    отступ по ряду
     * @param columnIndex  отступ по столбцу
     * @param columnOffset индекс столбца
     * @param sudoku       массив судоку
     * @return true - если есть совпадение
     */
    public static boolean sudokuValidation(int number,
                                           int rowIndex,
                                           int rowOffset,
                                           int columnIndex,
                                           int columnOffset,
                                           int[][] sudoku) {
        // Проверка блока 3х3
        if (blockCheck(number, rowOffset, columnOffset, sudoku)) {
            return true;
        }
        // Проверка ряда
        if (rowCheck(number, rowIndex, sudoku)) {
            return true;
        }
        // Проверка столбца
        if (columnCheck(number, columnIndex, sudoku)) {
            return true;
        }
        // Если заполнено правильно и нет совпадений
        return false;
    }

    /**
     * Метод для проверки совпадений сгенерированного числа с уже записанными
     * числами в блоке 3х3.
     *
     * @param number       сгенерированное число
     * @param rowOffset    отступ по ряду
     * @param columnOffset отступ по столбцу
     * @return true - если есть совпадение
     */
    private static boolean blockCheck(int number, int rowOffset, int columnOffset, int[][] sudoku) {
        for (int row = 3 * rowOffset; row < 3 * rowOffset + 3; row++) {
            for (int column = 3 * columnOffset; column < 3 * columnOffset + 3; column++) {
                if (sudoku[row][column] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод для проверки совпадений сгенерированного числа с уже записанными
     * числами в указанном ряду.
     *
     * @param number   сгенерированное число
     * @param rowIndex индекс ряда
     * @return true - если есть совпадение
     */
    private static boolean rowCheck(int number, int rowIndex, int[][] sudoku) {
        for (int column = 0; column < sudoku.length; column++) {
            if (sudoku[rowIndex][column] == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод для проверки совпадений сгенерированного числа с уже записанными
     * числами в указанном столбце.
     *
     * @param number      сгенерированное число
     * @param columnIndex индекс столбца
     * @return true - если есть совпадение
     */
    private static boolean columnCheck(int number, int columnIndex, int[][] sudoku) {
        for (int row = 0; row < sudoku.length; row++) {
            if (sudoku[row][columnIndex] == number) {
                return true;
            }
        }
        return false;
    }


    /**
     * Метод, для проверки на наличие в массиве-судоку нулевого значения.
     *
     * @param sudoku массива-судоку
     * @return true - если хоть в одной ячейке значение - 0.
     */
    public static boolean nullCheck(int[][] sudoku) {
        for (int row = 0; row < sudoku.length; row++) {
            for (int column = 0; column < sudoku.length; column++) {
                if (sudoku[row][column] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод, предназначенный для обнуления определенных ячеек. При заполнении
     * массива случается, что в указанную ячейку невозможно добавить число -
     * есть совпадения как по вертикали так, например, и в блоке. Тогда, в
     * зависимости от индекса текущей ячейки, ближайшим ячейкам присваивается
     * нулевое значение, и затем заполнение массива продолжается.
     *
     * @param rowIndex    индекс ряда
     * @param columnIndex индекс столбца
     */
    public static int[][] resetCells(int rowIndex, int columnIndex, int[][] sudoku) {
        if (columnIndex > 0 && columnIndex < 8) {
            sudoku[rowIndex][columnIndex - 1] = 0;
            sudoku[rowIndex][columnIndex + 1] = 0;
        } else if (columnIndex == 8) {
            sudoku[rowIndex][columnIndex - 1] = 0;
        } else {
            sudoku[rowIndex][columnIndex + 1] = 0;
        }
        if (rowIndex > 0 && rowIndex < 8) {
            sudoku[rowIndex - 1][columnIndex] = 0;
            sudoku[rowIndex + 1][columnIndex] = 0;
        } else if (rowIndex == 8) {
            sudoku[rowIndex - 1][columnIndex] = 0;
        } else {
            sudoku[rowIndex + 1][columnIndex] = 0;
        }
        return sudoku;
    }

    /**
     * Проверка на заполненность ячеек поля судоку
     *
     * @param grid поле судоку
     * @return true - если до конца не заполнено
     */
    private static boolean tilesAreNotFilled(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                if (grid[xIndex][yIndex] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}

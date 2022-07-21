package koyonn.sudoku.computationlogic;

import koyonn.sudoku.problemdomain.SudokuGame;

/**
 * Utility class.
 */
public class SudokuUtilities {
    /**
     * Статический метод, который копирует значения из старого массива в новый
     *
     * @param oldArray старый массив
     * @param newArray новый массив
     */
    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray) {
        for (int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++) {
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
    }

    /**
     * Статический метод, который возвращает новый массив со значениями из старого
     *
     * @param oldArray старый массив
     * @return новый массив
     */
    public static int[][] copyToNewArray(int[][] oldArray) {
        int[][] newArray = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];
        for (int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++) {
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
        return newArray;
    }
}

package koyonn.sudoku.computationlogic;

import java.util.Random;

import static koyonn.sudoku.computationlogic.GameLogic.sudokuValidation;
import static koyonn.sudoku.computationlogic.GameLogic.nullCheck;
import static koyonn.sudoku.computationlogic.GameLogic.resetCells;
import static koyonn.sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameGenerator {

    /**
     * Получить новую сгенерированную сетку судоку
     *
     * @return сетка судоку для заполнения игроком
     */
    public static int[][] getNewGameGrid() {
        return unsolvedGame(creatingSudoku());
    }

    /**
     * Метод для генерации судоку с незаполненными полями
     *
     * @param solvedGame заполненное судоку
     * @return судоку с пустыми ячейками, которое будет заполнять игрок
     */
    private static int[][] unsolvedGame(int[][] solvedGame) {
        boolean solvable = false;
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        // Попытки сгенерировать судоку до тех пор, пока оно не пройдет проверки
        // на решаемость
        while (!solvable) {
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);
            int count = 0;
            int numberOfEmptyCells = 0;
            // Генерироваться пустые ячейки будут по следующем принципу:
            // В каждом блоке судоку пустых ячеек должно быть не менее 3
            // и не более 6.
            for (int row = 0; row < GRID_BOUNDARY; row += 3) {
                for (int column = 0; column < GRID_BOUNDARY; column += 3) {
                    numberOfEmptyCells = getRandomNumber(3, 7);
                    while (count < numberOfEmptyCells) {
                        int xCoordinate = getRandomNumber(row, row + 3);
                        int yCoordinate = getRandomNumber(column, column + 3);
                        if (solvableArray[xCoordinate][yCoordinate] != 0) {
                            solvableArray[xCoordinate][yCoordinate] = 0;
                            count++;
                        }
                    }
                    count = 0;
                }
            }
            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);
            // Выполнение проверки на решаемость
            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
        }
        return solvableArray;
    }

    // true - если судоку сгенерировано и прошло все проверки
    private static boolean sudokuIsValid = false;

    // Геттер для sudokuIsValid
    public static boolean getSudokuIsValid() {
        return sudokuIsValid;
    }

    /**
     * Генерация полностью и правильно заполненной сетки судоку
     *
     * @return полностью заполненное судоку
     */
    private static int[][] creatingSudoku() {
        int[][] sudokuArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        int rowOffset = 0;
        int columnOffset = 0;
        int rowIndex = 0;
        int columnIndex = 0;
        // Цикл будет выполняться до тех пор, пока массив-судоку
        // не будет заполнен числами.
        while (nullCheck(sudokuArray)) {
            boolean checkIndex = true;
            // Цикл будет выполняться до тех пор, пока не будет выбрана
            // ячейка массива-судоку со значением 0.
            while (checkIndex) {
                rowIndex = getRandomNumber(0, GRID_BOUNDARY);
                columnIndex = getRandomNumber(0, GRID_BOUNDARY);
                rowOffset = rowIndex / 3;
                columnOffset = columnIndex / 3;
                if (sudokuArray[rowIndex][columnIndex] == 0) {
                    checkIndex = false;
                }
            }
            boolean checkNumber = true;
            // Отражается количество попыток записи числа в ячейку.
            int count = 0;
            // Цикл будет выполняться до тех пор, пока сгенерированное число
            // не будет записано в выбранную ячейку массива-судоку.
            while (checkNumber) {
                // Сгенерированное число
                int number = getRandomNumber(1, 10);
                count++;
                // Проверка на совпадение чисел
                if (!sudokuValidation(
                        number,
                        rowIndex,
                        rowOffset,
                        columnIndex,
                        columnOffset,
                        sudokuArray)) {
                    sudokuArray[rowIndex][columnIndex] = number;
                    checkNumber = false;
                }
                // Если количество попыток записи достигнет указанного значения, то
                // будет вызван метод для обнуления некоторых ячеек.
                if (count == 15) {
                    sudokuArray = resetCells(rowIndex, columnIndex, sudokuArray);
                    checkNumber = false;
                }
            }
        }
        sudokuIsValid = true;
        return sudokuArray;
    }

    /**
     * Получение случайно сгенерированного числа
     *
     * @param beginIndex начальный индекс
     * @param endIndex   конечный индекс
     * @return случайное число
     */
    private static int getRandomNumber(int beginIndex, int endIndex) {
        return new Random().nextInt(beginIndex, endIndex);
    }
}

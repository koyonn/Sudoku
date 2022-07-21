package koyonn.sudoku.computationlogic;

import static koyonn.sudoku.computationlogic.GameLogic.sudokuValidation;

import static koyonn.sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class SudokuSolver {

    /**
     * Метод, для выполнения проверки решаемости судоку
     *
     * @param puzzle массив судоку с 40 нулями в случайных ячейках
     * @return true - если судоку решаемо
     */
    public static boolean puzzleIsSolvable(int[][] puzzle) {
        int row = -1;
        int column = -1;
        boolean isEmpty = true;
        // Поиск ячеек судоку с 0
        for (int i = 0; i < GRID_BOUNDARY; i++) {
            for (int j = 0; j < GRID_BOUNDARY; j++) {
                if (puzzle[i][j] == 0) {
                    row = i;
                    column = j;
                    // Найдена ячейка с 0 значением
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }
        // Если пустых не найдено, значит судоку заполнено, а следовательно и решаемо
        if (isEmpty) {
            return true;
        }
        // Последовательно подставляем числа от 1 до 9 в каждую пустую ячейку
        for (int input = 1; input <= GRID_BOUNDARY; input++) {
            // Выполнение проверки на совпадение числа в квадрате, строке и столбце
            if (!sudokuValidation(
                    input,
                    row,
                    (row / 3),
                    column,
                    (column / 3),
                    puzzle)) {
                // Если совпадений нет, то число записывается в массив
                puzzle[row][column] = input;
                // Рекурсивно вызываем метод и подставляем в него массив, в который только что было внесено число
                if (puzzleIsSolvable(puzzle)) {
                    // Если в какой-то момент выполнения метода случится так, что ни одно число в цикле
                    // не удовлетворяет требованиям, то произойдет выход из цикла, значение так и останется 0, и
                    // будет возвращен false. Следовательно, будет возврат в прошлый шаг рекурсии, но уже будет
                    // выполнено else - обратно присвоено 0 значение и продолжен цикл input, начиная с того значений,
                    // которое было введено.
                    return true;
                } else {
                    puzzle[row][column] = 0;
                }
            }
        }
        return false;
    }
}

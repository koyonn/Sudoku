package koyonn.sudoku.problemdomain;

import koyonn.sudoku.computationlogic.SudokuUtilities;
import koyonn.sudoku.constants.GameState;

import java.io.Serializable;

/**
 * Судоку - это доска с 81 ячейкой
 */
public class SudokuGame implements Serializable {
    // Различные игровые состояния
    private final GameState gameState;
    // Состояние сетки Судоку
    private final int[][] gridState;
    // Количество ячеек в Судоку
    public static final int GRID_BOUNDARY = 9;
    // Начальное состояние массива судоку в конкретной игровой сессии
    private final int[][] originalGridState;

    /**
     * Основные "состояния" необходимые для отражения игры Судоку это - active state и complete state.
     * Игра начинается в active state, а когда игра завершается и таким образом достигается complete state
     * (основываясь на классе GameLogic) будет показан специальное окно, которое часть GUI.
     * <p>
     * Чтобы избежать shared mutable state, это класс создан как immutable. Каждый раз как происходят изменения на
     * доске, то создается новый объект игры Судоку на основе старого.
     *
     * @param gameState Игровые состояние представляют собой перечисления(Enums):
     *                  <p>
     *                  - GameState.Complete
     *                  <p>
     *                  - GameState.Active
     * @param gridState Состояние сетки Судоку. При достижении определенный условий GameLogic изменит gameState
     */
    public SudokuGame(GameState gameState, int[][] gridState, int[][] originalGridState) {
        this.gameState = gameState;
        this.gridState = gridState;
        this.originalGridState = originalGridState;
    }

    /**
     * Получить игровое состояние
     *
     * @return игровое состояние
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Возврат копии, чтобы обезопасить от изменения первоначальный объект
     *
     * @return копия текущего текущей доски Судоку
     */
    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }

    /**
     * Получить значение из массива, отражающего первоначальное состояние сетки судоку
     *
     * @param x индекс
     * @param y индекс
     * @return значение по индексу
     */
    public int getOriginalGridStateValue(int x, int y) {
        return originalGridState[x][y];
    }

    /**
     * Получить копию массива, отражающего первоначальное состояние сетки судоку
     *
     * @return копия массива
     */
    public int[][] getOriginalGridState() {
        return SudokuUtilities.copyToNewArray(originalGridState);
    }
}

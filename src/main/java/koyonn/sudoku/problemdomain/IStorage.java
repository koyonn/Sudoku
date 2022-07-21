package koyonn.sudoku.problemdomain;

import java.io.IOException;

/**
 * Интерфейс, методы которого служат для чтения-записи данных
 */
public interface IStorage {
    /**
     * Сохранить игровые данные
     *
     * @param game игра
     * @throws IOException ошибка записи
     */
    void updateGameData(SudokuGame game) throws IOException;

    /**
     * Получить игровые данные
     *
     * @return игровое состояние
     * @throws IOException ошибка чтения
     */
    SudokuGame getGameData() throws IOException;
}

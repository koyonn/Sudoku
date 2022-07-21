package koyonn.sudoku.buildlogic;

import koyonn.sudoku.computationlogic.GameLogic;

import koyonn.sudoku.persistence.LocalStorageImpl;
import koyonn.sudoku.problemdomain.IStorage;
import koyonn.sudoku.problemdomain.SudokuGame;
import koyonn.sudoku.userinterface.UserInterfaceContract;
import koyonn.sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

/**
 * Класс, отвечающий за создание игры
 */
public class SudokuBuildLogic {
    /**
     * Построение игры
     *
     * @param userInterface графический интерфейс
     * @throws IOException ошибка чтения-записи
     */
    public static void build(UserInterfaceContract.View userInterface) throws IOException {
        // Объект игры
        SudokuGame initialState;
        // Объект для хранения данных
        IStorage storage = new LocalStorageImpl();

        try {
            // Попытка получить сохраненные данные, при их наличии
            initialState = storage.getGameData();
        } catch (IOException e) {
            // Если сохраненных данных нет, то будет создана новая игра
            initialState = GameLogic.getNewGame();
            // Сохранение сгенерированной игры
            storage.updateGameData(initialState);
        }

        // Создание контроллера GUI
        UserInterfaceContract.SudokuEventListener uiLogic =
                (UserInterfaceContract.SudokuEventListener) new ControlLogic(storage, userInterface);

        // Создание слушателя графического интерфейса
        userInterface.setListener(uiLogic);
        // Заполнение доски
        userInterface.updateBoard(initialState);
    }
}

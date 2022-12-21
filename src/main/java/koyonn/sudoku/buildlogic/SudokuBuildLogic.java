package koyonn.sudoku.buildlogic;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import koyonn.sudoku.computationlogic.GameLogic;
import koyonn.sudoku.persistence.LocalStorageImpl;
import koyonn.sudoku.problemdomain.IStorage;
import koyonn.sudoku.problemdomain.SudokuGame;
import koyonn.sudoku.userinterface.UserInterfaceContract;
import koyonn.sudoku.userinterface.logic.ControlLogic;

/**
 * Класс, отвечающий за создание игры
 */
public class SudokuBuildLogic {
	private static final Logger logger = LoggerFactory.getLogger(SudokuBuildLogic.class);

	SudokuBuildLogic() {
		throw new IllegalStateException("Utility class");
	}

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
			logger.error(e.getMessage(), e);
			// Если сохраненных данных нет, то будет создана новая игра
			initialState = GameLogic.getNewGame();
			// Сохранение сгенерированной игры
			storage.updateGameData(initialState);
		}
		// Создание контроллера GUI
		UserInterfaceContract.SudokuEventListener uiLogic = new ControlLogic(storage, userInterface);
		// Создание слушателя графического интерфейса
		userInterface.setListener(uiLogic);
		// Заполнение доски
		userInterface.updateBoard(initialState);
	}
}

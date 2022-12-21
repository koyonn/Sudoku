package koyonn.sudoku.userinterface.logic;

import koyonn.sudoku.SudokuApplication;
import koyonn.sudoku.computationlogic.GameLogic;

import koyonn.sudoku.constants.GameState;
import koyonn.sudoku.problemdomain.IStorage;
import koyonn.sudoku.problemdomain.SudokuGame;
import koyonn.sudoku.userinterface.UserInterfaceContract;
import koyonn.sudoku.userinterface.UserInterfaceImplementation;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static koyonn.sudoku.constants.Messages.ERROR;
import static koyonn.sudoku.constants.Messages.GAME_COMPLETE;

/**
 * Control logic class. Управляет действиями между пользователем, графическим
 * интерфейсом и бекэндом. В данном случае мы общаемся с View
 */
public class ControlLogic implements UserInterfaceContract.SudokuEventListener {
	private static final Logger logger = LoggerFactory.getLogger(ControlLogic.class);
	private IStorage storage;
	private UserInterfaceContract.View view;

	public ControlLogic(IStorage storage, UserInterfaceContract.View view) {
		this.storage = storage;
		this.view = view;
	}

	/**
	 * Метод, отвечающий за ввод данных в ячейки судоку
	 *
	 * @param x     координата Х
	 * @param y     координата Y
	 * @param input вводимое число
	 */
	@Override
	public void onSudokuInput(int x, int y, int input) {
		try {
			// Создаем объект судоку и передаем ему все данные текущей игры
			SudokuGame gameData = storage.getGameData();
			// Создаем массив судоку и передаем ему текущее состояние сетки
			// судоку
			int[][] newGridState = gameData.getCopyOfGridState();
			// Присваиваем только что введенное значение по заданным координатам
			newGridState[x][y] = input;
			// Создаем массив судоку, который представляет собой состояние сетки
			// судоку на начало игровой сессии
			int[][] originalGridState = gameData.getOriginalGridState();
			// Присваиваем обновленные значения
			gameData = new SudokuGame(GameLogic.checkForCompletion(newGridState), newGridState, originalGridState);
			// Сохраняем состояние
			storage.updateGameData(gameData);
			// Обновление вида ячейки после ввода числа
			view.updateSquare(x, y, input);
			// Если судоку заполнено правильно, то игра получает статус COMPLETE
			if (gameData.getGameState() == GameState.COMPLETE) {
				view.showDialog(GAME_COMPLETE);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			view.showErrors(ERROR);
		}
	}

	/**
	 * Нажатие в диалоговом окне
	 */
	@Override
	public void onDialogClick() {
		try {
			storage.updateGameData(GameLogic.getNewGame());
			view.updateBoard(storage.getGameData());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			view.showErrors(ERROR);
		}
	}

	/**
	 * Перезапуск игры
	 */
	@Override
	public void restartSudoku() {
		try {
			storage.updateGameData(GameLogic.getNewGame());
			view.updateBoard(storage.getGameData());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			view.showErrors(ERROR);
		}
	}
}

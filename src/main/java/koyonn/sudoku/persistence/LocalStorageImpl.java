package koyonn.sudoku.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import koyonn.sudoku.problemdomain.IStorage;
import koyonn.sudoku.problemdomain.SudokuGame;

public class LocalStorageImpl implements IStorage {
	private static final Logger logger = LoggerFactory.getLogger(LocalStorageImpl.class);
	private static final File GAME_DATA = new File(System.getProperty("user.home"), "game_data.txt");

	public static File getSavePath() {
		return GAME_DATA;
	}

	/**
	 * Сохранить игровые данные
	 *
	 * @param game игра
	 * @throws IOException ошибка записи
	 */
	@Override
	public void updateGameData(SudokuGame game) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
		        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
			objectOutputStream.writeObject(game);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new IOException("Unable to access GAME_DATA");
		}
	}

	/**
	 * Получить игровые данные
	 *
	 * @return игровое состояние
	 * @throws IOException ошибка чтения
	 */
	@Override
	public SudokuGame getGameData() throws IOException {
		try (FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
		        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);) {
			SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
			return gameState;
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new IOException("File not found!");
		}
	}
}

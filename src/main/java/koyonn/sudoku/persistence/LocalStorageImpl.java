package koyonn.sudoku.persistence;

import koyonn.sudoku.problemdomain.IStorage;
import koyonn.sudoku.problemdomain.SudokuGame;

import java.io.*;

public class LocalStorageImpl implements IStorage {
    private static final File GAME_DATA =
            new File(System.getProperty("user.home"), "game_data.txt");

    public static File getSavePath() {
        File file = GAME_DATA;
        return file;
    }

    /**
     * Сохранить игровые данные
     *
     * @param game игра
     * @throws IOException ошибка записи
     */
    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
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
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            return gameState;
        } catch (ClassNotFoundException e) {
            throw new IOException("File not found!");
        }
    }
}

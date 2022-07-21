package koyonn.sudoku.userinterface;

import koyonn.sudoku.problemdomain.SudokuGame;

/**
 * Родительский интерфейс выступает как пространство имен множества компонентов пользовательского интерфейса
 */
public interface UserInterfaceContract {
    /**
     * EventListener выступает в качестве контроллера пользовательского графического интерфейса.
     * Он получает событие, вызванное действиями пользователя, обрабатывает его и передает во внутреннюю часть
     * программы.
     */
    interface SudokuEventListener {
        /**
         * Действие: ввод значения в поле судоку
         *
         * @param x     координата Х
         * @param y     координата Y
         * @param input вводимое число
         */
        void onSudokuInput(int x, int y, int input);

        /**
         * Действие: клик мышкой в диалоговом окне
         */
        void onDialogClick();

        /**
         * Метод, предназначенный для рестарта игры
         */
        void restartSudoku();
    }

    /**
     * View отвечает за отображение графического интерфейса
     */
    interface View {
        /**
         * Регистрация слушателя
         *
         * @param listener слушатель
         */
        void setListener(UserInterfaceContract.SudokuEventListener listener);

        /**
         * Обновление квадрата судоку
         *
         * @param x     координата Х
         * @param y     координата Y
         * @param input вводимое число
         */
        void updateSquare(int x, int y, int input);

        /**
         * Обновление всей доски
         *
         * @param game игра
         */
        void updateBoard(SudokuGame game);

        /**
         * Показ диалогового окна
         *
         * @param message сообщение
         */
        void showDialog(String message);

        /**
         * Показ ошибок
         *
         * @param message сообщение
         */
        void showErrors(String message);
    }
}

package koyonn.sudoku.userinterface;

import javafx.scene.control.TextField;

/**
 * Измененный TextField
 */
public class SudokuTextField extends TextField {
    private final int x;
    private final int y;

    public SudokuTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void replaceText(int i, int j, String text) {
        if (!text.matches("[0-9]")) {
            super.replaceText(i, j, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (!text.matches("[0-9]")) {
            super.replaceSelection(text);
        }
    }
}

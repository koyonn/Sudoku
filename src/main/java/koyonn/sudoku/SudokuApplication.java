package koyonn.sudoku;

import javafx.application.Application;
import javafx.stage.Stage;

import koyonn.sudoku.buildlogic.SudokuBuildLogic;
import koyonn.sudoku.userinterface.UserInterfaceContract;
import koyonn.sudoku.userinterface.UserInterfaceImplementation;

import java.io.IOException;

/**
 * Этот класс является главным контейнером (объектом, который связывает все основные компоненты между собой)
 */
public class SudokuApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Ссылка на пользовательский интерфейс (контракт)
        UserInterfaceContract.View uiImpl = new UserInterfaceImplementation(stage);
        try {
            SudokuBuildLogic.build(uiImpl);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

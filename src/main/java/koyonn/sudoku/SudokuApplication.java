package koyonn.sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import koyonn.sudoku.buildlogic.SudokuBuildLogic;
import koyonn.sudoku.userinterface.UserInterfaceContract;
import koyonn.sudoku.userinterface.UserInterfaceImplementation;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Этот класс является главным контейнером (объектом, который связывает все
 * основные компоненты между собой)
 */
public class SudokuApplication extends Application {
	private static final Logger logger = LoggerFactory.getLogger(SudokuApplication.class);

	@Override
	public void start(Stage stage) throws IOException {
		// Ссылка на пользовательский интерфейс (контракт)
		UserInterfaceContract.View uiImpl = new UserInterfaceImplementation(stage);
		try {
			SudokuBuildLogic.build(uiImpl);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

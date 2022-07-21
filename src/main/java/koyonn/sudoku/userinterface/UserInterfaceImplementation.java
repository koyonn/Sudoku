package koyonn.sudoku.userinterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import koyonn.sudoku.SudokuApplication;
import koyonn.sudoku.constants.GameState;
import koyonn.sudoku.problemdomain.Coordinates;
import koyonn.sudoku.problemdomain.SudokuGame;

import java.util.HashMap;
import java.util.Objects;

/**
 * Графическое окно приложения и всплывающие уведомления
 */
public class UserInterfaceImplementation implements UserInterfaceContract.View, EventHandler<KeyEvent> {
    // Окно приложения
    private final Stage stage;
    // Контейнер графических компонентов
    private final Group root;

    // HashMap для отслеживания состояния 81 ячейки поля судоку
    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;
    // Контроллер
    private UserInterfaceContract.SudokuEventListener listener;

    // Позиционирование по осям Х и Y на мониторе
    private static final double WINDOW_Y = 718;
    private static final double WINDOW_X = 668;
    // Расстояние между полем судоку и окном приложения
    private static final double BOARD_PADDING = 50;
    // Длина и ширина ячейки судоку
    private static final int DELTA = 64;
    // Размер судоку
    private static final double BOARD_X_AND_Y = 576;

    private static final String SUDOKU = "Sudoku";

    /**
     * Конструктор класса пользовательского интерфейса
     *
     * @param stage окно игры
     */
    public UserInterfaceImplementation(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    /**
     * Вспомогательный метод, служащий для инициализации компонентов графического интерфейса
     */
    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        addButtons(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        stage.getIcons().add(
                new Image(Objects.requireNonNull(
                        SudokuApplication.class.getResourceAsStream("Icon.png"))));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Создание кнопок и добавление их в окно
     *
     * @param root корневой контейнер
     */
    private void addButtons(Group root) {
        Button buttonExit = new Button("Exit game");
        buttonExit.setTranslateX(518);
        buttonExit.setTranslateY(640);
        buttonExit.setOpacity(0.70);
        buttonExit.setStyle(
                "-fx-padding: 17.5; " +
                        "-fx-background-color: black; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 16; " +
                        "-fx-border-width: 1.2;" +
                        "-fx-background-radius: 16; " +
                        "-fx-font-smoothing-type: lcd;"
        );
        buttonExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        Button buttonRestart = new Button("Restart game");
        buttonRestart.setTranslateX(53);
        buttonRestart.setTranslateY(640);
        buttonRestart.setOpacity(0.70);
        buttonRestart.setStyle(
                "-fx-padding: 17.5; " +
                        "-fx-background-color: black; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16; " +
                        "-fx-border-color: white; " +
                        "-fx-border-radius: 16; " +
                        "-fx-border-width: 1.2;" +
                        "-fx-background-radius: 16; " +
                        "-fx-font-smoothing-type: lcd;"
        );
        buttonRestart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.hide();
                listener.restartSudoku();
                stage.show();
            }
        });

        root.getChildren().addAll(buttonExit, buttonRestart);
    }

    /**
     * Отрисовка заднего плана
     *
     * @param root корневой контейнер
     */
    private void drawBackground(Group root) {
        Image image = new Image("koyonn/sudoku/background.jpg",
                732, 732, false, true);
        BackgroundImage myBI = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(myBI);
        StackPane sp = new StackPane();
        sp.setPrefSize(732, 732);
        sp.setBackground(background);
        root.getChildren().add(sp);

        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);

        stage.setScene(scene);
    }

    /**
     * Метод для отрисовки заголовка
     *
     * @param root корневой контейнер
     */
    private void drawTitle(Group root) {
        Text title = new Text(268, 40, SUDOKU);
        title.setStyle(
                "-fx-font: 38px System; " +
                        "-fx-stroke: white; " +
                        "-fx-stroke-width: 1; " +
                        "-fx-font-smoothing-type: lcd;"
        );
        root.getChildren().add(title);
    }

    /**
     * Метод для отрисовки поля судоку
     *
     * @param root корневой контейнер
     */
    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setHeight(BOARD_X_AND_Y);
        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setFill(new Color(0.0, 0.0, 0.0, 0.75));
        root.getChildren().add(boardBackground);
    }

    /**
     * Метод для отрисовки текстовых полей
     *
     * @param root корневой контейнер
     */
    private void drawTextFields(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;
        // O(n^2) сложность. Цикл вложен в цикл, добавление элементов увеличивает количество проходов квадратично
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                int x = xOrigin + xIndex * DELTA;
                int y = yOrigin + yIndex * DELTA;
                // Отрисовка текстового поля
                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);
                // Инкапсуляция информации о стиле текстового поля
                styleSudokuTile(tile, x, y);
                // Далее вызывается handler()
                tile.setOnKeyPressed(this);

                textFieldCoordinates.put(
                        new Coordinates(xIndex, yIndex), tile
                );

                root.getChildren().add(tile);
            }
        }
    }

    /**
     * Создание текстового поля
     *
     * @param tile текстовое поле
     * @param x    длина
     * @param y    ширина
     */
    private void styleSudokuTile(SudokuTextField tile, double x, double y) {
        Font numberFont = new Font(34);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);
        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(DELTA);
        tile.setPrefWidth(DELTA);
        tile.setBackground(Background.EMPTY);
    }

    /**
     * Метод для отрисовки вертикальных и горизонтальных линий
     *
     * @param root корневой контейнер
     */
    private void drawGridLines(Group root) {
        int xAndY = 114;
        int index = 0;
        while (index < 8) {
            int thickness;
            if (index == 2 || index == 5) {
                thickness = 3;
            } else {
                thickness = 2;
            }
            // Для отрисовки линий поля будет использоваться класс Rectangle (JavaFX)
            // Рисуем вертикальную линию и сдвигаем ее на 64 пикселя
            Rectangle verticalLine = getLine(
                    xAndY + DELTA * index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
            );
            // Рисуем горизонтальную линию и сдвигаем ее на 64 пикселя
            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + DELTA * index,
                    thickness,
                    BOARD_X_AND_Y
            );
            root.getChildren().addAll(verticalLine, horizontalLine);
            index++;
        }
    }

    /**
     * Метод для создания линий
     *
     * @param x      длина
     * @param y      ширина
     * @param height высота
     * @param width  толщина
     * @return линия
     */
    private Rectangle getLine(double x,
                              double y,
                              double height,
                              double width) {
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);
        line.setFill(Color.BLACK);
        return line;
    }

    /**
     * Регистрация слушателя
     *
     * @param listener контроллер графического интерфейса
     */
    @Override
    public void setListener(UserInterfaceContract.SudokuEventListener listener) {
        this.listener = listener;
    }

    /**
     * Метод для обновления ячейки судоку (одиночного элемента), а не всей доски
     *
     * @param x     координаты по оси Х
     * @param y     координаты по оси Y
     * @param input введенное число в поле судоку
     */
    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(
                new Coordinates(x, y)
        );

        String value = Integer.toString(input);

        if (value.equals("0")) {
            value = "";
        }

        tile.setStyle("-fx-text-inner-color: white;");

        tile.textProperty().setValue(value);
    }

    /**
     * Метод для обновления всего поля судоку (например, когда игрок закончил игру)
     *
     * @param game игра
     */
    @Override
    public void updateBoard(SudokuGame game) {
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                TextField tile = textFieldCoordinates.get(
                        new Coordinates(xIndex, yIndex)
                );

                String value = Integer.toString(
                        game.getCopyOfGridState()[xIndex][yIndex]
                );
                if (value.equals("0")) {
                    value = "";
                }
                tile.setText(value);
                if (game.getGameState() == GameState.NEW) {
                    // Если ячейка пуста, то делаем ее активной, иначе блокируем и делаем шрифт более прозрачным
                    if (value.equals("")) {
                        tile.setStyle(
                                "-fx-opacity: 1; " +
                                        "-fx-text-inner-color: white;");
                        tile.setDisable(false);
                    } else {
                        tile.setStyle(
                                "-fx-opacity: 0.45; " +
                                        "-fx-text-inner-color: white;");
                        tile.setDisable(true);
                    }
                } else if (game.getGameState() == GameState.ACTIVE) {
                    // При повторном запуске, первоначально заполненные ячейки делаем неактивными
                    if (game.getOriginalGridStateValue(xIndex, yIndex) != 0) {
                        tile.setStyle(
                                "-fx-opacity: 0.45; " +
                                        "-fx-text-inner-color: white;");
                        tile.setDisable(true);
                    } else {
                        tile.setStyle(
                                "-fx-opacity: 1; " +
                                        "-fx-text-inner-color: white;");
                    }
                }
            }
        }
    }

    /**
     * Метод будет вызван, когда logic center получит извещение, что игра окончена
     *
     * @param message игровое сообщение
     */
    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) {
            listener.onDialogClick();
        }
    }

    /**
     * Показ окна в случае ошибки
     *
     * @param message сообщение
     */
    @Override
    public void showErrors(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }

    /**
     * Когда пользователь вводит число в текстовой поле, то это событие появится тут
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(KeyEvent event) {
        // Проверяем тип события
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            // Если введено число от 0 до 9
            if (event.getText().matches("[0-9]")) {
                int value = Integer.parseInt(event.getText());
                handleInput(value, event.getSource());
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0, event.getSource());
            } else {
                ((TextField) event.getSource()).setText("");
            }
        }
        event.consume();
    }

    /**
     * Когда случится ввод значения, оно будет передано в control logic class,
     * чей listener есть в данном методе
     *
     * @param value  значение
     * @param source источник
     */
    private void handleInput(int value, Object source) {
        listener.onSudokuInput(((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value);
    }
}

package TicTackToe;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static int sizeField = 3;
    static int dots_to_WIN = 3;
    static int gameMode = 1;
    static final char DOT_X = 'X';
    static final char DOT_0 = '0';
    static final char DOT_EMPTY = '.';
    static char symbol = DOT_X;
    static char[][] gameField;
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    public static int diff;

    public static void main(String[] args) {
        menuMain();
    }

    // 1. Главное меню:
    private static void menuMain() {
        System.out.println("КРЕСТИКИ-НОЛИКИ");
        System.out.println("1. введите, что бы сыграть в быструю игру.");
        System.out.println("2. введите, что бы создать пользовательскую игру.");
        System.out.println("3. введите для завершения программы.");
        switch (scanner.nextInt()) {
            case 1 -> {
                sizeField = 3;
                dots_to_WIN = 3;
                gameMode = 1;
                symbol = DOT_X;
                newGame(sizeField, dots_to_WIN, gameMode, symbol);
            }
            case 2 -> createYourselfSettingsGame();
            case 3 -> System.exit(1);
        }

    }

    // 1.1. Выбор размера поля и количесва ячеек ПОДРЯД для победы
    private static void createYourselfSettingsGame() {
        newGame(setSizeField(), setDots_to_WIN(), playerChoiceMode(), chooseSymbol());
    }

    private static int setDots_to_WIN() {
        if (sizeField >= 4) {
            System.out.println("Теперь введите число ячеек с одинаковыми символами подряд для победы...");
            dots_to_WIN = scanner.nextInt();
            while (dots_to_WIN > sizeField) {
                System.out.println("Вообще-то, это слишком много для поля размером " + sizeField + " на " + sizeField);
                System.out.println("Кол-во ячеек для победы должно быть <= " + sizeField);
                dots_to_WIN = scanner.nextInt();
            }
        }
        return dots_to_WIN;
    }

    private static int setSizeField() {
        System.out.println("Введите в консоль размер поля для игры (3 и больше)");
        sizeField = scanner.nextInt();
        while (sizeField < 3) {
            System.out.println("Такие крестики-нолики никому не будут интересны =/...");
            System.out.println("Попробуйте ввести число 3 или более...");
            sizeField = scanner.nextInt();
        }
        if (sizeField == 3) {
            dots_to_WIN = 3;
            System.out.println("Играем в классику! ;)");
        }
        return sizeField;
    }

    // 1.2. Выбор режима: Игорок/Игрок или Игрок/ИИ
    private static int playerChoiceMode() {
        System.out.println("1. введите, что бы играть против компьютера.");
        System.out.println("2. введите, что бы играть с другим человеком.");
        System.out.println("3. введите, что бы возвратиться в главное меню.");
        switch (scanner.nextInt()) {
            case 1 -> {
                gameMode = 1;
                return gameMode;
            }
            case 2 -> {
                gameMode = 2;
                return gameMode;
            }
            case 3 -> menuMain();
            default -> scanner.nextInt();
        }
        return gameMode;
    }

    // 1.3. Выбор первого хода
    private static char chooseSymbol() {
        System.out.println("Выбирете символ игрока (X или 0)...\nКрестики ходят первыми ;)");
        System.out.println("1. введите для выбора 'X'.");
        System.out.println("2. введите для выбора '0'.");
        switch (scanner.nextInt()) {
            case 1 -> symbol = DOT_X;
            case 2 -> symbol = DOT_0;
        }
        return symbol;
    }

    // 2.0 НОВАЯ ИГРА
    private static void newGame(int sizeField, int dots_to_WIN, int gameMode, char symbol) {
        createGameField(sizeField);
        showGameField();
        diff = sizeField-dots_to_WIN;
        while (true) {
            if (gameMode == 1) {
                if (symbol == DOT_X) {
                    moveHuman(DOT_X);
                    if (hasVictory(dots_to_WIN, DOT_X)) {
                        System.out.println("Победил Человек!");
                        break;
                    }
                    if (isFieldFull()) {
                        System.out.println("НИЧЬЯ");
                        break;
                    }
                    moveAI(DOT_0);
                    if (hasVictory(dots_to_WIN, DOT_0)) {
                        System.out.println("Победил компьютер");
                        break;
                    }
                }
                if (symbol == DOT_0) {
                    moveAI(DOT_X);
                    if (hasVictory(dots_to_WIN, DOT_X)) {
                        System.out.println("Победил компьютер");
                        break;
                    }
                    if (isFieldFull()) {
                        System.out.println("НИЧЬЯ");
                        break;
                    }
                    moveHuman(DOT_0);
                    if (hasVictory( dots_to_WIN, DOT_0)) {
                        System.out.println("Победил Человек!");
                        break;
                    }
                }

            }
            if (gameMode == 2) {
                System.out.println("Человек VS человека");
                if (symbol == DOT_X) {
                    moveHuman(DOT_X);
                    if (hasVictory(dots_to_WIN, DOT_X)) {
                        System.out.println("Победил игрок №1!");
                        break;
                    }
                    if (isFieldFull()) {
                        System.out.println("НИЧЬЯ");
                        break;
                    }
                    moveHuman(DOT_0);
                    if (hasVictory( dots_to_WIN, DOT_0)) {
                        System.out.println("Победил игрок №2!");
                        break;
                    }
                }
            }
        }
        menuMain();
    }

    // 2. Создание игрового поля
    private static void createGameField(int sizeField) {
        gameField = new char[sizeField][sizeField];
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                gameField[i][j] = DOT_EMPTY;
            }
        }
    }

    // 3. Показ игрового поля для игроков
    static void showGameField() {
        for (int i = 0; i <= gameField.length; i++) {
            System.out.print(i + "  ");
        }
        System.out.println();
        for (int i = 0; i < gameField.length; i++) {
            System.out.print((i + 1) + "  ");
            for (int j = 0; j < gameField[i].length; j++) {
                System.out.print(gameField[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // ЦИКЛ
    // 4. Ход человека
    private static void moveHuman(char symbol) {
        // 4.1. Установка символа в ячейку
        int x;
        int y;
        do {
            System.out.println("Введите координаты ячейки в формате X - Y ...");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (isCellValid(x, y));
        gameField[y][x] = symbol;
        showGameField();

    }

    // 4.2. Проверка на правильность координат и занятость выбранной позиции для символа
    private static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= sizeField || y < 0 || y >= sizeField) {
            System.out.println("Вы ввели неверные координаты!");
            return true;
        } else if (gameField[y][x] != DOT_EMPTY) {
            System.out.println("Эта ячейка уже занята!");
            return true;
        } else return false;
    }

    // 4.4. Проверка на Победу (Ряд, Столбец, Диагонали)
    private static boolean hasVictory(int dots_to_WIN, char symbol) {

        int counter = 0;
        int couLine = 0;
        int couColumn = 0;

        // по столбцам
        while (couColumn != gameField.length) {
            for (char[] chars : gameField) {
                if (chars[couColumn] == symbol) {
                    counter++;
                    if (counter == dots_to_WIN) {
                        System.out.println("Победил игорк " + symbol + " по столбцам!");
                        return true;
                    }
                } else counter = 0;
            }
            couColumn++;
            counter = 0;
        }
        // по строкам
        while (couLine != gameField.length) {
            for (int j = 0; j < gameField.length; j++) {
                if (gameField[couLine][j] == symbol) {
                    counter++;
                    if (counter == dots_to_WIN) {
                        System.out.println("Победил игорк " + symbol + " по строкам!");
                        return true;
                    }
                } else counter = 0;
            }
            couLine++;
            counter = 0;
        }
// по диагонали
// основная главная
        for (int i = 0; i < gameField.length; i++) {
            if (gameField[i][i] == symbol) {
                    counter++;
                    if (counter == dots_to_WIN) {
                        System.out.println("Основная диагональ");
                        return true;
                    }
             } else counter = 0;
        }

// второстепенная главная
            for (int i = 0; i < gameField.length; i++) {
                if (gameField[i][gameField.length-i-1]==symbol){
                    counter++;
                    if (counter == dots_to_WIN){
                    System.out.println("второстепенная главная");
                    return true;
                    }
                }
                else counter = 0;
            }

// основная низ
        if (diff>0) {
            for (int d = 1; d <= diff; d++) {

                for (int i = d; i < gameField.length; i++) {
                    if (gameField[i][i - d] == symbol) {
                        counter++;
                        if (counter == dots_to_WIN) {
                            System.out.println("основная низ");
                            return true;
                        }
                    } else counter = 0;
                }

// основная верх
                for (int i = 0; i < gameField.length - d; i++) {
                    if (gameField[i][i + d] == symbol) {
                        counter++;
                        if (counter == dots_to_WIN) {
                            System.out.println("основная верх");
                            return true;
                        }
                    } else counter = 0;
                }

// второстепенная низ
                for (int i = d; i < gameField.length; i++) {
                    for (int j = 0; j < gameField[i].length; j++) {
                        if (i + j == gameField.length - 1 + d) {
                            if (gameField[i][j] == symbol) {
                                counter++;
                                if (counter == dots_to_WIN) {
                                    System.out.println("второстепенная низ");
                                    return true;
                                }
                            } else counter = 0;
                        }
                    }
                }

// второстепенная верх
                for (int i = 0; i < gameField.length; i++) {
                    for (int j = 0; j < gameField[i].length; j++) {
                        if (i + j == gameField.length - 1 - d) {
                            if (gameField[i][j] == symbol) {
                                counter++;
                                if (counter == dots_to_WIN) {
                                    System.out.println("второстепенная верх");
                                    return true;
                                }
                            } else counter = 0;
                        }
                    }
                }
            }
        }
        return false;
    }

        // 6. Ход ИИ
        public static void moveAI ( char symbol){
            int x;
            int y;
            do {
                x = random.nextInt(sizeField);
                y = random.nextInt(sizeField);
            }
            while (isCellValid(x, y));
            gameField[y][x] = symbol;
            System.out.println("Компьютер сделал ход на " + (x + 1) + " - " + (y + 1) + " ...");
            showGameField();
        }

        // 7. Проверка на НИчью
        public static boolean isFieldFull () {
            for (char[] chars : gameField) {
                for (int j = 0; j < chars.length; j++) {
                    if (chars[j] == DOT_EMPTY) {
                        return false;
                    }
                }
            }
            return true;
        }

}

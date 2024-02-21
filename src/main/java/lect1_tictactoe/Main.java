package lect1_tictactoe;

public class Main {
    public static void main(String[] args) {
        new GameWindow();
    }
}

/*
    public static int fieldSizeX = 5;
    public static int fieldSizeY = 6;



    public static void main(String[] args) {
        int[][] arr0 = {
                {0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0},
        };
        int[][] arr1 = {
                {0, 0, 0, 0, 0},
                {1, 1, 0, 1, 0},
                {1, 0, 1, 0, 1},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0},
        };
        int[][] arr2 = {
                {0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1},
        };
        int[][] arr3 = {
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 1},
                {0, 0, 0, 1, 0},
                {0, 0, 1, 0, 1},
                {0, 1, 0, 0, 0},
        };
        int[][] arr4 = {
                {2, 2, 1, 1, 2},
                {2, 1, 1, 0, 1},
                {2, 1, 2, 1, 0},
                {0, 0, 1, 0, 2},
                {2, 1, 2, 2, 2},
                {1, 0, 1, 0, 0},
        };


        System.out.println("arr0: " + checkWin(arr0, 1));
        System.out.println("arr1: " + checkWin(arr1, 1));
        System.out.println("arr2: " + checkWin(arr2, 1));
        System.out.println("arr3: " + checkWin(arr3, 1));
        System.out.println("arr4: " + checkWin(arr4, 1));

    }

    public static boolean checkWin(int[][] field, int symbol){

        // ВАРИАНТ 2
        // УНИВЕРСАЛЬНЫЙ ДЛЯ ЛЮБОГО ПОЛЯ и любой длины выигрыша
        // СОЗДАНИЕ СЧЁТЧИКА для каждого варианта

        int winLength = 5;

        // проверка всех горизонталей и вертикалей
        // если последовательность продолжается - увеличение счётчика
        // если последовательность прерывается - обнуление счётчика
        int[] rowsWin = new int[fieldSizeY];
        int[] columnsWin = new int[fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == symbol) {
                    rowsWin[i] +=1;
                    columnsWin[j] +=1;
                }
                else {
                    rowsWin[i] = 0;
                    columnsWin[j] = 0;
                }
                if (rowsWin[i] == winLength || columnsWin[j] == winLength) return true;
            }
        }

        // проверка всех правых диагоналей (из левого верхнего в правый нижний)
        // проверка всех левых диагоналей (из правого верхнего в левый нижний)
        // количество диагоналей, начинающихся из каждой строки и идущих далее вниз = Х - длина выигрыша + 1
        // проверяем только первые несколько рядов, ниже которых влезают диагонали
        int rowsWithDiag = fieldSizeY - winLength + 1;
        int rightDiagonalWin = 0;
        int leftDiagonalWin = 0;

        for (int j = 0; j < rowsWithDiag; j++) {        // отсчитываем ряды
            for (int i = 0; i < fieldSizeX; i++) {      // отсчитываем элементы в ряду
                if (field[j][i] == symbol) {            // если текущий символ совпадает с искомым, то ...
                    if ((i + winLength) <= fieldSizeX) {        // проверяем - может ли идти из точки правая диагональ
                        rightDiagonalWin = 1;                   // начинаем плюсовать длину правой диагонали,
                        for (int k = 1; k < winLength; k++) {   // отсчитываем элементы в диагонали
                            if (field[j + k][i + k] == symbol)  // проверяем символы по правой диагонали
                                rightDiagonalWin += 1;          // увеличиваем длину диагонали
                            else                                // иначе
                                break;                          // прерываем цикл при первом же несовпадении
                        }
                    }
                    if ((i - winLength + 1) >= 0) {             // проверяем - может ли идти из точки ЛЕВАЯ диагональ
                        leftDiagonalWin = 1;                    // начинаем плюсовать длину ЛЕВОЙ диагонали,
                        for (int k = 1; k < winLength; k++) {   // отсчитываем элементы в диагонали
                            if (field[j + k][i - k] == symbol)  // проверяем символы по ЛЕВОЙ диагонали
                                leftDiagonalWin += 1;           // увеличиваем длину диагонали
                            else                                // иначе
                                break;                          // прерываем цикл при первом же несовпадении
                        }
                    }
                    // ДАЛЕЕ - тоже самое, только короче запись - проверка обоих диагоналей в одном цикле
//                    for (int k = 1; k < winLength; k++) {
//                        if ((i + winLength) <= fieldSizeX) {
//                            if (field[j + k][i + k] == symbol)
//                                rightDiagonalWin += 1;
//                        }
//                        if ((i - winLength + 1) >= 0) {
//                            if (field[j + k][i - k] == symbol)
//                                leftDiagonalWin += 1;
//                        }
//                    }

                    if (rightDiagonalWin == winLength || leftDiagonalWin == winLength)
                        return true;
                }
            }
        }
        return false;
    }
}

*/
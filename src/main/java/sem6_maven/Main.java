package sem6_maven;

public class Main {
    public static void main(String[] args) {
        MontyHallGame mh = new MontyHallGame(15555);

        mh.startgame();
        mh.getStatistics();
    }
}

/*
ДОБРО ПОЖАЛОВАТЬ НА ИГРУ МОНТИ ХОЛЛА !
Дверь с машиной: 3  Дверь с козой: 2  Пустая дверь: 1
Выбор игрока:    1  Монти открыл:  2  Игрок меняет дверь: 3, Выигрыш: true
────────────────────
Дверь с машиной: 2  Дверь с козой: 3  Пустая дверь: 1
Выбор игрока:    2  Монти открыл:  1  Игрок меняет дверь: 3, Выигрыш: false
────────────────────
Дверь с машиной: 1  Дверь с козой: 2  Пустая дверь: 3
Выбор игрока:    3  Монти открыл:  2  Игрок меняет дверь: 1, Выигрыш: true
────────────────────
Обработка │████████████████████│ 100%
Количество игр: 15555, Количество выигрышей: 10425 (67,02 %).
 */
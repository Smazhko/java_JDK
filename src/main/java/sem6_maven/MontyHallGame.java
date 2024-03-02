package sem6_maven;


/*
https://habr.com/ru/companies/piter/articles/676394/

В качестве задачи предлагаю вам реализовать код для демонстрации парадокса Монти Холла
и наглядно убедиться в верности парадокса (запустить игру в цикле на 1000 и вывести итоговый счет).
Необходимо:
- Создать свой Java Maven или Gradle проект;
- Подключите зависимость lombok и возможно какую то математическую библиотеку (напр. commons-math3)
- Самостоятельно реализовать прикладную задачу;
- Сохранить результат игр в одну из коллекций или в какой-то библиотечный класс
- Вывести на экран статистику по победам и поражениям
В качестве ответа прислать ссылку на репозиторий, в котором присутствует все важные файлы проекта (напр pom.xml)
 */

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

@RequiredArgsConstructor
// @RequiredArgsConstructor автоматически создаст конструктор, содержащий
// неинициализированные поля final, а также любые поля, помеченные как @NonNull,
// которые не инициализируются при объявлении
public class MontyHallGame {
    @Setter
    // @Setter автоматически создаст сеттер для помеченного поля
    private final int numberOfCycles;
    private List<Integer> doors = new ArrayList<>();
    private Random rnd = new Random();
    private int superPrizeDoor, goatDoor, emptyDoor;
    private int playerDoor, montyDoor, newPlayerDoor;
    private boolean isPlayerWin;
    private List<Boolean> statistics = new ArrayList<>();
    // 1 = car
    // 2 = goat
    // 3 = empty

    public void startgame(){
        for (int i = 0; i < numberOfCycles; i++) {
            assignPrize();
            int playerDoorIndex = rnd.nextInt(0,3);
            playerDoor = doors.get(playerDoorIndex);
            doors.remove(playerDoorIndex);

            int montyDoorIndex = (doors.get(0) == superPrizeDoor) ? 1 : 0;
            montyDoor = doors.get(montyDoorIndex);
            doors.remove(montyDoorIndex);

            newPlayerDoor = doors.get(0);
            isPlayerWin = (newPlayerDoor == superPrizeDoor);
            logGame();
            statistics.add(isPlayerWin);
        }
    }

    private void assignPrize(){
        resetDoors();
        int superPrizeIndex = rnd.nextInt(0,3);
        superPrizeDoor = doors.get(superPrizeIndex);
        doors.remove(superPrizeIndex);
        int goatIndex = rnd.nextInt(0, 2);
        goatDoor = doors.get(goatIndex);
        doors.remove(goatIndex);
        emptyDoor = doors.get(0);
        resetDoors();
    }

    public void resetDoors(){
        if (!doors.isEmpty())
            doors.clear();
        for (int i = 1; i < 4; i++) {
            doors.add(i);
        }
    }

    private void logGame(){
        if (statistics.isEmpty())
            System.out.println("\nДОБРО ПОЖАЛОВАТЬ НА ИГРУ МОНТИ ХОЛЛА !");
        if (statistics.size() < 3) {
            System.out.printf("Дверь с машиной: %s  Дверь с козой: %s  Пустая дверь: %s%n" +
                              "Выбор игрока:    %s  Монти открыл:  %s  Игрок меняет дверь: %s, Выигрыш: %s%n",
                    superPrizeDoor, goatDoor, emptyDoor,
                    playerDoor, montyDoor, newPlayerDoor, isPlayerWin);
            System.out.println("─".repeat(20));
        }
        else {
            int percentage = Math.round((float) statistics.size() * 100 / numberOfCycles);
            System.out.printf("\rОбработка │%-20s│ %-3s%%", "█".repeat(percentage/5), percentage);

        }

    }

    public void getStatistics(){
        // более правильным и актуальным - будет применение метода
        // int winsCount = Collections.frequency(statistics, true);
        // но в учебных целях мы подключили стороннюю библиотеку. так что ...
        int winsCount = CollectionUtils.cardinality(true, statistics);


        System.out.printf("%nКоличество игр: %s, Количество выигрышей: %s (%.2f %%).%n",
                statistics.size(), winsCount, (float) winsCount * 100 / statistics.size());
    }
}

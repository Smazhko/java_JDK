package sem5_multithread.runners;

/*
В рамках выполнения задачи необходимо:
    3 бегуна должны прийти к старту гонки
    Программа должна гарантировать, что гонка начнется только когда все три участника будут на старте
    Программа должна отсчитать “На старт”, “Внимание”, “Марш”
    Программа должна завершиться когда все участники закончат гонку.
    Подумайте об использовании примитива синхронизации
 */

import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

public class Main3 {

    // ВТОРОЙ ВАРИАНТ РЕШЕНИЯ с использованием wait() notify()
    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(3);

        AnotherRunner sportsman1 = new AnotherRunner(cdl);
        AnotherRunner sportsman2 = new AnotherRunner(cdl);
        AnotherRunner sportsman3 = new AnotherRunner(cdl);

        // Этот вариант будет работать с ANOTHER RUNNER.
        // Ждём, пока обнулится CDL - пока каждый из бегунов
        // не отнимет из CDL по единице.
        // CDL = 0 -> Командуем - на старт.
        // У каждого бегуна активируем метод go(),
        // который его разбудит, и бегун побежит.
        // Различия в методиках будет в том, что момент старта
        // для каждого бегуна будет обозначен разными событиями.
        // В этом варианте - будет производиться запуск каждого
        // бегуна в отдельности - "вручную" - по команде go().
        // В другом варианте - всех одновременно автоматически
        // запустит обнуление CDL.
        Thread anotherStarter = new Thread(() -> { // вместо new Runnable() можно использовать LAMBDA
            try {
                cdl.await();
                System.out.println("На старт!");
                sleep(800);
                System.out.println("Внимание!");
                sleep(800);
                System.out.println("Марш!");
                sportsman1.go();
                sportsman2.go();
                sportsman3.go();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        anotherStarter.start();

        sportsman1.start();
        sportsman2.start();
        sportsman3.start();
    }


    // ПЕРВЫЙ ВАРИНТ РЕШЕНИЯ ЧЕРЕЗ CDL = 4
    public static void main1(String[] args) {
        CountDownLatch cdl = new CountDownLatch(4);

        Runner sportsman1 = new Runner(cdl);
        Runner sportsman2 = new Runner(cdl);
        Runner sportsman3 = new Runner(cdl);

        Thread starter = new Thread(new Runnable() { // вместо new Runnable() можно использовать LAMBDA
            @Override
            public void run() {
                while (cdl.getCount() != 1) {
                    // в момент, когда все три бегуна пришли на старт
                    // и отняли из CDL по единице - бесконечный цикл
                    // ожидания бегунов заканчивается
                    try {
                        sleep(50);
                        // задержка sleep между итерациями для того,
                        // чтобы не нагружать процессор
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    System.out.println("На старт!");
                    sleep(800);
                    System.out.println("Внимание!");
                    sleep(800);
                    System.out.println("Марш!");
                    cdl.countDown();
                    // отнимание последней единицы из CDL - момент запуска всех бегунов
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        starter.start();

        sportsman1.start();
        sportsman2.start();
        sportsman3.start();
    }
}

/*
Спортсмен 2 выдвинулся на соревнования.
Спортсмен 3 выдвинулся на соревнования.
Спортсмен 1 выдвинулся на соревнования.
Спортсмен 2 прибыл к старту за 826 сек.
Спортсмен 3 прибыл к старту за 1623 сек.
Спортсмен 1 прибыл к старту за 1990 сек.
На старт!
Внимание!
Марш!
Спортсмен 2 стартовал.
Спортсмен 1 стартовал.
Спортсмен 3 стартовал.
Спортсмен 2 финишировал за 1005 сек.
Спортсмен 3 финишировал за 1057 сек.
Спортсмен 1 финишировал за 1301 сек.
 */
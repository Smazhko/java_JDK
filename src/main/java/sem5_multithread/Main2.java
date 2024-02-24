package sem5_multithread;

/*
В рамках выполнения задачи необходимо:
    Создайте два потока A и B.
    Поток A меняет значение Boolean переменной switcher с задержкой 1000 миллисекунд
    (true в состояние false и наоборот).
    Поток B ожидает состояния true переменной switcher и выводит на консоль обратный отсчет от 100
    с задержкой 100 миллисекунд и приостанавливает свое действие, как только поток A переключит switcher
    в состояние false.
    Условием завершения работы потоков является достижение отсчета нулевой отметки.
 */


import java.util.concurrent.atomic.AtomicBoolean;

//   В рамках выполнения задачи необходимо:
//    Создайте два потока A и B.
//    Поток A меняет значение Boolean переменной switcher с задержкой 1000 миллисекунд
//    (true в состояние false и наоборот).
//    Поток B ожидает состояния true переменной switcher и выводит на консоль обратный отсчет от 100
//    с задержкой 100 миллисекунд и приостанавливает свое действие, как только поток A переключит switcher
//    в состояние false.
//    Условием завершения работы потоков является достижение отсчета нулевой отметки.
public class Main2 {

    public static AtomicBoolean switcher = new AtomicBoolean(true);

    public static boolean work = true;

    public static void main(String[] args) {

        Thread changer = new Thread(()-> {
            while (work) {
                switcher.set(!switcher.get());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(switcher);
            }
        });

        Thread counter = new Thread(() ->{
            int count = 100;
            while (count > 0) {
                if (switcher.get()) {
                    System.out.println(count--);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            work = false;
        });

        changer.start();
        counter.start();



    }
}

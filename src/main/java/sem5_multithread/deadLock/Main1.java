package sem5_multithread.deadLock;

/*
В рамках выполнения задачи необходимо:
    Создать два класса ObjectA, ObjectB
    Реализовать класс в котором два потока при запуске провоцируют DeadLock для объектов ObjectA, ObjectB
 */

public class Main1 {
    public static void main(String[] args) {
        ObjectA objectA = new ObjectA();
        ObjectB objectB = new ObjectB();

        Thread thr1 = new Thread(() -> {
            synchronized (objectA) {
                System.out.println("Занят объект A, ждём объект Б");
                synchronized (objectB) {
                    System.out.println("Занят объект Б");
                }
            }
        });

        Thread thr2 = new Thread(() -> {
            synchronized (objectB) {
                System.out.println("Занят объект B, ждём объект A");
                synchronized (objectA) {
                    System.out.println("Занят объект A");
                }
            }
        });


        thr1.start();
        thr2.start();

    }
}


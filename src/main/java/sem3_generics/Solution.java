package sem3_generics;

/*
Создать обобщенный класс с тремя параметрами (T, V, K).
Класс содержит три переменные типа (T, V, K),
конструктор, принимающий на вход параметры типа (T, V, K),
методы возвращающие значения трех переменных.

Создать метод, выводящий на консоль имена классов для трех переменных класса.
Наложить ограничения на параметры типа:
T должен реализовать интерфейс Comparable,
V должен реализовать интерфейс DataInput и расширять класс InputStream,
K должен расширять класс Number.
 */

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;

public class Solution<K extends Number, T extends Comparable<T>, V extends InputStream & DataInput> {
    private K keeper;
    private T time;
    private V volume;

    public T getTime() {
        return time;
    }

    public V getVolume() {
        return volume;
    }

    public K getKeeper() {
        return keeper;
    }

    public void getClasses(){
        System.out.printf( "K: %s %n" +
                "T: %s %n" +
                "V: %s %n", keeper.getClass().getSimpleName(),
                time.getClass().getSimpleName(),
                volume.getClass().getSimpleName());
    }
}

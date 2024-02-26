package sem3_generics_home;

/*
Напишите обобщенный класс Pair, который представляет собой пару значений разного типа.
Класс должен иметь методы getFirst(), getSecond() для получения значений каждого из составляющих пары,
а также переопределение метода toString(), возвращающее строковое представление пары.
 */

public class PairMain{

    public static void main(String[] args) {
        Pair<Integer, String> myPair = new Pair<>(123, "какая-то строка");
        Integer id = myPair.getFirst();
        String line = myPair.getSecond();

        System.out.println(id);
        System.out.println(line);

        System.out.println(myPair);
    }

}

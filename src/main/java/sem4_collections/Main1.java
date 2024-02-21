package sem4_collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
В рамках выполнения задачи необходимо:
1. Создайте коллекцию мужских и женских имен с помощью интерфейса List
2. Отсортируйте коллекцию в алфавитном порядке
3. Отсортируйте коллекцию по количеству букв в слове
4. Разверните коллекцию
 */
public class Main1 {
    public static void main(String[] args) {
        List<String> list = createList();
        System.out.println(sortByAlphabet(list));
        System.out.println(sortByLength(list));
        System.out.println(reverse(list));
    }

    static List<String> reverse(List<String> list) {
        return list.stream().sorted(Comparator.reverseOrder()).toList();
    }

    static List<String> sortByLength(List<String> list) {
        return list.stream().sorted(Comparator.comparingInt(String::length)).toList();
    }

    static List<String> sortByAlphabet(List<String> list) {
        list.sort((e1, e2) -> e1.compareTo(e2));
        return list;
    }

    static List<String> createList(){
        List<String> list = new ArrayList<>();
        list.add("Костя");
        list.add("Мария");
        list.add("Святослав");
        list.add("Кирилл");
        list.add("Семен");
        list.add("Светлана");
        return list;
    }
}

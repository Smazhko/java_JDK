package sem4_collections;

import java.util.*;
import java.util.stream.Collectors;

/*
В рамках выполнения задачи необходимо:
Создайте коллекцию мужских и женских имен с помощью интерфейса List - добавьте повторяющиеся значения
Получите уникальный список Set на основании List
Определите наименьший элемент (алфавитный порядок)
Определите наибольший элемент (по количеству букв в слове но в обратном порядке)
Удалите все элементы содержащие букву ‘A’
 */

public class Main2 {
    public static void main(String[] args) {
        List<String> nameList = createList();
        System.out.println(nameList);

        Set<String> nameSet = getAsSet(nameList);
        System.out.println(nameSet);

        String firstName = getFirst(nameSet);
        System.out.println(firstName);

        String longestName = getLongest(nameSet);
        System.out.println(longestName);

        Set<String> cuttedSet = cut(nameSet);
        System.out.println(cuttedSet);
    }

    private static Set<String> cut(Set<String> nameSet) {
//        return nameSet.stream().filter(s -> !s.toLowerCase().contains("а")).collect(Collectors.toSet());
        nameSet.removeIf(s -> s.toLowerCase().contains("а"));
        return nameSet;
    }

    static String getLongest(Set<String> nameSet) {
        return nameSet.stream().max((o1, o2) -> o1.length() - o2.length()).orElse("Пусто");
    }

    static String getFirst(Set<String> nameSet) {
//        TreeSet<String> tree = new TreeSet<>(nameSet);
//        return tree.first();
        return nameSet.stream().min((o1, o2) -> o1.compareTo(o2)).orElse("Пусто");

    }

    static Set<String> getAsSet(List<String> list) {
        return new HashSet<>(list);
    }


    static List<String> createList(){
        List<String> list = new ArrayList<>();
        list.add("Костя");
        list.add("Мария");
        list.add("Мария");
        list.add("Святослав");
        list.add("Кирилл");
        list.add("Кирилл");
        list.add("Семен");
        list.add("Светлана");
        list.add("Светлана");
        return list;
    }
}

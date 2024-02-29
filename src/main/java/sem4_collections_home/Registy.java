package sem4_collections_home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Registy {
    private List<Staffer> base;
    private int registryNumber = 1000;

    public Registy() {
        base = new ArrayList<>();

    }


    public void add(Staffer newStaffer) {
        base.add(newStaffer);
        newStaffer.setNumber(registryNumber);
        registryNumber++;
    }


    public String searchBySeniority(int seniority){
        List<Staffer> search;
        search = base.stream()
                .filter(staffer -> staffer.getSeniority() == seniority)
                .toList();
        return formResponse(search);
    }


    public String searchPhoneByName(String name){
        // получить из базы объектов LIST с определенными полями объектов
//        List<String> search;
//        search = base.stream()
//                .filter(staffer -> staffer.getName().toLowerCase().contains(name.toLowerCase()))
//                .map(Staffer::getPhone)
//                .toList();
//        System.out.println(search);

        // сформировать из поиска список STRINGов,
        // и собрать результат в итоговый STRING для возврата
        // Map<String, String> search;
        String search = base.stream()
                .filter(staffer -> staffer.getName().toLowerCase().contains(name.toLowerCase()))
                .map(staffer -> String.format("%-20s: %s", staffer.getName(), staffer.getPhone()))
                .collect(Collectors.joining("\n"));

        // сразу из потока вывести в консоль результаты поиска в нужном виде
        // но у нас метод, который возвращает строку
//        base.stream()
//                .filter(staffer -> staffer.getName().toLowerCase().contains(name.toLowerCase()))
//                .forEach(staffer -> System.out.printf("%-20s: %s%n", staffer.getName(), staffer.getPhone()));

        return search.isEmpty() ? "Поиск не дал результата" : search;
    }


    public String searchByNumber(int number){
        List<Staffer> search;
        search = base.stream()
                .filter(staffer -> staffer.getNumber() == number)
                .toList();
        return formResponse(search);
    }


    private String formResponse(List<Staffer> stafferList) {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%-5s | %-20s | %-3s | %s %n", "NN", "name", "sen", "phone"));
        if (stafferList.isEmpty())
            result.append("EMPTY ");
        else{
            for (Staffer staffer : stafferList) {
                result.append(String.format("%-5s | %-20s | %-3s | %s %n",
                        staffer.getNumber(), staffer.getName(), staffer.getSeniority(), staffer.getPhone()));
            }
        }
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }


    @Override
    public String toString() {
        return formResponse(base);
    }
}

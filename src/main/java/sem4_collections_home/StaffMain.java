package sem4_collections_home;
/*
Создать справочник сотрудников
Необходимо:
Создать класс справочник сотрудников, который содержит внутри
коллекцию сотрудников - каждый сотрудник должен иметь следующие атрибуты:
Табельный номер
Номер телефона
Имя
Стаж
Добавить метод, который ищет сотрудника по стажу (может быть список)
Добавить метод, который возвращает номер телефона сотрудника по имени (может быть список)
Добавить метод, который ищет сотрудника по табельному номеру
Добавить метод добавление нового сотрудника в справочник
 */

public class StaffMain {
    public static void main(String[] args) {
        Registy staff = new Registy();
        staff.add(new Staffer("Щеглов Дмитрий", "8-916-548-52-21", 13));
        staff.add(new Staffer("Волнова Ольга",  "8-928-248-51-78", 1));
        staff.add(new Staffer("Кузнецов Игорь", "8-912-265-87-92", 5));
        staff.add(new Staffer("Иванов Андрей",  "8-934-257-85-41", 9));
        staff.add(new Staffer("Меренко Ольга",  "8-999-888-77-66", 5));
        staff.add(new Staffer("Босов Иван",     "8-050-653-01-13", 1));
        staff.add(new Staffer("Дашко Анастасия","8-3652-25-13-03", 3));
        staff.add(new Staffer("Триванин Дмитрий","8-916-125-12-12", 2));
        staff.add(new Staffer("Варнава Ирина",  "8-916-657-21-56", 2));

        System.out.println("\nСписок сотрудников\n" + staff);

        System.out.println("\nПоиск сотрудников по стажу 5 лет");
        System.out.println(staff.searchBySeniority(5));

        System.out.println("\nПоиск телефонов по имени Иван");
        System.out.println(staff.searchPhoneByName("Иван"));

        System.out.println("\nПоиск сотрудника по табельному номеру 1002");
        System.out.println(staff.searchByNumber(1002));

        System.out.println("\nПустой поиск");
        System.out.println(staff.searchBySeniority(20));
        System.out.println(staff.searchPhoneByName("Кирилл"));
        System.out.println(staff.searchByNumber(1010));

    }
}

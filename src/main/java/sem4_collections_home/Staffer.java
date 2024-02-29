package sem4_collections_home;

public class Staffer {
    private int number;
    private String name;
    private String phone;
    private int seniority;

    public Staffer(String name, String phone, int seniority) {
        this.name = name;
        this.phone = phone;
        this.seniority = seniority;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getSeniority() {
        return seniority;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Сотрудник " +
                name +
                " (таб.№ " + number +
                "), стаж " + seniority +
                " лет, тел.:" + phone;
    }
}

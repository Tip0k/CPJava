package model;

import java.util.ArrayList;

public class CourierStatus {

    public static final String FREE = "Вільний";
    public static final String IN_WORK = "Зайнятий";
    public static final String UNKNOWN = "Невідомо";
    public static final String REWORK = "Переробляє";
    //...

    public static ArrayList<String> getAllStatuses() {
        ArrayList<String> s = new ArrayList<>();

        s.add(FREE);
        s.add(IN_WORK);
        s.add(REWORK);

        return s;
    }
}

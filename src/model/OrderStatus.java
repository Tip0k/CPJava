package model;

import java.util.ArrayList;

public class OrderStatus {

    public static final String DONE = "Виконано";
    public static final String IS_PERFORMED = "Виконується";
    public static final String UNKNOWN = "Невідомо";
    //...

    public static ArrayList<String> getAllStatuses() {
        ArrayList<String> s = new ArrayList<>();

        s.add(DONE);
        s.add(IS_PERFORMED);

        return s;
    }
}

package model;

public final class Regions {

    public static final String KHMELNYTSKYI = "Хмельницька обл.";
    public static final String VINNITSA = "Вінницька обл.";
    public static final String LVIV = "Львівська обл.";
    public static final String CHERNOVTSY = "Чернівецька обл.";
    public static final String KYIV = "Київська обл.";
    //...

    public static String[] getRegions() {
        return new String[]{
            KHMELNYTSKYI,
            VINNITSA,
            LVIV,
            CHERNOVTSY,
            KYIV,};
    }
}

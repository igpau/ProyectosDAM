package dam.java.classes;

import java.sql.Date;

public class Sale {
    private static String Song;
    private static String Card;
    private static Date Sale_date;

    public Sale(String Song, String Card, Date Sale_date) {
        this.Song = Song;
        this.Card = Card;
        this.Sale_date = Sale_date;
    }

    public Sale() {}

    public static String getSong() {
        return Song;
    }

    public static String getCard() {
        return Card;
    }

    public static Date getSale_date() {
        return Sale_date;
    }
}

package dam.java.classes;

public class Card {
    private String code;
    private String card_number;
    private String csv;
    private String exp_date;
    private int client;

    public Card(String code, String card_number, String csv, String exp_date, int client) {
        this.code = code;
        this.card_number = card_number;
        this.csv = csv;
        this.exp_date = exp_date;
        this.client = client;
    }

    public String getCode() {
        return code;
    }

    public String getCard_number() {
        return card_number;
    }

    public String getCsv() {
        return csv;
    }

    public String getExp_date() {
        return exp_date;
    }

    public int getClientID() {
        return client;
    }
}

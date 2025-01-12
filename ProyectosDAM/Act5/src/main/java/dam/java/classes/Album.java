package dam.java.classes;

public class Album {
    private static String code;
    private static String title;
    private static String artist;
    private static String year;
    private static String style;

    public Album(String code, String title, String artist, String year, String style) {
        this.code = code;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.style = style;
    }

    public Album() {}

    public static String getCode() {
        return code;
    }

    public static String getTitle() {
        return title;
    }

    public static String getArtist() {
        return artist;
    }

    public static String getYear() {
        return year;
    }

    public static String getStyle() {
        return style;
    }



}

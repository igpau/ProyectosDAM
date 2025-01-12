package dam.java.classes;

public class Song {
    private static String code;
    private static String title;
    private static String album;
    private static String track;

    public Song(String code, String title, String album, String track) {
        this.code = code;
        this.title = title;
        this.album = album;
        this.track = track;
    }

    public Song() {
    }

    public static String getCode() {
        return code;
    }

    public static String getTitle() {
        return title;
    }

    public static String getAlbum() {
        return album;
    }

    public static String getTrack() {
        return track;
    }
}

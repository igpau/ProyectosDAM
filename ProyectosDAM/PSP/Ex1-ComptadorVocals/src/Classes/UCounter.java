package Classes;

public class UCounter extends Thread {
    private int count;
    private String text;

    public UCounter(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == 'u' || text.charAt(i) == 'U') {
                count++;
            }
        }
        System.out.println("U count: " + count);
    }
}

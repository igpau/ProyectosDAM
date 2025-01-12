package Classes;

public class ACounter extends Thread {
    private int count;
    private String text;

    public ACounter(String text) {
        this.text = text;
        this.count = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == 'a' || text.charAt(i) == 'A') {
                count++;
            }
        }
        System.out.println("A count: " + count);
    }

    public int getCount() {
        return count;
    }


}

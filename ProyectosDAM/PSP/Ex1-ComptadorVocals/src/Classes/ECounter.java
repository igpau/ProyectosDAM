package Classes;

public class ECounter extends Thread{
    private int count;
    private String text;

    public ECounter(String text) {
        this.text = text;
        this.count = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == 'e' || text.charAt(i) == 'E') {
                count++;
            }
        }
        System.out.println("E count: " + count);
    }

    public int getCount() {
        return count;
    }

}

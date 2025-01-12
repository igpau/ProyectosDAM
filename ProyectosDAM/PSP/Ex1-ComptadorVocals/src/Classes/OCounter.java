package Classes;

public class OCounter extends Thread{
    private int count;
    private String text;

    public OCounter(String text) {
        this.text = text;
        this.count = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == 'o' || text.charAt(i) == 'O') {
                count++;
            }
        }
        System.out.println("O count: " + count);
    }

    public int getCount() {
        return count;
    }


}

package Classes;

public class ICounter extends Thread{
    private int count;
    private String text;

    public ICounter(String text) {
        this.text = text;
        this.count = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == 'i' || text.charAt(i) == 'I') {
                count++;
            }
        }
        System.out.println("I count: " + count);
    }

    public int getCount() {
        return count;
    }


}

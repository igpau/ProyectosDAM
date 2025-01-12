package dam.java.classes;

public class Client {
    private int clientID;
    private String name;
    private String surname1;
    private String surname2;
    private String region;

    public Client(int clientID, String name, String surname1, String surname2, String region) {
        this.clientID = clientID;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.region = region;
    }

    public Client() {
    }

    public int getClientID() {
        return clientID;
    }

    public String getName() {
        return name;
    }

    public String getSurname1() {
        return surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public String getRegion() {
        return region;
    }
}

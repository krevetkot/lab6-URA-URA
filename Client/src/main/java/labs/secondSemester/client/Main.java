package labs.secondSemester.client;

public class Main {
    public static void main(String[] args) {

        try {
            Client client = new Client("127.0.0.1");
            client.start();
        }

        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
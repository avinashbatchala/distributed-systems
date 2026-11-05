package ex4;

public class Exercise4 {
    static void main() {

        System.out.println(DatabaseClient.getRecord(4103));
        System.out.println(DatabaseClient.getRecord(41003));

        System.out.println("The size of db is " + DatabaseClient.getSize());

        System.out.println(DatabaseClient.addRecord(5000, "Distributed Systems"));
        System.out.println(DatabaseClient.addRecord(5001, "AI"));

        System.out.println(DatabaseClient.getRecord(5000));
        System.out.println(DatabaseClient.getRecord(5001));
    }
}

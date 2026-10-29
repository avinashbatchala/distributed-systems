package ex3;

import ex3.generated.LogMessage;

/**
 * Sends sample log and database requests to the Exercise 3 servers.
 *
 * @author Avinash Batchala
 * @author Bhargav Parvatha
 * @author Nithin Gangavarapu
 */
public class Exercise3 {

    /**
     * Sends two log messages and queries the database server.
     *
     * @param args unused
     * @throws Exception if a client call fails
     */
    public static void main(String[] args) throws Exception {

        String host = "localhost";
        int logPort = 9090;

        LogClient.send(
                host, logPort,
                LogMessage.Severity.INFO,
                "Exercise3",
                "Application started");

        LogClient.send(
                host, logPort,
                LogMessage.Severity.WARN,
                "Database",
                "Test warning");

        DatabaseClient databaseClient = new DatabaseClient("localhost", 9091);

        databaseClient.addRecord(0, "Test1");
        databaseClient.addRecord(76537, "Test2");

        System.out.println(databaseClient.getRecord(76537));
        System.out.println(databaseClient.getSize());
    }
}

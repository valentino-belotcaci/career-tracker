package VaLocaProject;

import java.sql.Connection;
import java.sql.DriverManager;

// NEEDS TO BE TESTED, SHOULD CHECK CONNECTION TO DB
public class TestDbConnection {
    public static void main(String[] args) {
        String host = System.getenv().getOrDefault("DB_HOST", "career-tracker-loca2gaming-18ad.g.aivencloud.com");
        String port = System.getenv().getOrDefault("DB_PORT", "13765");
        String db = System.getenv().getOrDefault("DB_NAME", "defaultdb");
        String user = System.getenv().getOrDefault("DB_USER", "avnadmin");
        String pass = System.getenv().getOrDefault("DB_PASSWORD", "AVNS_pQo1WsnuBn7vq6uQGBu");

        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, db);
        System.out.println("Testing connection to: " + url);
        try (Connection c = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Connected: " + c.getMetaData().getDatabaseProductName() + " " + c.getMetaData().getDatabaseProductVersion());
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

package database;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Const {
    private static String dbCredentialsPath = "src/main/resources/db_credentials.txt";
    public static String DB_HOST;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PASS;

    public Const() {
        try (Scanner scanner = new Scanner(new File(dbCredentialsPath))){
            DB_HOST = scanner.nextLine();
            DB_NAME = scanner.nextLine();
            DB_USER = scanner.nextLine();
            DB_PASS = scanner.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

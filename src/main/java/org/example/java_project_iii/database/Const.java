package org.example.java_project_iii.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class that handles database credentials
 */
public class Const {
    private static String dbCredentialsPath = "src/main/resources/db_credentials.txt";
    public static String DB_HOST;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PASS;

    /**
     * Tries to read credentials from a file
     */
    public Const() {
        try (Scanner scanner = new Scanner(new File(dbCredentialsPath))) {
            DB_HOST = scanner.nextLine();
            DB_NAME = scanner.nextLine();
            DB_USER = scanner.nextLine();
            DB_PASS = scanner.nextLine();
        } catch (FileNotFoundException e) {
            // harmless little exception, file will be created anyway if it's not there
            System.out.println("Creating db credentials file...");
        }
    }
}

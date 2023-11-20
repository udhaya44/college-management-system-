package index_page;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose Role:");
            System.out.println("1. Teacher");
            System.out.println("2. HOD");
            System.out.println("3. Admin");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    TeacherHandler.handle();
                    
                    break;
                case 2:
                    HODHandler.handle();
                    break;
                case 3:
                    AdminHandler.handle();
                    break;
                case 4:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }
}

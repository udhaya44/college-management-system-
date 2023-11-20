
package index_page;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import authentication.Authentication;
import doa.HODDAO;
import models.HOD;

public class AdminHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static HODDAO hodDAO = new HODDAO();
    private static Authentication auth = new Authentication();

    public static void handle() throws SQLException {
        System.out.println("Enter Admin Username:");
        String adminUsername = scanner.next();
        System.out.println("Enter Password:");
        String adminPassword = scanner.next();

        if (auth.authenticateAdmin(adminUsername, adminPassword)) {
            System.out.println("Authenticated as Admin!");
            handleAdminOperations();
        } else {
            System.out.println("Authentication failed.");
        }
    }

    private static void handleAdminOperations() throws SQLException {
        while (true) {
            System.out.println("Admin Operations:");
            System.out.println("1. View All HODs");
            System.out.println("2. Add a HOD");
            System.out.println("3. Edit HOD");
            System.out.println("4. Delete HOD");
            System.out.println("5. Go back");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    List<HOD> hods = hodDAO.getAllHODs();
                    for (HOD hod : hods) {
                        System.out.println(hod);
                    }
                    break;
                case 2:
                    System.out.println("Enter HOD Name:");
                    String hodName = scanner.next();
                    System.out.println("Enter Password:");
                    String password = scanner.next();
                    hodDAO.addHOD(new HOD(0, hodName, password));  // Assuming ID is auto-incremented
                    System.out.println("HOD added successfully!");
                    break;
                case 3:
                    System.out.println("Enter HOD ID to edit:");
                    int hodId = scanner.nextInt();
                    HOD hodToEdit = hodDAO.getHODById(hodId);
                    if (hodToEdit != null) {
                        System.out.println("Enter new name:");
                        hodToEdit.setName(scanner.next());

                        System.out.println("Enter new password:");
                        hodToEdit.setPassword(scanner.next());

                        hodDAO.updateHOD(hodToEdit);
                        System.out.println("HOD details updated!");
                    } else {
                        System.out.println("No HOD found with the given ID.");
                    }
                    break;
                case 4:
                    System.out.println("Enter HOD ID to delete:");
                    int idToDelete = scanner.nextInt();
                    hodDAO.deleteHOD(idToDelete);
                    System.out.println("HOD deleted successfully!");
                    break;
                case 5:
                    return;  // Exit to the main menu
            }
        }
    }
}

package index_page;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import authentication.Authentication;
import doa.TeacherDAO;
import models.Teacher;

public class HODHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static TeacherDAO teacherDAO = new TeacherDAO();
    private static Authentication auth = new Authentication();

    public static void handle() throws SQLException {
        System.out.println("Enter HOD Name:");
        String hodName = scanner.next();
        System.out.println("Enter Password:");
        String hodPassword = scanner.next();

        if (auth.authenticateHOD(hodName, hodPassword)) {
            System.out.println("Authenticated as HOD!");
            handleHODOperations();
        } else {
            System.out.println("Authentication failed.");
        }
    }

    private static void handleHODOperations() throws SQLException {
        while (true) {
            System.out.println("HOD Operations:");
            System.out.println("1. View All Teachers");
            System.out.println("2. Add a Teacher");
            System.out.println("3. Edit Teacher");
            System.out.println("4. Delete Teacher");
            System.out.println("5. Go back");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    List<Teacher> teachers = teacherDAO.getAllTeachers();
                    for (Teacher teacher : teachers) {
                        System.out.println(teacher);
                    }
                    break;
                case 2:
                    System.out.println("Enter Teacher Name:");
                    String teacherName = scanner.next();
                    System.out.println("Enter Branch:");
                    String branch = scanner.next();
                    teacherDAO.addTeacher(new Teacher(0, teacherName, branch));  // Assuming ID is auto-incremented
                    System.out.println("Teacher added successfully!");
                    break;
                case 3:
                    System.out.println("Enter Teacher ID to edit:");
                    int teacherId = scanner.nextInt();
                    Teacher teacherToEdit = teacherDAO.getTeacherById(teacherId);
                    if (teacherToEdit != null) {
                        System.out.println("Enter new name:");
                        teacherToEdit.setName(scanner.next());

                        System.out.println("Enter new branch:");
                        teacherToEdit.setBranch(scanner.next());

                        teacherDAO.updateTeacher(teacherToEdit);
                        System.out.println("Teacher details updated!");
                    } else {
                        System.out.println("No teacher found with the given ID.");
                    }
                    break;
                case 4:
                    System.out.println("Enter Teacher ID to delete:");
                    int idToDelete = scanner.nextInt();
                    teacherDAO.deleteTeacher(idToDelete);
                    System.out.println("Teacher deleted successfully!");
                    break;
                case 5:
                    return;  // Exit to the main menu
            }
        }
    }
}

package index_page;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import authentication.Authentication;
import doa.StudentDAO;
import models.Student;

public class TeacherHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentDAO studentDAO = new StudentDAO();
    private static Authentication auth = new Authentication();

    public static void handle() throws SQLException {
        System.out.println("Enter Teacher Name:");
        String teacherName = scanner.next();
        System.out.println("Enter Branch:");
        String branch = scanner.next();

        if (auth.authenticateTeacher(teacherName, branch)) {
            System.out.println("Authenticated as Teacher!");
            handleTeacherOperations(branch);
        } else {
            System.out.println("Authentication failed.");
        }
    }

	private static void handleTeacherOperations(String branch) throws SQLException {
	    while (true) {
	        System.out.println("Teacher Operations:");
	        System.out.println("1. View Students of My Branch");
	        System.out.println("2. Add Student to My Branch");
	        System.out.println("3. Edit Student of My Branch");
	        System.out.println("4. Delete Student from My Branch");
	        System.out.println("5. Go back");
	        int choice = scanner.nextInt();
	        switch (choice) {
	            case 1:
	                List<Student> students = studentDAO.getStudentsByBranch(branch);
	                for (Student student : students) {
	                    System.out.println(student);
	                }
	                break;
	            case 2:
	                System.out.println("Enter Student Name:");
	                String studentName = scanner.next();
	                System.out.println("Enter Age:");
	                int age = scanner.nextInt();
	                System.out.println("Enter Gender:");
	                String gender = scanner.next();
	                System.out.println("Enter City:");
	                String city = scanner.next();
	                int[] marks = {70, 80, 85, 75, 90}; 
	                studentDAO.addStudent(new Student(0, studentName, age, gender, city, branch, marks));
	                System.out.println("Student added to " + branch + " branch!");
	                break;
	            case 3:
	                System.out.println("Enter Student ID to edit:");
	                int studentId = scanner.nextInt();
	                Student studentToEdit = studentDAO.getStudentById(studentId);
	                if (studentToEdit != null && studentToEdit.getBranch().equals(branch)) {
	                    System.out.println("Editing Student: " + studentToEdit.getName());

	                    System.out.println("Enter Student Name:");
	                    String newName = scanner.next();

	                    System.out.println("Enter Age:");
	                    int newAge = scanner.nextInt();

	                    System.out.println("Enter Gender:");
	                    String newGender = scanner.next();

	                    System.out.println("Enter City:");
	                    String newCity = scanner.next();
	                    
                        System.out.println("Enter new marks (comma separated for 5 subjects):");
                        String[] marksStr = scanner.next().split(",");
                        int[] newMarks = new int[5];
                        for (int i = 0; i < 5; i++) {
                            newMarks[i] = Integer.parseInt(marksStr[i]);
                        }
	                    Student updatedStudent = new Student(studentId, newName, newAge, newGender, newCity, branch, newMarks);
	                    studentDAO.updateStudent(updatedStudent);
	                    System.out.println("Student details updated!");

	                } else {
	                    System.out.println("No student found or not authorized to edit!");
	                }
	                break;

	            case 4:
	                System.out.println("Enter Student ID to delete:");
	                int idToDelete = scanner.nextInt();
	                Student studentToDelete = studentDAO.getStudentById(idToDelete);
	                if (studentToDelete != null && studentToDelete.getBranch().equals(branch)) {
	                    studentDAO.deleteStudent(idToDelete);
	                    System.out.println("Student deleted from " + branch + " branch!");
	                } else {
	                    System.out.println("No student found or not authorized to delete!");
	                }
	                break;
	            case 5:
	                return;  // Exit to the teacher's menu
    	        }
    	    }
    	}

    }



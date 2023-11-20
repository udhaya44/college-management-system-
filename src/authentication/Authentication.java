package authentication;

import java.sql.SQLException;
import java.util.List;

import doa.AdminDAO;
import doa.HODDAO;
import doa.TeacherDAO;
import models.Teacher;

public class Authentication {

    private TeacherDAO teacherDAO = new TeacherDAO();
    private HODDAO hodDAO = new HODDAO();
    private AdminDAO adminDAO = new AdminDAO();

    // Authenticate Teacher by name and branch
    public boolean authenticateTeacher(String name, String branch) throws SQLException {
        return teacherDAO.authenticateTeacher(name,branch);
    }

    // Authenticate HOD using name and password
    public boolean authenticateHOD(String name, String password) throws SQLException {
        return hodDAO.authenticateHOD(name, password);
    }

    // Authenticate Admin using username and password
    public boolean authenticateAdmin(String username, String password) throws SQLException {
        return adminDAO.authenticateAdmin(username, password);
    }
}


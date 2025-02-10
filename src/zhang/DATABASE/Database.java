package zhang.DATABASE;

import java.util.ArrayList;
import zhang.COURSE.Course;
import zhang.USERS.Admin;
import zhang.USERS.User;
import zhang.UTILS.Utils;

public class Database {
    
    public static ArrayList<Course> courses = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static User currentUser = null; // Track logged-in user

    private static final String COURSE_FILE = "src/zhang/DATA/course.ser";
    private static final String STUDENT_FILE = "src/zhang/DATA/students.ser";
    private static final String COURSE_CSV = "src/zhang/DATA/MyUniversityCourses.csv";
    
    
    // Method to initialize database: Load from files or CSV if necessary
    public static void initialize() {
        // Load courses/users
    	loadCourses();
    	loadUsers();
        
    	// create admin if necessary
        ensureAdminExists();
    }

    public static void loadCourses() {
    	Utils.deserializeCourses(COURSE_FILE, COURSE_CSV);
    }

    public static void saveCourses() {
        Utils.serializeCourses(courses, COURSE_FILE);
    }

    public static void loadUsers() {
        users = Utils.deserializeUsers(STUDENT_FILE);
    }

    public static void saveUsers() {
        Utils.serializeUsers(users, STUDENT_FILE);
    }

    public static void addCourse(Course course) {
        courses.add(course);
        saveCourses();
    }

    public static void removeCourse(String courseId) {
        courses.removeIf(course -> course.getId().equals(courseId));
        saveCourses();
    }

    public static void addStudent(User user) {
        users.add(user);
        saveUsers();
    }

    public static void removeStudent(String username) {
        users.removeIf(student -> student.getUsername().equals(username));
        saveUsers();
    }

    // Display all courses
    public static void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course course : courses) {
                System.out.println(course.getName() + " (ID: " + course.getId() + ")");
            }
        }
    }

    // Display all students
    public static void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No students registered.");
        } else {
            for (User user : users) {
                System.out.println(user.getFirstName() + " " + user.getLastName() + " (Username: " + user.getUsername() + ")");
            }
        }
    }
    
    private static void ensureAdminExists() {
        boolean adminExists = false;

        for (User user : users) {// search through users to see if admin exists
            if (user instanceof Admin) {
                adminExists = true;
                break;
            }
        }

        if (!adminExists) {// create admin if none
            System.out.println("No admin found. Creating default admin account...");
            Admin defaultAdmin = new Admin("Admin", "Admin001", "System", "Admin");
            users.add(defaultAdmin);
            saveUsers();
            System.out.println("Admin user created: Username: Admin, Password: Admin001");
        }
    }
}


package zhang.USERS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import zhang.COURSE.Course;
import zhang.DATABASE.*;
import zhang.INTERFACES.AdminInterface;

public class Admin extends User implements AdminInterface, Serializable {
	
    private static final long serialVersionUID = 1L;

	public Admin(String username, String password, String firstName, String lastName) {
        super();  // Call the default User constructor
        this.setUsername(username);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

	@Override
	public void createCourse(String name, String id, int maxStudents, int currentStudentCount, String instructor,
			int sectionNum, String location) {
		Course course = new Course(name, id, maxStudents, currentStudentCount, instructor, sectionNum, location);
		Database.addCourse(course);
		Database.saveCourses();
	}

	@Override
	public void deleteCourse(String id) {
		Database.removeCourse(id);
		Database.saveCourses();
		// null pointers in students course lists?
	}

	@Override
	public void editCourse(String id, int maxStudents, String instructor, int sectionNum,
			String location) {
		for (Course course: Database.courses) { // find specific course and pass in new values
			if (course.getId().equals(id)) {
				course.setMaxStudents(maxStudents);
				course.setInstructor(instructor);
				course.setSectionNum(sectionNum);
				course.setLocation(location);
				System.out.println("New max students: " + course.getMaxStudents());
				System.out.println("New instructor: " + course.getInstructor());
				System.out.println("New section num: " + course.getSectionNum());
				System.out.println("New location: " + course.getLocation());
			}
		}
		Database.saveCourses(); // save changes
	}

	@Override
	public void displayCourse(String id) {
		for (Course course: Database.courses) {
			if (course.getId().equals(id)) {
				System.out.println("Name: " + course.getName());
				System.out.println("Course ID: " + course.getId());
				System.out.println("Max students: " + course.getMaxStudents());
				System.out.println("Current students: " + course.getCurrentStudentCount());
				System.out.println("Instructor: " + course.getInstructor());
				System.out.println("Section number: " + course.getSectionNum());
				System.out.println("Location: " + course.getLocation());
				System.out.println("Students: ");
				for (Student student: course.getListOfStudents()) {
					System.out.println(student);
				}
				return;
			}
		}
		System.out.println("That course does not exist");
	}

	@Override
	public void registerStudent(String username, String password, String firstName, String lastName) {
		Student student = new Student(username, password, firstName, lastName);
		Database.addStudent(student);
		Database.saveUsers();
		Database.saveCourses();
	}

	@Override
	public void viewFullCourses() {
		for (Course course: Database.courses) {// if current student count equals to max then at capacity
			if (course.getMaxStudents() == course.getCurrentStudentCount()) {
				System.out.println(course.getName());
			}
		}
	}
	
	@Override
	public void viewAllCourses() {
		for (Course course: Database.courses) {
			System.out.println(course.getName());
		}
	}

	@Override
	public void writeToFileFullCourse() {
		ArrayList<Course> fullCourses = new ArrayList<>();// arraylist to keep full courses
		
		for (Course course: Database.courses) {
			if (course.getMaxStudents() == course.getCurrentStudentCount()) {
				fullCourses.add(course);
			}
		}
		
		try (BufferedWriter bf = new BufferedWriter(new FileWriter("src/zhang/DATA/full_courses.txt"))) {
			for (Course course: fullCourses) {// write each course in fullCourses to file
				bf.write(course.getName() + "\n");
			}
			System.out.println("File created at src/zhang/DATA/full_courses.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void viewStudentsInCourse(String id) {
		for (Course course: Database.courses) {// find course based on ID
			if (course.getId().equals(id)) {
				if (course.getListOfStudents().isEmpty()) {// check if empty
					System.out.println("No students in this course");
					return;
				}
				for (Student student: course.getListOfStudents()) {// loop through all students
					System.out.println(student.getFirstName() + " " + student.getLastName());
				}
			}
		}
		
	}

	@Override
	public void viewStudentsClasses(String firstName, String lastName) {
		for (User user: Database.users) {// find specifc student
			if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)) {
				user = (Student)user;
				if (user.getCourses().isEmpty()) {
					System.out.println("Student not registered in any courses");
					return;
				}
				for (Course course: user.getCourses()) {// print courses if student registered in any
					System.out.println(course.getName());
				}
			}
		}
	}

	@Override
	public void sort() {
	    // Sort courses in descending order by student count
	    Database.courses.sort((c1, c2) -> Integer.compare(c2.getCurrentStudentCount(), c1.getCurrentStudentCount()));

	    System.out.println("Courses have been sorted");
	}


}

package zhang.USERS;

import java.io.Serializable;

import zhang.COURSE.Course;
import zhang.DATABASE.Database;
import zhang.INTERFACES.StudentInterface;

public class Student extends User implements StudentInterface, Serializable {
	
	private static final long serialVersionUID = 1L;

	public Student(String username, String password, String firstName, String lastName) {
		this.setUsername(username);
		this.setPassword(password);;
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}
	
	@Override
	public void viewAllCourses() {
		for (Course course: Database.courses) {
			System.out.println(course.getName());
		}
	}

	@Override
	public void viewNotFullCourses() {// loop through all courses to find which have reached max student count
		for (Course course: Database.courses) {
			if (course.getMaxStudents() > course.getCurrentStudentCount()) {
				System.out.println(course.getName());
			}
		}
	}

	@Override
	public void registerInNotFullCourse(String courseName, int section, String first, String last) {
	    for (Course course : Database.courses) {
	        if (course.getName().equals(courseName) && (course.getMaxStudents() > course.getCurrentStudentCount()) && 
	            !course.getListOfStudents().contains((Student) Database.currentUser)) {// course name match, not max capacity and student not in course already
	            
	            // Add student to the course
	            course.addStudent((Student) Database.currentUser);

	            // Add course to student's list inside the actual Student object
	            Student student = (Student) Database.currentUser;
	            student.getCourses().add(course);
	            
	            // changes saved to serialized files
	            Database.saveCourses();
	            Database.saveUsers();
	            
	            System.out.println(student.getFirstName() + " " + student.getLastName() + " has been registered for " + courseName);
	            return;

	        } else if (course.getName().equals(courseName) && (course.getMaxStudents() <= course.getCurrentStudentCount())) {
	            System.out.println("Course at max capacity");
	            return;

	        } else if (course.getName().equals(courseName) && course.getListOfStudents().contains((Student) Database.currentUser)) {
	            System.out.println("Student already registered in this course");
	            return;
	        }
	    }
	    System.out.println("Course " + courseName + " not found.");
	}

	@Override
	public void WithdrawFromCourse(String courseName, String first, String last) {
	    Student student = (Student) Database.currentUser;
	    Course courseToRemove = null;

	    // Iterate over the student's own courses
	    for (Course course : student.getCourses()) {
	        if (course.getName().equals(courseName)) {
	            courseToRemove = course;
	            break;
	        }
	    }

	    if (courseToRemove == null) {
	        System.out.println("You are not enrolled in " + courseName);
	        return;
	    }

	    // Remove student from the course's student list
	    courseToRemove.deleteStudent(student);

	    // Remove course from student's course list safely
	    student.getCourses().remove(courseToRemove);

	    // Save changes
	    Database.saveCourses();
	    Database.saveUsers();

	    System.out.println(student.getFirstName() + " " + student.getLastName() + " has withdrawn from " + courseName);
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void viewRegisteredCourses() {
	    for (Course course : this.getCourses()) {// each course in this students course list
	    	System.out.println(course.getName());
	    }
	}

	

}

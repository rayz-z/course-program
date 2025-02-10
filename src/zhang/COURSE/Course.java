package zhang.COURSE;

import java.io.Serializable;
import java.util.*;
import zhang.USERS.Student;

public class Course implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String id;
	private int maxStudents;
	private int currentStudentCount;
	private ArrayList<Student> listOfStudents;
	private String instructor;
	private int sectionNum;
	private String location;
	
	public Course(String name, String id, int maxStudents, int currentStudentCount, 
			String instructor, int sectionNum, String location) {
		this.listOfStudents = new ArrayList<Student>();
		this.name = name;
		this.id = id;
		this.maxStudents = maxStudents;
		this.currentStudentCount = currentStudentCount;
		this.instructor = instructor;
		this.sectionNum = sectionNum;
		this.location = location;
	}
	
	public void displayCourse() {
		System.out.println("Name: " + this.name);
		System.out.println("ID: " + this.id);
		System.out.println("Max Students: " + this.maxStudents);
		System.out.println("Current Students: " + this.currentStudentCount);
		System.out.println("Instructor: " + this.instructor);
		System.out.println("Section Num: " + this.sectionNum);
		System.out.println("Location: " + this.location);
	}
	
	public void addStudent(Student student) {
		if (this.currentStudentCount < this.maxStudents) {// check if at capacity
			this.listOfStudents.add(student);
			this.currentStudentCount++;
			System.out.println("Student added successfully.");
            return;
		}
		System.out.println("Course at max capacity.");		
	}
	
	public void deleteStudent(Student student) {
	    for (Student studentI: this.listOfStudents) {// check course's list of students
	        if (studentI.equals(student)) {
	            this.listOfStudents.remove(studentI); 
	            this.currentStudentCount--;
	            System.out.println("Student removed successfully.");
	            return;
	        }
	    }
		System.out.println("Student not found in this course.");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}

	public int getCurrentStudentCount() {
		return currentStudentCount;
	}

	public void setCurrentStudentCount(int currentStudentCount) {
		this.currentStudentCount = currentStudentCount;
	}

	public ArrayList<Student> getListOfStudents() {
		return listOfStudents;
	}

	public void setListOfStudents(ArrayList<Student> listOfStudents) {
		this.listOfStudents = listOfStudents;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public int getSectionNum() {
		return sectionNum;
	}

	public void setSectionNum(int sectionNum) {
		this.sectionNum = sectionNum;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}

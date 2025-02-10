package zhang.MAIN;

import java.util.Scanner;

import zhang.COURSE.Course;
import zhang.DATABASE.Database;
import zhang.USERS.*;

public class Zhang {
	
	public static void main(String[] args) {
		
		Database.initialize();// initialize all data from serialized/CSV files
		
		boolean flag = true;
		Scanner scan = new Scanner(System.in);
		while (flag) {// flag check to continue/quit program
			loginPage();
	        if (Database.currentUser == null) {
	            System.out.println("Login failed. Exiting...");
	            return;
	        }
		
			if (Database.currentUser instanceof Admin) {// check if current user is admin/student
				System.out.println("Welcome admin!");
			} else {
				System.out.println("Welcome student!");
			}
			while ((Database.currentUser != null) && (Database.currentUser instanceof Admin)){
				adminOptions();// provides admin options if admin logged in
			}
			
			while ((Database.currentUser != null) && (Database.currentUser instanceof Student)){
				studentOptions();// provides student options if student logged in
			}
			
			// save changes after logged out
			Database.saveCourses();
			Database.saveUsers();
			
			System.out.println("quite program? Answer Y or N");// quite program check
			String ans = scan.nextLine();
			if (ans.equals("Y")) {
				flag = false;
				break;
			}// if not quite loops back to log in
		}
	}
	
	public static void loginPage() {// login menu
		Scanner scan = new Scanner(System.in);
		System.out.println("Username: ");
		String user = scan.nextLine();
		System.out.println("Password: ");
		String pass = scan.nextLine();
		
		for (int i = 0; i < Database.users.size(); i++) {// check to set the current user
			if ((Database.users.get(i).getUsername().equals(user)) && 
					(Database.users.get(i).getPassword().equals(pass))){
				Database.currentUser = Database.users.get(i);
			}
		}
	}

	public static void adminOptions() {
	    Scanner scan = new Scanner(System.in);
	    Admin user = (Admin)Database.currentUser;// cast user to admin class to use admin methods
	    
	    while (true) {  // Keep looping until the admin chooses to exit
	        System.out.println("\nPlease choose an option by entering the corresponding number:");
	        System.out.println("1: Create a new course");
	        System.out.println("2: Delete a course");
	        System.out.println("3: Edit a course");
	        System.out.println("4: Display information for a given course");
	        System.out.println("5: Register a student");
	        System.out.println("6: View all courses");
	        System.out.println("7: View all courses that are FULL");
	        System.out.println("8: Write to a file the list of courses that are full");
	        System.out.println("9: View the names of students in a specific course");
	        System.out.println("10: View the list of courses a student is registered in");
	        System.out.println("11: Sort courses");
	        System.out.println("12: Exit");

	        int option = scan.nextInt();
	        scan.nextLine();  // Consume newline left-over

	        switch (option) {
	            case 1:// add a new course
	                System.out.println("Enter course name:");
	                String name = scan.nextLine();
	                System.out.println("Enter course ID:");
	                String id = scan.nextLine();
	                System.out.println("Enter max students:");
	                int maxStudents = scan.nextInt();
	                scan.nextLine();  // Consume newline
	                System.out.println("Enter instructor name:");
	                String instructor = scan.nextLine();
	                System.out.println("Enter section number:");
	                int section = scan.nextInt();
	                scan.nextLine();
	                System.out.println("Enter location:");
	                String location = scan.nextLine();
	                
	                Course newCourse = new Course(name, id, maxStudents, 0, instructor, section, location);
	                Database.addCourse(newCourse);
	                System.out.println("New course added successfully!");
	                break;

	            case 2:// delete a course
	                System.out.println("Enter the Course ID to delete:");
	                String courseIdToDelete = scan.nextLine();
	                Database.removeCourse(courseIdToDelete);
	                System.out.println("Course deleted");
	                break;

	            case 3:// edit a course
	            	Course res = null;
	                System.out.println("Enter the course ID of the course you want to edit:");
	                String editId = scan.nextLine();
	                
	                for (Course course: Database.courses) {
	                	if (course.getId().equals(editId)) {
	                		res = course;
	                	}
	                }
	                
	                // check if want to edit for each editable element
	                int newMaxStudents;
	                System.out.println("Edit max students? (Y/N) ");
	                String ans1 = scan.nextLine();
	                if (ans1.equals("Y")) {
	                	System.out.println("Enter new max students:");
		                newMaxStudents = scan.nextInt();
		                scan.nextLine();
	                } else {
	                	newMaxStudents = res.getMaxStudents();
	                }
	                
	                String newInstructor;
	                System.out.println("Edit instructor? (Y/N) ");
	                String ans2 = scan.nextLine();
	                if (ans2.equals("Y")) {
	                	System.out.println("Enter new instructor:");
		                newInstructor = scan.nextLine();
	                } else {
	                	newInstructor = res.getInstructor();
	                }
	                
	                int newSection;
	                System.out.println("Edit section number? (Y/N) ");
	                String ans3 = scan.nextLine();
	                if (ans3.equals("Y")) {
	                	System.out.println("Enter new section number:");
	                	newSection = scan.nextInt();
	                	scan.nextLine();
	                } else {
	                	newSection = res.getSectionNum();
	                }
	                
	                String newLocation;
	                System.out.println("Edit location? (Y/N) ");
	                String ans4 = scan.nextLine();
	                if (ans4.equals("Y")) {
	                	System.out.println("Enter new location:");
	                	newLocation = scan.nextLine();
	                } else {
	                	newLocation = res.getLocation();
	                }
	                
	                // set the new edits
	                user.editCourse(editId, newMaxStudents, newInstructor, newSection, newLocation);
	                break;

	            case 4:// display a course
	                System.out.println("Enter Course ID:");
	                String courseId = scan.nextLine();
	                user.displayCourse(courseId);
	                break;

	            case 5:// create a student
	                System.out.println("Enter student first name:");
	                String firstName = scan.nextLine();
	                System.out.println("Enter student last name:");
	                String lastName = scan.nextLine();
	                System.out.println("Enter student username:");
	                String username = scan.nextLine();
	                System.out.println("Enter student password:");
	                String password = scan.nextLine();

	                Student newStudent = new Student(username, password, firstName, lastName);
	                Database.addStudent(newStudent);
	                System.out.println("Student registered successfully!");
	                break;

	            case 6:// display all courses
	                Database.displayAllCourses();
	                break;

	            case 7:// display all the courses that are full
	                System.out.println("Full courses:");
	                user.viewFullCourses();
	                break;

	            case 8:// write to a file all the full courses
	                user.writeToFileFullCourse();
	                break;

	            case 9:// view all the students in a course
	                System.out.println("Enter Course ID:");
	                String courseID = scan.nextLine();
	                user.viewStudentsInCourse(courseID);
	                break;

	            case 10:// view a student's classes
	                System.out.println("Enter Student First Name:");
	                String first = scan.nextLine();
	                System.out.println("Enter Student Last Name:");
	                String last = scan.nextLine();
	                user.viewStudentsClasses(first, last);
	                break;

	            case 11:// sort the courses by enrollment
	                user.sort();
	                break;

	            case 12:// log out
	                System.out.println("Exiting admin panel...");
	                Database.currentUser = null;
	                return;

	            default:
	                System.out.println("Invalid option. Please enter a number between 1 and 12.");
	        }
	    }
	}

	
	public static void studentOptions() {
		Scanner scan = new Scanner(System.in);
	    Student user = (Student)Database.currentUser;// cast current user to student class to use student methods
	    
	    while (true) {  // Keep looping until the student chooses to exit
	        System.out.println("\nPlease choose an option by entering the corresponding number:");
	        System.out.println("1: View all courses"); // works
	        System.out.println("2: View all courses that are NOT full"); // works
	        System.out.println("3: Register in a course"); // works
	        System.out.println("4: Withdraw from a course"); // works
	        System.out.println("5: View the list of courses you are registered in"); // works
	        System.out.println("6: Exit"); // works

	        int option = scan.nextInt();
	        scan.nextLine();  // Consume newline left-over

	        switch (option) {
	            case 1:// display all courses
	                Database.displayAllCourses();
	                break;
	            	
	            case 2:// display courses that aren't full
	                System.out.println("Not full courses:");
	            	user.viewNotFullCourses();
	            	break;
	            	
	            case 3:// register in a course
	            	System.out.println("Enter course name: ");
	            	String name = scan.nextLine();
	            	System.out.println("Enter course section: ");
	            	int section = scan.nextInt();
	            	System.out.println("Enter your first name: ");
	            	String first = scan.nextLine();
	            	System.out.println("Enter your last name: ");
	            	String last = scan.nextLine();
	            	
	            	Course registerCourse = null;
	            	for (Course course: Database.courses) {
	        			if (course.getName().equals(name)) {
	        				registerCourse = course;
	        			}
	        		}
	            	// check if section is valid
	            	if (section <= registerCourse.getSectionNum()) {
		            	user.registerInNotFullCourse(name, section, first, last);;
	            	} else {
	            		System.out.println("No such class");
	            	}
	            	break;
	            	
	            case 4:// withdraw from a course
	            	System.out.println("What course do you want to withdraw from? ");
	            	String ans = scan.nextLine();
	            	System.out.println("What's your first name? ");
	            	String firstName = scan.nextLine();
	            	System.out.println("What's your last name? ");
	            	String lastName = scan.nextLine();
	            	user.WithdrawFromCourse(ans, firstName, lastName);
	            	break;
	            
	            case 5:// display all a student's courses
	            	user.viewRegisteredCourses();
	            	break;
	            	
	            case 6:// log out
	                System.out.println("Exiting student panel...");
	                Database.currentUser = null;
	                return;
	            	
	            default:
	                System.out.println("Invalid option. Please enter a number between 1 and 12.");
	        }
	    }
	}

}

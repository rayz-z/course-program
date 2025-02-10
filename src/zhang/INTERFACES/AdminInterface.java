package zhang.INTERFACES;

public interface AdminInterface {
	
	public abstract void createCourse(String name, String id, 
			int maxStudents, int currentStudentCount, 
			String instructor, int sectionNum, String location);
	
	public abstract void deleteCourse(String id);
	
	public abstract void editCourse(String id, int maxStudents, 
			String instructor, int sectionNum, String location);
	
	public abstract void displayCourse(String id);
	
	public abstract void registerStudent(String username, String password, 
			String firstName, String lastName);
	
	public abstract void viewFullCourses();
	
	public abstract void writeToFileFullCourse();
	
	public abstract void viewStudentsInCourse(String filePath);
	
	public abstract void viewStudentsClasses(String firstName, String lastName);
	
	public abstract void sort();

}

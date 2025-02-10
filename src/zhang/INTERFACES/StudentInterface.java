package zhang.INTERFACES;

public interface StudentInterface {
	
	public abstract void viewNotFullCourses();
	
	public abstract void registerInNotFullCourse(String courseName, int section,
			String first, String last);
	
	public abstract void WithdrawFromCourse(String courseName, String first, String last);
	
	public abstract void viewRegisteredCourses();

}

package zhang.UTILS;

import java.io.*;
import java.util.ArrayList;
import zhang.COURSE.*;
import zhang.DATABASE.Database;
import zhang.USERS.User;

public class Utils {
	
	public static void readCSV(String filePath) {
		
		try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
			String line;
			boolean isHeader = true; // tracks line as CSV header
			
			while ((line = in.readLine()) != null) {
				if (isHeader) {
					isHeader = false;
					continue;
				}
				String[] values = line.split(",");

				if (values.length == 8) { // each course has 8 values
					try {
						Course course = new Course( // course object created with specifics
								values[0],
								values[1],
								Integer.parseInt(values[2].trim()),
								Integer.parseInt(values[3].trim()),
								values[5],
								Integer.parseInt(values[6].trim()),
								values[7]);
						Database.courses.add(course); // add new course object to database
					} catch (NumberFormatException e) {
                        System.err.println("Error: Invalid number format in row: " + line);
                    }
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static boolean serializeCourses(ArrayList<Course> courseList, String filePath) {
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
	        out.writeObject(courseList);
	        System.out.println("Course list successfully serialized.");
	        return true;
	    } catch (IOException e) {
	        System.err.println("Error serializing courses: " + e.getMessage());
	        return false;
	    }
	}
	
	@SuppressWarnings("unchecked")
	public static void deserializeCourses(String serFile, String csvFile) {
	    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serFile))) {
	        Object obj = in.readObject();
	        if (obj instanceof ArrayList<?>) { // just check if object is an arraylist
	            ArrayList<?> tempList = (ArrayList<?>) obj;
	            if (!tempList.isEmpty() && tempList.get(0) instanceof Course) {
	                Database.courses = (ArrayList<Course>)tempList; // cast the arraylist to specifcally course arraylist
	            } else {
	                System.err.println("Warning: File does not contain valid Course objects.");
	            }
	        } else {
	            System.err.println("Error: File format is invalid.");
	        }
	    } catch (FileNotFoundException e) {
	        System.err.println("File not found: " + serFile + ". Reading CSV.");
	        readCSV(csvFile);
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void serializeUsers(ArrayList<User> userList, String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
        	out.writeObject(userList);
        	System.out.println("User list has been serialized to the filepath");

           } catch (IOException e) {
               e.printStackTrace();
           }
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<User> deserializeUsers(String filepath) {
		ArrayList<User> data = new ArrayList<>();
		
		try (FileInputStream fileIn = new FileInputStream(filepath); 
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			Object obj = in.readObject();
			
			if (obj instanceof ArrayList<?>) { // just check if object is an arraylist
                ArrayList<?> tempList = (ArrayList<?>) obj;

                // Ensure every element inside the list is a Course
                if (!tempList.isEmpty() && tempList.get(0) instanceof User) {
                    data = (ArrayList<User>) tempList; // Safe cast to user arraylist
                    System.out.println("Successfully deserialized users from: " + filepath);
                } else {
                    System.err.println("Warning: File does not contain a valid list of User objects.");
                }
            } else {
                System.err.println("Error: Invalid file format.");
            }
			
		} catch (FileNotFoundException e) {
            System.err.println("Error: File " + filepath + " not found.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
}

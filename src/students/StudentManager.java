package students;
import java.io.Serializable;
import java.util.*;

import courses.Course;
import util.DataBaseManager;

public class StudentManager implements Serializable{
    private static final String STUDENT_FILENAME = "students.txt";

    // Adds a new student to the data base
    public void addNewStudent(String name, String matricNum) {
    	//Do check to see if student already exist
        Student student = new Student(name, matricNum);
        ArrayList<Student> temp = retrieveStudents();
        temp.add(student);
        updateStudentDatabase(temp);
        System.out.println("Student has been added.");
    }

    
    // Finds a student according to his studentID
    public Student getStudent(String studentID) throws Exception {
    	
  
    		 for (Student temp : retrieveStudents() ) {
    	        	
    	            if(temp.getStudentID().equals(studentID)) {
    	                return temp;
    	            }
    	        }
    
        throw new Exception("This student cannot be found");
       
    }

    
    // Registers a student to the course
    public void registerCourse(String id, Course course) {
    	ArrayList<Student> temp = retrieveStudents(); 
    	
    	for(Student stud : temp) {
    		if(stud.getStudentID().equals(id)) {
    			stud.courses.add(course);
    		}
    	}
    	
    	updateStudentDatabase(temp);
    	
    }


    
    
    // Prints out information about students
    
    // Prints out the whole list of students in the school
   

    
    // Prints out the courses a student has registered in 
    public void printCoursesRegistered(String studentID) {
    	Student stud;
    	System.out.println(studentID + " has registered in ");
    	
		try {
			stud = getStudent(studentID);
			for(Course course : stud.getCourses()) {
	    		course.printCourseInfo();
	    	}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
    	
    	
    }
    
    
    
    
    // Prints out all the students and its course information 
    public void printStudents() {
        for(Student temp : retrieveStudents()) {
            System.out.println("Name: " + temp.getName()+ "\nStudentID: "+ temp.getStudentID() + "\n");
            for(Course course : temp.getCourses()) {
            	course.printCourseInfo();
            }
        }
    }
    
    
    
    // Writes the arraylist to the student database
    // Database managers

    // Writes the object into the database
    public void updateStudentDatabase(Object obj){
        DataBaseManager.updateData(obj,STUDENT_FILENAME);
    }


    // Retrieves data from database
    public ArrayList<Student> retrieveStudents() {

        if((ArrayList<Student>) DataBaseManager.retrieveData(STUDENT_FILENAME) == null) {
            ArrayList<Student> students = new ArrayList<Student>();
            DataBaseManager.updateData(students, STUDENT_FILENAME);
            return students;
        }
        else {
            return (ArrayList<Student>) DataBaseManager.retrieveData(STUDENT_FILENAME);
        }
    }
    
    // Resets the students database
    public void resetStudents() {
    	updateStudentDatabase(new ArrayList<Student>());
    }
}

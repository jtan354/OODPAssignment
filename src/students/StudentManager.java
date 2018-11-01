package students;
import java.util.*;
import courses.Course;
import courses.CourseManager;
import functionalityClasses.CRUDByID;
import functionalityClasses.PrintByID;
import functionalityClasses.PrintStudentByID;
import functionalityClasses.Reset;
import functionalityClasses.ResetStudents;
import functionalityClasses.StudentCRUDByID;
import functionalityClasses.StudentUpdate;
import util.DataBaseManager;
import util.DatabaseHandler;

public class StudentManager{
   
    private Reset reset;
    private CRUDByID crudID;
    private PrintByID pbID;
    private StudentUpdate update;

    public StudentManager() {
    	this.reset = new ResetStudents();
    	this.crudID = new StudentCRUDByID();
    	this.pbID = new PrintStudentByID();
    	this.update = new StudentUpdate();
    	
    }
    
    // Adds a new student to the data base
    public void addNewStudent(String name, String id) throws Exception {
    	crudID.createByID(id);
    	update.addName(id, name);
    }

    
    
    // Registers a student to the course
    public void registerCourse(String studentId, Course course) throws Exception{
    	update.registerCourse(course, studentId);
    }
    
    public Student getStudent(String id) throws Exception {
		return (Student) crudID.readByID(id);
    }
    
    // Prints out the courses a student has registered in 
    public void printCoursesRegistered(String studentID) throws Exception {
    	pbID.printByID(studentID);
    }
    
    
    // Prints out all the students and its course information 
    public void printStudents() throws Exception{
        reset.printAll();
    }
  
    
    // Resets the students database
    public void resetStudents() throws Exception {
    	reset.reset();
    }
    
    
}

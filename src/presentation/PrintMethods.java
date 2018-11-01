package presentation;

import courses.CourseManager;
import results.ResultManager;
import students.StudentManager;
import util.Scan;

public class PrintMethods {
	
	
	// Prints Course Statistics
	public static void printCourseStatistics() throws Exception {
		System.out.print("Enter course ID: ");
    	String courseID = Scan.readString();
    	
    	new ResultManager().printCourseStatistics(courseID);
	}
	
	// Prints Exam marks 
	public static void printExamMark() throws Exception {
		System.out.print("----- Exam Result ------"
    			+ "\nEnter student ID: ");
    	String studentID = Scan.readString();
    	System.out.print("Enter course ID: ");
    	String courseID = Scan.readString();
    	System.out.print("Enter exam marks: ");
    	double exMark = Scan.readDouble();
    	
    	new ResultManager().updateResult(courseID, studentID, exMark, 1);
	}
	
	
	// Prints Students registered in a course
	public static void printStudentList() throws Exception {
		System.out.print("----- Registered Students ------\n"
    			+ "Enter Course ID: ");
    	String courseID = Scan.readString();
    	new CourseManager().printStudentsRegisteredInCourse(courseID);
	}
	
	
	// Prints vacancy in a course
	public static void printVacancy() throws Exception {
		System.out.print("----- Course Vacancy -----\n"
    			+ "Enter Course ID: ");
    	String courseID = Scan.readString();
    	System.out.print("Vacancy: ");
    	new CourseManager().printVacancy(courseID);
	}
	
	// Prints Student Transcripts
	public static void printStudentTranscript() throws Exception {
		ResultManager rm = new ResultManager();
		
		System.out.print("Enter student ID: ");
    	String studentID = Scan.readString();
    	rm.printTranscript(studentID);
    
	}
	
	// Print All Courses
	public static void printAllCourses() {
		CourseManager cm = new CourseManager();
		
		cm.printCourses();
	}
	
	// Print all students 
	public static void printAllStudents() throws Exception {
		StudentManager sm = new StudentManager(); 
		
		sm.printStudents();
	}
	

	
}
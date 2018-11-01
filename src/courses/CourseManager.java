package courses;
import java.io.Serializable;
import java.util.*;

import functionalityClasses.FindByID;
import functionalityClasses.FindCoursesByID;
import functionalityClasses.CourseCRUDByID;
import lessons.Lab;
import lessons.Lecture;
import lessons.Lessons;
import lessons.Tutorial;
import students.Student;
import students.StudentManager;
import util.DataBaseManager;
import results.ResultManager;

/**
 * <code>CourseManager</code>
 * 
 * @author LFM
 */
// Problems: When you add Students into a course, only the course copy gets updated(Vacancy Reduces). The student copy remains the same. : Work around is to Store a reference in the student copy. 
//TODO: Decide whether we are using id or the object itself


public class CourseManager{
	private static final String COURSE_FILENAME = "Courses.txt";
	private ArrayList<Course> courses;
	private FindByID fbID;
	
	
	
	public CourseManager() {
		this.courses = retrieveCourses();
		this.fbID = new FindCoursesByID();
	}

    // Adds a new course to the data base
    public void addNewCourse(String courseID, ArrayList<String> profNames) throws Exception{
        CourseCRUDByID courseCRUD = new CourseCRUDByID();
        courseCRUD.createByID(courseID);
    }

  
    
    // Registers a student to the course
	// Updates ArrayList<Student> in Course
	public void registerStudent(Student student, String courseID) throws Exception {
    	for(Course course : this.courses) {
    		if(course.getCourseID().equals(courseID)) {
    			course.addStudent(student);
    			course.reduceVacancy();
    		}
    	}
    	
    	
    //Add Result entry
    	ResultManager rm = new ResultManager();
    	rm.addResult(courseID, student.getStudentID());
    	updateCourseDatabase(this.courses);
	}

	// Prints the students registered in a course
    public void printStudentsRegisteredInCourse(String courseID) throws Exception {
    	fbID.printByID(courseID);
    }
	
    
    
	// Adds new lesson to course 
	// Updates ArrayList<Lesson> in Course 
	 public void addLesson(Course course,int option, String lessonID, String lecturerID, int vacancy, String groupID) {
	    	Lessons les = null;
	    	
	    	switch(option) {
	    	case 1: 
	    		les = new Tutorial(lecturerID, lessonID, vacancy, groupID); 
	    		break; 
	    	case 2: 
	    		les = new Lab(lecturerID, lessonID, vacancy, groupID);
	    		break; 
	    	case 3: 
	    		les = new Lecture(lecturerID, lessonID, vacancy); 
	    		break;
	    	
	    	}
	    	
	    	ArrayList<Lessons> temp = course.getLessons(); 
	    	temp.add(les);
	    	updateCourseDatabase(temp);
	    }
	 
	 
	 
	 //Check Vacancy
	 private int getVacancy(String courseID) throws Exception{
		 return ((Course) fbID.getByID(courseID)).getVacancy();
	 }
	 
	 private int getMaxVacancy(String courseID) throws Exception{
		 return ((Course) fbID.getByID(courseID)).getMaxVacancy();
	 }
	 
	 public void printVacancy(String courseID) throws Exception{
		 System.out.println(getVacancy(courseID) + "/" + getMaxVacancy(courseID));
	 }
	 
	 
	 
	 //Prints the lessons in the course 
	 public void printLessons(String id) throws Exception {
		 Course temp = getCourse(id); 
			for(Lessons lesson : temp.getLessons()) {
	    		lesson.printInfo();
	    	}
	 }
	 
	 
	 
	public void setCourseworkWeightage(String courseID, int weightage) throws Exception{
		for(Course course : this.courses) {
			if(course.getCourseID().equals(courseID)) {
				course.setCWWeightage(weightage);
				course.setEXWeightage(100 - weightage);
			}
		}
		updateCourseDatabase(this.courses);
	}
	
	public void setClassParticipationWeightage(String courseID, int weightage) throws Exception{
		for(Course course : this.courses) {
			if(course.getCourseID().equals(courseID)) {
				course.setCPWeightage(weightage);
				course.setASWeightage(100 - weightage);
			}
		}
		updateCourseDatabase(this.courses);
	}
	
	
	
	
    // Lessons logic
  
    // Add Lessons
    // Adding lesson according to your choice
    // Option: 1--> Lab, Option: 2--> Tutorial, Option: 3 --> Lecture
    // Creates a new lesson and adds it into the course
    
    // Comments: 
    // Max Vacancy cannot be changed
    // groupID argument is set as 0 adding of lectures 
    
    
    
    // Add lessons
   
    
    
    // Find Lesson 
    public Lessons findLesson(String lessonID) {
    	for(Lessons temp : lessons) {
    		if(temp.getLessonID() == lessonID) {
    			return temp;
    		}
    	}
    	return null;
    }
    
    
    // Remove lessons
    public void removeLessons(String lessonID) {
    	for(Lessons temp : lessons) {
    		if(temp.getLessonID() == lessonID) {
    			lessons.remove(temp);
    		}
    	}
    }
    
   
    
    

    // Utility methods 
    
    // finds the course in database according to courseID
    /**
     * Returns a <code>Course</code> object corresponding to the course ID 
     * that can then be used to extract information of the specific 
     * course.
     * <p>
     * 
     * 
     * @param courseID - a unique ID corresponding to an existing Course
     * @return Course 
     * @throws Exception 
     * @see CourseInfo 
     */

    
    
    // Database handlers
    
    // Writes the object into the database
    public void updateCourseDatabase(Object obj){
    	DataBaseManager.updateData(obj,COURSE_FILENAME);
    }


    // Retrieves data from database
    public static ArrayList<Course> retrieveCourses() {
        if((ArrayList<Course>) DataBaseManager.retrieveData(COURSE_FILENAME) == null) {
            ArrayList<Course> courses = new ArrayList<Course>();
            DataBaseManager.updateData(courses, COURSE_FILENAME);
            return courses;
        }
        else {
            return (ArrayList<Course>) DataBaseManager.retrieveData(COURSE_FILENAME);
        }
    }
    
   
    
    
    // Testing methods : Methods for testing database 
    
    // These methods are not to be used for actual app usage 
    public void printCourses() {
    	for (Course temp : this.courses) {
    		temp.printCourseInfo();
    	}
    }
    

    public void resetCourses() {
    	updateCourseDatabase(new ArrayList<Course>());
    }
    
    
    

}

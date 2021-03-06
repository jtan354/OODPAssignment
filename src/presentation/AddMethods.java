package presentation;

import java.util.ArrayList;

import Exceptions.AlreadyExistsException;
import courses.*;
import students.StudentManager;
import util.Scan;
import util.inParse;

/**
 * Boundary layer holding all add methods
 * 
 * @author LFM
 * @version 1.0
 * @since 2018-11-05
 */
public class AddMethods {

	/**
	 * Adds student to a course
	 */
	public static void addStudent(){
		try {
			String name;
			do {
				System.out.println("Enter student's name: ");
				name = Scan.readString();
				if(inParse.numberExists(name)) {
					System.out.println("Student name cannot have numbers");
				}
			} while(inParse.numberExists(name));
			String id;
			do {
				System.out.println("Enter student's matric number: ");
				id = Scan.readString();
				if(id.equals("")) {
					System.out.println("Please key in a valid matric number.");
				}
			}while(id.equals(""));
			StudentManager sm = new StudentManager();
			sm.addNewStudent(name, id);
			sm.printStudents();
		}

		catch(AlreadyExistsException e) {
			System.out.println("This student ID already exists, please enter another student ID");
			addStudent();
		}

		catch(Exception e) {
			System.out.print(e);
		}
	}

	/**
	 * Add a new course
	 */

	public static void addCourse() {

		try {
			// Add course
			boolean courseExists;
			String courseID;
			CourseManager cm = new CourseManager();


			// Checks if the course exists
			do {
				courseExists = false;
				System.out.println("Enter course ID: ");
				courseID = Scan.readString();
				if(cm.getCourse(courseID) != null) {
					courseExists = true;
					System.out.println("This course already exists, please enter another course ID.");
				}
				if(!inParse.validCourseIDLength(courseID)) {
					System.out.println("The course ID length must be 6.");
				}
			}while(courseExists || !inParse.validCourseIDLength(courseID));



			// Takes in course name and vacancy from user
			System.out.print("Enter course name: ");
			String courseName = Scan.readString();
			int maxVacancy;
			do {
				System.out.println("Enter Maximum Vacancy for the course");
				maxVacancy = Scan.readInteger();
				if(maxVacancy <= 0) {
					System.out.println("Max Vacancy cannot be less than or equals to 0.");
				}
			} while(maxVacancy <= 0);

			// Add new course 
			// addProfs() returns an arraylist of profs
			cm.addNewCourse(courseID, addProfs());
			cm.updateMaxVacancy(courseID, maxVacancy);
			cm.updateCourseName(courseID, courseName);



			// Handles logic for adding lessons

			// Adds Lecture to the course
			System.out.print("Enter Lecture ID: ");
			String lectureID = Scan.readString();
			cm.addLesson(courseID, 1, lectureID, maxVacancy);


			// Retrieves User input to add tutorials to the course
			int sum;

			ArrayList<String> tutorialIDs = new ArrayList<String>();
			System.out.println("Do you want to add tutorials? Y/N");
			if(Scan.readString().toLowerCase().charAt(0) == 'y') {
				ArrayList<Integer> tutorialVacancies = new ArrayList<Integer>();
				do {
					sum = 0;
					tutorialIDs = new ArrayList<String>();
					String tutorialID; int tutorialVacancy; 
					do {
						boolean validLesson = true;
						System.out.print("Enter tutorial ID: ");
						tutorialID = Scan.readString();
						for(String id : tutorialIDs) {
							if(id.equals(tutorialID)) {
								validLesson = false;
								break;
							}
						}
						if(tutorialID.equals(lectureID)) {
							validLesson = false;
						}
						if(validLesson) {
							System.out.print("Enter tutorial vacancy: ");
							tutorialVacancy = Scan.readInteger();
							tutorialIDs.add(tutorialID);
							tutorialVacancies.add(tutorialVacancy);
							sum += tutorialVacancy;
							System.out.println("Total tutorial Vacancies: " + sum
									+ "\nCourse Vacancy: " + maxVacancy);
						} else {
							System.out.println("ID already exists.");
						}
						System.out.println("Any other tutorials? Y/N");
					} while(Scan.readString().toLowerCase().charAt(0) == 'y');
					if(sum != maxVacancy) {
						System.out.println("Total tutorial vacancy must be equals to course vacancy!"
								+ "\nPlease try again.");
					}
				} while(sum != maxVacancy);


				//Adding tutorials
				for(int i = 0; i < tutorialIDs.size(); i++) {
					cm.addLesson(courseID, 3, tutorialIDs.get(i), tutorialVacancies.get(i));
				}
			}


			// Retrieves user input to add Lab to the course 
			System.out.println("Do you want to add lab? Y/N");
			if(Scan.readString().toLowerCase().charAt(0) == 'y') {
				ArrayList<Integer> labVacancies = new ArrayList<Integer>();
				ArrayList<String> labIDs = new ArrayList<String>();
				do {
					sum = 0;
					String labID; int labVacancy; 
					do {
						boolean validLesson = true;
						System.out.print("Enter lab ID: ");
						labID = Scan.readString();
						for(String id : labIDs) {
							if(id.equals(labID)) {
								validLesson = false;
								break;
							}
						}
						for(String id : tutorialIDs) {
							if(id.equals(labID)) {
								validLesson = false;
								break;
							}
						}
						if(labID.equals(lectureID)) {
							validLesson = false;
						}
						if(validLesson) {
							System.out.print("Enter lab vacancy: ");
							labVacancy = Scan.readInteger();
							labIDs.add(labID);
							labVacancies.add(labVacancy);
							sum += labVacancy;
							System.out.println("Total Lab Vacancies: " + sum
									+ "\nCourse Vacancy: " + maxVacancy);
						}
						else {
							System.out.println("ID already exists. ");
						}
						System.out.println("Any other labs? Y/N");
					} while(Scan.readString().toLowerCase().charAt(0) == 'y');
					if(sum != maxVacancy) {
						System.out.println("Total lab vacancy must be equals to course vacancy!"
								+ "\nPlease try again.");
					}
				} while(sum != maxVacancy);

				// Adds lab to the course
				for(int j = 0; j < labIDs.size(); j++) {
					cm.addLesson(courseID, 2, labIDs.get(j), labVacancies.get(j));
				}
			} 
			PrintMethods.printAllCourses();	//Print all courses after creation success
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Add professors in charge of a course
	 * @return A list of professors in charge of a course
	 */

	private static ArrayList<String> addProfs() {
		try {
			ArrayList<String> profNames = new ArrayList<String>(); 
			System.out.print("Enter the name of main professor: ");
			profNames.add(Scan.readString());
			System.out.println("Enter Prof Names for the course, enter -1 after the last entry");
			String profName;
			do {
				System.out.println("Enter Prof Name");
				profName = Scan.readString();
				if(!profName.equals("-1")) {
					profNames.add(profName);
				}
			}
			while(!profName.equals("-1"));

			return profNames;
		}catch(Exception e) {
			System.out.print(e);
		}
		return null;
	}

	/**
	 * Adds student to a particular Tutorial/Lab group in a course
	 * @throws Exception
	 */

	public static void addStudentToLesson(){
		System.out.print("Enter Student ID: ");
    	String studentID = Scan.readString(); 
    	System.out.print("Enter Course ID: ");
    	String courseID = Scan.readString();
    	System.out.print("Enter lesson ID: ");
    	String lessonID = Scan.readString();
		
		try {
			new StudentManager().registerStudentToLesson(studentID, courseID, lessonID);
		}catch(Exception e) {
			System.out.print(e);
		}
	}
}


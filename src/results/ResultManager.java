package results;

import java.util.ArrayList;

import courses.CourseManager;
import util.DataBaseManager;
import courses.*;

public class ResultManager {
	private static final String RESULT_FILENAME = "Results.txt";
	private ArrayList<Result> results;
	
	public ResultManager() {
		this.results = retrieveResults();
	}
	
	private Result buildResult(String courseID, String studentID) {
		
		Result results = new Result(courseID, studentID, "overall");
		results.addSubComponent(new ResultComponent("exam"));
		results.addSubComponent(new ResultComponent("coursework"));
		results.getSubComponent().get(1).addSubComponent(new ResultComponent("assignment"));
		results.getSubComponent().get(1).addSubComponent(new ResultComponent("class participation"));
		return results;
		
	}
	
	public void addResult(String courseID, String studentID) {
		ResultManager rm = new ResultManager();
		Result result = rm.buildResult(courseID, studentID); 
		this.results.add(result);
		updateResultDatabase(this.results);
	}
	
	//Option: 1:exam, 2:assignment, 3: class part
	public void updateResult(String courseID, String studentID, double mark, int option) throws Exception{
		int index = getResultIndex(courseID, studentID);
		if(option >3 | option < 1) {
			throw new IllegalArgumentException("Option parameter can only be 1, 2 or 3.");
		}
		switch(option) {
		case 1:
			setEXResult(index, mark);
			break;
		case 2:	//first .get(1) returns coursework arraylist
			setASResult(index, mark);
			break;
		case 3:
			setCPResult(index, mark);
			break;
		}
		updateAllResult(courseID, studentID);
		updateResultDatabase(this.results);
		System.out.println("Result updated");
	}
	
	public void printTranscript(String studentID) throws Exception{
		System.out.println("----- Student Transcript ------"
				+ "\nStudent ID: " + studentID);
		for(Result result : this.results) {
			if(result.getStudentID().equals(studentID)) {
				updateAllResult(result.getCourseID(), studentID);
				System.out.println("Course ID: " + result.getCourseID() + 
						"\nOverall score: " + getOverallResult(result) + 
						"\nExam score: " + getEXResult(result) + 
						"\nCoursework score: " + getCWResult(result) + 
						"\nAssignment score: " + getASResult(result) + 
						"\nClass Participation score: " + getCPResult(result));
			}
		}
	}
	
	private void updateAllResult(String courseID, String studentID) throws Exception{
		Course course = CourseManager.getCourse(courseID);
		int index = getResultIndex(courseID, studentID);
		if(index != -1) {
			Result result = this.results.get(index);
			double cwComponent = course.getASWeightage()*getASResult(result)/100 
					+ course.getCPWeightage()*getCPResult(result)/100;
			double overall = course.getEXWeightage()*getEXResult(result)/100
					+ course.getCWWeightage()*getCWResult(result)/100;
			setCWResult(index, cwComponent);
			System.out.println(course.getCPWeightage() +  " " + course.getASWeightage());
			System.out.println(course.getEXWeightage() + " " + course.getCWWeightage() );
			System.out.println(cwComponent + " " + overall);
			setOverallResult(index, overall);
			updateResultDatabase(this.results);
		}
	}
	
	//Getter
	private double getASResult(Result result) {
		return result.getSubComponent().get(1).getSubComponent().get(0).getMark();
	}
	
	private double getCPResult(Result result) {
		return result.getSubComponent().get(1).getSubComponent().get(1).getMark();
	}
	
	private double getCWResult(Result result) {
		return result.getSubComponent().get(1).getMark();
	}
	
	private double getEXResult(Result result) {
		return result.getSubComponent().get(0).getMark();
	}
	
	private double getOverallResult(Result result) {
		return result.getMark();
	}
	
	//Setter
	private void setASResult(int index, double mark) {
		this.results.get(index).getSubComponent().get(1).getSubComponent().get(0).setMark(mark);
	}
	
	private void setCPResult(int index, double mark) {
		this.results.get(index).getSubComponent().get(1).getSubComponent().get(1).setMark(mark);
	}
	
	private void setCWResult(int index, double mark) {
		this.results.get(index).getSubComponent().get(1).setMark(mark);
	}
	
	private void setEXResult(int index, double mark) {
		this.results.get(index).getSubComponent().get(0).setMark(mark);
	}
	
	private void setOverallResult(int index, double mark) {
		this.results.get(index).setMark(mark);
	}
	
	private int getResultIndex(String courseID, String studentID) {
		for(Result result : this.results) {
			if(result.getCourseID().equals(courseID) && result.getStudentID().equals(studentID)) {
				return this.results.indexOf(result);
			}
		}
		return -1;
	}
	
	private void updateResultDatabase(Object obj){
    	DataBaseManager.updateData(obj, RESULT_FILENAME);
    }
	
	private static ArrayList<Result> retrieveResults() {
        if((ArrayList<Result>) DataBaseManager.retrieveData(RESULT_FILENAME) == null) {
            ArrayList<Result> results = new ArrayList<Result>();
            DataBaseManager.updateData(results, RESULT_FILENAME);
            return results;
        }
        else {
            return (ArrayList<Result>) DataBaseManager.retrieveData(RESULT_FILENAME);
        }
	}
	
}

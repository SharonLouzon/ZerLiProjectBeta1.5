package BranchWorker;

import java.io.Serializable;
import java.time.Year;
/**
 * class for satisfaction survey details.
 * @author Sharon & Elias
 *
 */
public class satisfactionSurvey  implements Serializable {


	private int customerID;
	private int step;
	private int QarSurvey;
	private String surveyYear;
	private float  Q1  ;
	private float Q2  ;
	private float Q3  ; 
	private float Q4   ; 
	private float Q5   ; 
	private float Q6   ;
	/**
	 * constrcutor
	 * @param customerID integer id of customer
	 * @param  step can be 1 or 0 , if 0 serveis worker create survey , if 1 branch worker filled the survey and we have results in DB
	 * @param qarSurvey integer quarter in year
	 * @param surveyYear integer year
	 * @param q1 answer question 1 answer from 1 to 10
	 * @param q2 answer question 2 answer from 1 to 10
	 * @param q3 answer question 3 answer from 1 to 10
	 * @param q4 answer question 4 answer from 1 to 10
	 * @param q5 answer question 5 answer from 1 to 10
	 * @param q6 answer question 6 answer from 1 to 10
	 */
	public satisfactionSurvey(int customerID, int step, int qarSurvey, String surveyYear, float q1, float q2, float q3, float q4,
			float q5, float q6) {
		super();
		this.customerID = customerID;
		this.step = step;
		QarSurvey = qarSurvey;
		this.surveyYear = surveyYear;
		Q1 = q1;
		Q2 = q2;
		Q3 = q3;
		Q4 = q4;
		Q5 = q5;
		Q6 = q6;
	}
	
	/**
	 * Default constructor
	 */
	public satisfactionSurvey() 
	{
		super();
	}
	
	/**
	 * getter of id of customer
	 * @return integer
	 */
	public int getCustomerID() 
	{
		return customerID;
	}
	/**
	 * setter of id of customer
	 * @param customerID ineteger new is of customer
	 */
	public void setCustomerID(int customerID) 
	{
		this.customerID = customerID;
	}
	
	/**
	 * getter of step
	 * @return integer
	 */
	public int getStep() 
	{
		return step;
	}
	/**
	 * setter of step
	 * @param step integer
	 */
	public void setStep(int step) 
	{
		this.step = step;
	}
	/**
	 * getter of quarter
	 * @return integer
	 */
	public int getQarSurvey() 
	{
		return QarSurvey;
	}
	
	/**
	 * setter of quarter of survey of the year
	 * @param qarSurvey integer
	 */
	public void setQarSurvey(int qarSurvey) 
	{
		QarSurvey = qarSurvey;
	}
	
	/**
	 * getter of year
	 * @return string
	 */
	public String getSurveyYear() 
	{
		return surveyYear;
	}
	/**
	 * setter of year
	 * @param surveyYear
	 */
	public void setSurveyYear(String surveyYear) {
		this.surveyYear = surveyYear;
	}
	
	/**
	 * getter return answer of question 1
	 * @return float
	 */
	public float getQ1() 
	{
		return Q1;
	}
	 /**
	  * setter answer of question 1
	  * @param q1 float
	  */
	public void setQ1(float q1) 
	{
		Q1 = q1;
	}
	/**
	 * getter return answer of question 2
	 * @return float
	 */
	public float getQ2() 
	{
		return Q2;
	}

/**
	  * setter answer of question 2
	  * @param q2 float
	  */
	public void setQ2(float q2) 
	{
		Q2 = q2;
	}
	/**
	 * getter return answer of question 3
	 * @return float
	 */
	public float getQ3() 
	{
		return Q3;
	}
	
	/**
	  * setter answer of question 3
	  * @param q3 float
	  */
	public void setQ3(float q3) 
	{
		Q3 = q3;
	}
	/**
	 * getter return answer of question 4
	 * @return float
	 */
	public float getQ4() 
	{
		return Q4;
	}

/**
	  * setter answer of question 4
	  * @param q4 float
	  */
	public void setQ4(float q4) 
	{
		Q4 = q4;
	}
	
	/**
	 * getter return answer of question 5
	 * @return float
	 */
	public float getQ5() 
	{
		return Q5;
	}
	

		/**
	  * setter  answer of question 5
	  * @param q5 float
	  */
	public void setQ5(float q5) 
	{
		Q5 = q5;
	}
	
	/**
	 * getter return answer of question 6
	 * @return float
	 */
	public float getQ6() 
	{
		return Q6;
	}
	
	/**
	  * setter answer of question 6
	  * @param q6 float
	  */
	public void setQ6(float q6) 
	{
		Q6 = q6;
	}

	
	/**
	 * tostring method that return all answers of specific Survey
	 */
	@Override
	public String toString() {
		return "Survey [customerID=" + customerID + ", step=" + step + ", QarSurvey=" + QarSurvey + ", surveyYear="
				+ surveyYear + ", Q1=" + Q1 + ", Q2=" + Q2 + ", Q3=" + Q3 + ", Q4=" + Q4 + ", Q5=" + Q5 + ", Q6=" + Q6
				+ "]";
	}
	
	  
}

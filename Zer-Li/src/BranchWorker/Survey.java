package BranchWorker;

import java.io.Serializable;

/**
 * this class is the Survey itself
 * @author sharon & elias
 *
 */
public class Survey  implements Serializable 
{
	private boolean ansStep;
	private int QarSurvey;
	private String surveyYear;
	 
	/**
	 * Default constructor
	 */
	 public Survey()
	 {

	 }
	
	 /**
	  * Constructor
	  * @param ansStep boolean flag of Survey creation
	  * @param QarSurvey integer quarter in a year
	  * @param surveyYear integer year
	  */
	 public Survey(boolean ansStep, int QarSurvey,String surveyYear)
	 {
			super();
			this.ansStep = ansStep;
			this.QarSurvey = QarSurvey;
			this.surveyYear = surveyYear;
	 }
	 
	 /**
	  * getter of step flag
	  * @return boolean
	  */
		public boolean getansStep() 
		{
			return ansStep;
		}
		/**
		 * setter of step
		 * @param ansStep boolean
		 */
		public void setansStep(boolean ansStep) 
		{
			this.ansStep = ansStep;
		}
		/**
		 * getter of quarter in year
		 * @return integer
		 */
		public int getQarSurvey() 
		{
			return QarSurvey;
		}
		
		/**
		 * setter of quarter in year
		 * @param qarSurvey integer
		 */
		public void setQarSurvey(int qarSurvey) {
			QarSurvey = qarSurvey;
		}
		
		/**
		 * getter of Survey  year
		 * @return string
		 */
		public String getSurveyYear() {
			return surveyYear;
		}
		/**
		 * setter of Survey year
		 * @param surveyYear  string 
		 */
		public void setSurveyYear(String surveyYear) {
			this.surveyYear = surveyYear;
		}
		
		/**
		 * to string method that return string with all details about Survey
		 */
		@Override
		public String toString() {
			return "Survey [stepis0=" + ansStep + ", QarSurvey=" + QarSurvey + ", surveyYear=" + surveyYear + "]";
		}
}

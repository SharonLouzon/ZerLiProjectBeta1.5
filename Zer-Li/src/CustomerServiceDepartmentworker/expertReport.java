package CustomerServiceDepartmentworker;

import java.io.Serializable;
/** and entity that contains the expert Report data,
 * used for the client-server communication. 
 * 
 * @author Alex
 * @implements Serializable
 *
 */
public class expertReport implements Serializable  {
	
/**constructor 
 * 	
 * @param ExpertId type of int
 * @param Quarter type of int
 * @param Year type of int
 * @param Report type of String
 */
	public expertReport(int ExpertId,int Quarter,int Year,String Report)
	{
		setExpertID(ExpertId);
		setQuarter(Quarter);
		setYear(Year);
		setReport(Report);
	}
	
	private int expertID;
	private int quarter;
	private int year;
	private String report;
	/**expert id getter
	 * 
	 * @return expertID
	 */
	public int getExpertID() {
		return expertID;
	}
	/** expertID setter
	 * 
	 * @param expertID type of int
	 */
	public void setExpertID(int expertID) {
		this.expertID = expertID;
	}
	/**reports quartet getter
	 * 
	 * @return quarter type of int
	 */
	public int getQuarter() {
		return quarter;
	}
	/**quarter setter 
	 * 
	 * @param quarter
	 */
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	/**year getter
	 * 
	 * @return year
	 */
	public int getYear() {
		return year;
	}
	/**year setter
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**report getter
	 * 
	 * @return report
	 */
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	

}

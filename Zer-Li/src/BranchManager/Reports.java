package BranchManager;

import java.io.Serializable;
import java.time.Year;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;

/**
 * A method that represents the Reports entity. contains all the data of a
 * single Report row from the Database.
 * 
 * @author Elias
 *
 */
public class Reports implements Serializable {

	Integer ReportType;
	String ReportYear;
	Integer ReportQuarter;
	String CsvFILE;
	String BranchID;

	
	/**A getter method, returns the reportType.
	 * 
	 * @return ReportType Integer
	 */
	public Integer getReportType() {
		return ReportType;
	}
/**a setter method for the reportType.
 * 
 * @param reportType
 */
	public void setReportType(Integer reportType) {
		ReportType = reportType;
	}
/**A getter method for the Report Year.
 * 
 * @return ReportYear String.
 */
	public String getReportYear() {
		return ReportYear;
	}

	/**A setter method, saves the current reportYear
	 * 
	 * @param reportYear
	 */
	public void setReportYear(String reportYear) {
		ReportYear = reportYear;
	}
/**a getter method, return the current Quarter, that the report belongs to.
 * 
 * @return ReportQuarter Integer.
 */
	public Integer getReportQuarter() {
		return ReportQuarter;
	}
/**a setter method saves the current quarter the report belongs to.
 * 
 * @param reportQuarter
 */
	public void setReportQuarter(Integer reportQuarter) {
		ReportQuarter = reportQuarter;
	}
/** a getter method. returns a CSV report file.
 * 
 * @return
 */
	public String getCsvFILE() {
		return CsvFILE;
	}
/**a setter method that saves a report CSV file.
 *  
 * @param csvFILE
 */
	public void setCsvFILE(String csvFILE) {
		CsvFILE = csvFILE;
	}
	
/**a getter method that returns the branchID that is related to the current reoprt.
 * 
 * @return BranchID type String.
 */
	public String getBranchID() {
		return BranchID;
	}
/** a setter method, that sets the current reports branch ID
 * 
 * @param branchID
 */
	public void setBranchID(String branchID) {
		BranchID = branchID;
	}

	@Override
	public String toString() {
		return "Reports [ReportType=" + ReportType + ", ReportYear=" + ReportYear + ", ReportQuarter=" + ReportQuarter
				+ ", CsvFILE=" + CsvFILE + ", BranchID=" + BranchID + "]";
	}
/** Report constructor
 * 
 * @param reportType Integer
 * @param reportYear String
 * @param reportQuarter Integer
 * @param csvFILE String  
 * @param branchID String 
 */
	public Reports(Integer reportType, String reportYear, Integer reportQuarter, String csvFILE, String branchID) {
		super();
		ReportType = reportType;
		ReportYear = reportYear;
		ReportQuarter = reportQuarter;
		CsvFILE = csvFILE;
		BranchID = branchID;
	}

}

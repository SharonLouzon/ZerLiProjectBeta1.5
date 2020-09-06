package CustomerServiceDepartmentworker;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**a ExpertReport Entity that used for the data binding in the GUI,
 * used mainly in the reportController controller.
 * 
 * @author Alex
 *
 */
public class expertReportEntry {

	private SimpleStringProperty expertId;
	private SimpleIntegerProperty quarter;
	private SimpleIntegerProperty year;
	private SimpleStringProperty report;

	public expertReportEntry() {
		expertId = new SimpleStringProperty();
		quarter = new SimpleIntegerProperty();
		year = new SimpleIntegerProperty();
		report = new SimpleStringProperty();
	}

	/**
	 * @return the expertId
	 */
	/*public SimpleIntegerProperty getExpertId() {
		return expertId;
	}*/
	
	public SimpleStringProperty getExpertIdStringProperty() {
		return expertId;
	}

	/**
	 * @param expertId
	 *            the expertId to set
	 */
	public void setExpertId(int ExpertId) {
		expertId.setValue(String.valueOf(ExpertId));
	}

	/**
	 * @return the quarter
	 */
	public SimpleIntegerProperty getQuarter() {
		return quarter;
	}

	/**
	 * @param quarter
	 *            the quarter to set
	 */
	public void setQuarter(int Quarter) {
		this.quarter.setValue(Quarter);
	}

	/**
	 * @return the year
	 */
	public SimpleIntegerProperty getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int Year) {
		this.year.setValue(Year);
	}

	/**
	 * @return the report
	 */
	public SimpleStringProperty getReport() {
		return report;
	}

	/**
	 * @param report
	 *            the report to set
	 */
	public void setReport(String Report) {
		report.setValue(Report);
	}

}

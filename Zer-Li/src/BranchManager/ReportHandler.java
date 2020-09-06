package BranchManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Blob;
import com.mysql.jdbc.Statement;

import CustomerServiceDepartmentworker.complaint;
import Users.LoginContol;
import client.ChatClient;
import client.Message;
/** the following class gets an orders quarterly report, and saves it
 * to a CSV file.
 * @author Elias, Alex
 *
 */
public class ReportHandler  extends LoginContol {
 
	/** 
	 * Method that generate reports in the quarter[1 or 2 or 3 or 4]
	 * reports type[1-revenue | 2-complaint | 3-order | 4-survey]
	 * @author Elias, Alex
	 *
	 */
	
	@SuppressWarnings("deprecation")
	public void generateQuarterItemsReport(Connection serverDataBase, int quarterNum) {

		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		int Year = Integer.parseInt(yearFormat.format(date));
		// java sql date format
		String startDate;
		String endDate;
		// array to hold all the item sales reports
		ArrayList<ordersReportEntry> report = new ArrayList<ordersReportEntry>();
		ArrayList<revenueReport> report1 = new ArrayList<revenueReport>();
		ArrayList<CompliantReport> report2 = new ArrayList<CompliantReport>();
		if (quarterNum == 1) {
			startDate = Year + "-01-01";
			endDate = Year + "-03-31";
		} else {
			if (quarterNum == 2) {
				startDate = Year + "-04-01";
				endDate = Year + "-06-30";
			} else {
				if (quarterNum == 3) {
					startDate = Year + "-07-01";
					endDate = Year + "-09-30";
				} else {
					startDate = Year + "-10-01";
					endDate = Year + "-12-31";
				}
			}
		}

		/*
		 * SELECT CIO.ItemID, CIO.Quantity FROM catalogiteminorder AS CIO,
		 * customerorders AS CO WHERE CIO.OrderID=CO.OrderID AND CO.CompletedDate
		 * between '2017-06-09' AND '2017-11-12'
		 */

		// send the query to the server
		try {
			// get the items from the DB
			String selectStatement = "SELECT CIO.ItemID, CIO.Quantity ,CO.BranchID FROM catalogiteminorder AS CIO, customerorders AS CO WHERE CIO.OrderID=CO.OrderID AND CO.CompletedDate between ? AND ?";
			PreparedStatement statement = serverDataBase.prepareStatement(selectStatement);
			statement.setString(1, startDate);
			statement.setString(2, endDate);
			ResultSet rs = statement.executeQuery();
			System.out.println("we are printing the report handling results: " + startDate + " " + endDate);
			
			while (rs.next()) {

				System.out.println(rs.getInt(1) + " " + rs.getInt(2) + "  "+rs.getString(3)+"\n");
				report.add(new ordersReportEntry(rs.getInt(1), rs.getInt(2)));
				writeToCSV(report, quarterNum, Year,rs.getString(3));
			}
			
			//Generate a CSV file
			//writeToCSV(report, quarterNum, Year);
			 

			rs.close();
			statement.close();

		} catch (SQLException e) {
			System.out.print("Sorry something went wrong with the SQL expression\n");
			e.printStackTrace();
		}
		
		
		
		//Compliant reports type 2  
		try {
			// get the items from the DB
			String selectStatement = "SELECT count(c.Topic) as NumberOfComplaints ,pa.BranchID From complaints as c,paymentaccounts as pa WHERE c.CustomerID=pa.CustomerID AND c.status='open' and c.DateComplaint BETWEEN ? AND ? group by pa.BranchID";
 
			PreparedStatement statement = serverDataBase.prepareStatement(selectStatement);
			statement.setString(1, startDate);
			statement.setString(2, endDate);
			ResultSet rs = statement.executeQuery();
			System.out.println("we are printing the report handling results: " + startDate + " " + endDate);
			 
			while (rs.next()) {

				System.out.println(rs.getInt(1) + " " + rs.getString(2)  +"\n");
				report2.add(new CompliantReport(rs.getInt(1), rs.getString(2)));
				writeToCSV2(report2, quarterNum, Year,rs.getString(2));
			}
			
			//Generate a CSV file
			//writeToCSV(report, quarterNum, Year);
			 

			rs.close();
			statement.close();

		} catch (SQLException e) {
			System.out.print("Sorry something went wrong with the SQL expression\n");
			e.printStackTrace();
		}
		
		
		try {
			// get the items from the DB
			String selectStatement = "SELECT OrderID,OrderPrice,BranchID  FROM  customerorders WHERE  CompletedDate between ? AND ? ";
			PreparedStatement statement = serverDataBase.prepareStatement(selectStatement);
			statement.setString(1, startDate);
			statement.setString(2, endDate);
			ResultSet rs = statement.executeQuery();
			System.out.println("we are printing the report handling results: " + startDate + " " + endDate);
			 
			while (rs.next()) {

				System.out.println(rs.getInt(1) + " " + rs.getInt(2) + "  "+rs.getString(3)+"\n");
				report1.add(new revenueReport(rs.getInt(1), rs.getFloat(2)));
				writeToCSV1(report1, quarterNum, Year,rs.getString(3));
			}
			
			//Generate a CSV file
			//writeToCSV(report, quarterNum, Year);
			 

			rs.close();
			statement.close();

		} catch (SQLException e) {
			System.out.print("Sorry something went wrong with the SQL expression\n");
			e.printStackTrace();
		}

	}
	

	 /**A method that creates CSV File for revenue report.
	  * It separates each field with a comma, and new line for each row,
	  * and appends it all to a single file
	  *  
	  * @param report
	  * @param quarter
	  * @param year
	  * @param BranchID
	  */
		public void writeToCSV2(ArrayList<CompliantReport> report, int quarter, int year, String BranchID) {
			String FileHeader = "NumberOfCompliants,BranchID\n";
			String csvFileName =  System.getProperty("user.dir")+"\\ZerLiProject_G13\\Zer-Li\\src\\Reports\\Compliant_Report_" + String.valueOf(quarter) + "-" + String.valueOf(year)+ "-" +BranchID+".csv";
			System.out.println("the set file name plus path is: "+csvFileName);
			try {
				FileWriter writer = new FileWriter(csvFileName);

				//append headers
				writer.append(new StringBuilder(FileHeader).toString());
				/*add all the existing entries from the DB to the CVS file
				each field is separated by a comma, new line with '\n'
				currently the CSV file is saved to the main directory
				for example C:\\*/
				for (CompliantReport reportEntry : report) {
					StringBuilder sb = new StringBuilder();
					sb.append(String.valueOf(reportEntry.getCountOfCompliants()));
					sb.append(",");
					sb.append(String.valueOf(reportEntry.getBranchID()));
					sb.append("\n");
					System.out.println("currently appending to csv: " + sb.toString());
					writer.append(sb.toString());
				}
				//force save the CVS to the drive
				writer.flush();
				writer.close();
				addNewReport(2,year+"",quarter,csvFileName,BranchID);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}

	
 // create Csv File for revenue report 
		 /**A method that creates CSV File for revenue report. 
		  * It separates each field with a comma, and new line for each row, 
		  * and appends it all to a single file
		  * @param report ArrayList<revenueReport>
		  * @param quarter int 
		  * @param year int 
		  * @param BranchID String
		  */

	public void writeToCSV1(ArrayList<revenueReport> report, int quarter, int year, String BranchID) {
		String FileHeader = "OrderID,OrderPrice\n";
		String csvFileName =  System.getProperty("user.dir")+"\\ZerLiProject_G13\\Zer-Li\\src\\Reports\\revenue_Report_" + String.valueOf(quarter) + "-" + String.valueOf(year)+ "-" +BranchID+".csv";
		System.out.println("the set file name plus path is: "+csvFileName);
		try {
			FileWriter writer = new FileWriter(csvFileName);

			//append headers
			writer.append(new StringBuilder(FileHeader).toString());
			/*add all the existing entries from the DB to the CVS file
			each field is separated by a comma, new line with '\n'
			currently the CSV file is saved to the main directory
			for example C:\\*/
			for (revenueReport reportEntry : report) {
				StringBuilder sb = new StringBuilder();
				sb.append(String.valueOf(reportEntry.getOrderID()));
				sb.append(",");
				sb.append(String.valueOf(reportEntry.getOrderPrice()));
				sb.append("\n");
				System.out.println("currently appending to csv: " + sb.toString());
				writer.append(sb.toString());
			}
			//force save the CVS to the drive
			writer.flush();
			writer.close();
			addNewReport(1,year+"",quarter,csvFileName,BranchID);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	 /**A method that creates CSV File for revenue report. 
	  * It separates each field with a comma, and new line for each row, 
	  * and appends it all to a single file
	  * @param report ArrayList<revenueReport>
	  * @param quarter int 
	  * @param year int 
	  * @param BranchID String
	  */

	public void writeToCSV(ArrayList<ordersReportEntry> report, int quarter, int year,String BranchID) {
		String FileHeader = "ItemId,Quantity\n";
		String csvFileName =  System.getProperty("user.dir")+"\\ZerLiProject_G13\\Zer-Li\\src\\Reports\\Orders_Report_" + String.valueOf(quarter) + "-" + String.valueOf(year)+ "-" +BranchID+".csv";
		System.out.println("the set file name plus path is: "+csvFileName);
		try {
			FileWriter writer = new FileWriter(csvFileName);

			//append headers
			writer.append(new StringBuilder(FileHeader).toString());
			/*add all the existing entries from the DB to the CVS file
			each field is separated by a comma, new line with '\n'
			currently the CSV file is saved to the main directory
			for example C:\\*/
			for (ordersReportEntry reportEntry : report) {
				StringBuilder sb = new StringBuilder();
				sb.append(String.valueOf(reportEntry.getItemType()));
				sb.append(",");
				sb.append(String.valueOf(reportEntry.getItemQuantity()));
				sb.append("\n");
				System.out.println("currently appending to csv: " + sb.toString());
				writer.append(sb.toString());
			}
			//force save the CVS to the drive
			writer.flush();
			writer.close();
			addNewReport(3,year+"",quarter,csvFileName,BranchID);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**A method that sends a request from the Server to save a new Report into the Database
	 * 
	 * @param reporttype
	 * @param year
	 * @param qar
	 * @param csvFile
	 * @param branchid
	 */
	public void addNewReport(int reporttype, String year ,int qar,String csvFile,String branchid )
	{
		Reports newReport=new Reports(reporttype,year,qar,csvFile,branchid);
		System.out.println(newReport);
		 int port=PORT ;
	 	   String ip=ServerIP ;
 	 	   try  
	 	   {
	 		myClient = new ChatClient(ip,port);	//create new client to get all users in db (server)
	 		myClient.setLoginControl(this); 
	 	   } 
	 	   catch (IOException e) 
	 	   {
	 		   System.out.println("Cannot create client");	  
	 	   }
	 	    
 	 	 myClient.AddNewReportToDB(newReport); 

		
		
		
		
	}
	
}

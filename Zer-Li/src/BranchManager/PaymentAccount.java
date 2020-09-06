package BranchManager;

import java.io.Serializable;

public class PaymentAccount  implements Serializable {
	 
	String UserName          ;
	int  CustomerID        ;
	  String  Password          ;
	  String PhoneNumber;
	  String  PaymentType      ;
	  String  CreditCardNum     ;
	  String  CreditCardExpDate  ;
	  int CvvCreditCardNum  ; 
	  String CreditCardType     ;
	  String SubscriptionType  ;
	  String BranchID;
	  String ExpAccountDate;
	  
	  
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getPaymentType() {
		return PaymentType;
	}
	public void setPaymentType(String paymentType) {
		PaymentType = paymentType;
	}
	public String getCreditCardNum() {
		return CreditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		CreditCardNum = creditCardNum;
	}
	public String getCreditCardExpDate() {
		return CreditCardExpDate;
	}
	public void setCreditCardExpDate(String creditCardExpDate) {
		CreditCardExpDate = creditCardExpDate;
	}
	public int getCvvCreditCardNum() {
		return CvvCreditCardNum;
	}
	public void setCvvCreditCardNum(int cvvCreditCardNum) {
		CvvCreditCardNum = cvvCreditCardNum;
	}
	public String getCreditCardType() {
		return CreditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		CreditCardType = creditCardType;
	}
	public String getSubscriptionType() {
		return SubscriptionType;
	}
	public void setSubscriptionType(String subscriptionType) {
		SubscriptionType = subscriptionType;
	}
	public String getBranchID() {
		return BranchID;
	}
	public void setBranchID(String branchID) {
		BranchID = branchID;
	}
	public String getExpAccountDate() {
		return ExpAccountDate;
	}
	public void setExpAccountDate(String expAccountDate) {
		ExpAccountDate = expAccountDate;
	}
 
	public PaymentAccount(String userName, int customerID, String password, String phoneNumber, String paymentType,
			String creditCardNum, String creditCardExpDate, int cvvCreditCardNum, String creditCardType,
			String subscriptionType, String branchID, String expAccountDate) {
		super();
		UserName = userName;
		CustomerID = customerID;
		Password = password;
		PhoneNumber = phoneNumber;
		PaymentType = paymentType;
		CreditCardNum = creditCardNum;
		CreditCardExpDate = creditCardExpDate;
		CvvCreditCardNum = cvvCreditCardNum;
		CreditCardType = creditCardType;
		SubscriptionType = subscriptionType;
		BranchID = branchID;
		ExpAccountDate = expAccountDate;
	}
	@Override
	public String toString() {
		return "PaymentAccount [UserName=" + UserName + ", CustomerID=" + CustomerID + ", Password=" + Password
				+ ", PhoneNumber=" + PhoneNumber + ", PaymentType=" + PaymentType + ", CreditCardNum=" + CreditCardNum
				+ ", CreditCardExpDate=" + CreditCardExpDate + ", CvvCreditCardNum=" + CvvCreditCardNum
				+ ", CreditCardType=" + CreditCardType + ", SubscriptionType=" + SubscriptionType + ", BranchID="
				+ BranchID + ", ExpAccountDate=" + ExpAccountDate + "]";
	}
	public PaymentAccount() {
		// TODO Auto-generated constructor stub
	}
	  
	  
}

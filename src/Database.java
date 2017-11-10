/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */



/**
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010

 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.
 */
import sun.awt.image.ImageWatched;

import java.io.*;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * The class that performs actions for manipulating data
 */
public class Database implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int PAYMENT_TYPE_NOT_FOUND = -1;
    private CCControl cccontrol;
    private BankAccountControl bankAccountControl;
    private DonorControl donorControl;
    private TransactionsControl transactionControl;
    private  ExpenseControl expenseControl;
    private static Database database;

    /**
     * Private for the singleton pattern Creates the catalog and member
     * collection objects
     */
    private Database() {
        cccontrol = CCControl.instance();
        bankAccountControl = BankAccountControl.instance();
        donorControl = DonorControl.instance();
        transactionControl = TransactionsControl.instance();
        expenseControl = ExpenseControl.instance();
    }

    /**
     * Supports the singleton pattern
     *
     * @return the singleton object
     */
    public static Database instance() {
        if (database == null) {
            return (database = new Database());
        } else {
            return database;
        }
    }

    /**
     * Searches for a given donor
     *
     * @param donorId
     *            id of the member
     * @return true iff the member is in the member list collection
     */
    public Donor searchDonorList(int donorId) {
        return donorControl.search(donorId);
    }

    /**
     * Removes a credit card from the database
     *
     * @param donorId
     *            id of the donor
     * @param creditCardNumber
     *            book id
     * @return result of the operation
     */
    public CreditCard removeCreditCard(int donorId, String creditCardNumber) {
        Donor donor = donorControl.search(donorId);
        if (donor == null) {
            return null;
        }
        CreditCard creditCard = cccontrol.search(donorId, creditCardNumber);
        if (creditCard == null) {
            return null;
        }
        donorControl.removeCreditCard(donorId, creditCardNumber);
        return cccontrol.removeCreditCard(donorId, creditCardNumber);
    }

    public BankAccount removeBankAccount(int donorId, String bankAccountNumber) {
        Donor donor = donorControl.search(donorId);
        if (donor == null) {
            return null;
        }
        BankAccount bankAccount = (BankAccount) bankAccountControl.search(donorId,bankAccountNumber);
        if (bankAccount == null) {
            return null;
        }
        donorControl.removeBankAccount(donorId, bankAccountNumber);
        return (BankAccount) bankAccountControl.removeBankAccount(donorId, bankAccountNumber);
    }

    /*
     * Removes Donor
     */
    public Donor removeDonor(int donorId) {
        Donor donor = donorControl.search(donorId);
        if (donor == null) {
            return null;
        }
        if (donorControl.removeDonor(donorId)) return donor;
        else return null;
    }

    /**
     * Returns an iterator to the transactions for a specific donor on a
     * certain date
     *
     * @param donorId
     *            donor id
     * @param date
     *            date of issue
     * @return iterator to the collection
     */
    public Iterator getDonorsTransactions(int donorId, Calendar date) {
        Donor donor = donorControl.search(donorId);
        if (donor == null) {
            return (null);
        }
        return donor.getTransactions(date);
    }

    /**
     * Returns an iterator to the transactions for a specific donor on a
     * certain date
     *
     * @return iterator to the collection
     */
    public Iterator getTransactions(){
        return transactionControl.getTransactions();
    }


    public Iterator getTransactionsAboveThreshold(int threshold){
        return transactionControl.getTransactionsAboveThreshold(threshold);
    }

    /**
     * Retrieves a deserialized version of the database from disk
     *
     * @return a Library object
     */
    public static Database retrieve() {
        try {
            FileInputStream file = new FileInputStream("DatabaseData");
            ObjectInputStream input = new ObjectInputStream(file);
            database = (Database) input.readObject();
            return database;
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    /**
     * Serializes the database object
     *
     * @return true iff the data could be saved
     */
    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("DatabaseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(database);
            file.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    /**
     * Function to Add a Donor
     *
     * @param name Donor Name
     * @param phone Donor Phone Number
     *
     * @return Donor
     */
    public Donor addDonor(String name, String phone) {
        try{
            Donor newDonor = donorControl.newDonor(name, phone);
            donorControl.insertDonor(newDonor);
            return newDonor;
        }catch(Exception e){return null;}

    }

    public Expense addExpense(String name, int amount) {
        try{
            Expense expense = new Expense(amount, name);
            expenseControl.addExpense(amount, name);
            return expense;
        }catch(Exception e){return null;}
    }

    /**
     * Function to Add a Credit Card
     *
     * @param donorId Donor Id
     * @param creditCardNumber Credit Card Number
     * @param donationAmount Donation Amount
     */
    public CreditCard addCreditCard(int donorId, String creditCardNumber, int donationAmount) {
        try{
            donorControl.addCreditCard(donorId, creditCardNumber, donationAmount);
            return cccontrol.addCreditCard(donorId, creditCardNumber, donationAmount);
        }catch(Exception e){return null;}
    }

    /**
     * Function to Add a Bank Account
     *
     * @param donorId Donor Id
     * @param bankAccountNumber Bank Account Number
     * @param donationAmount Donation Amount
     */
    public BankAccount addBankAccount(int donorId, String bankAccountNumber, int donationAmount) {
        try{
            donorControl.addBankAccount(donorId, bankAccountNumber, donationAmount);
            return bankAccountControl.addBankAccount(donorId, bankAccountNumber, donationAmount);
        }catch(Exception e){return null;}

    }

    /**
     * Function to get a Donor
     * @param donorId Donor Id
     * @return Donor
     */
    public Donor getDonor(int donorId) {
        return donorControl.search(donorId);
    }

    /**
     * Function to get the credit cards for Donor
     * @param donorID Donor id
     * @return List of credit cards for that donor
     */
    public Iterator getCreditCards(int donorID) {
        if (donorControl.search(donorID)!= null){
            return donorControl.search(donorID).getCreditCards().iterator();
        }
        return null;
    }

    public Iterator getBankAccounts(int donorID) {
        if (donorControl.search(donorID)!= null){
            return donorControl.search(donorID).getBankAccounts().iterator();
        }
        return null;
    }

    /**
     * Function to get a list of donors
     * @return Iterator of Donors
     */
    public Iterator getDonors() {
        return donorControl.getDonors();
    }

    /**
     * Function to get the Transaction
     * @param transactionID Transaction Id
     * @return The Transaction
     */
    public Transaction getTransaction(String transactionID){
        return transactionControl.search(transactionID);
    }

    /**
     * Method to remove transactions
     * @param donorID Donor Id
     */
    public void removeTransactions(int donorID) {
        transactionControl.removeTransactions(donorID);
    }

    /*
    * returns negative value as not found error, as any positive value could be a donation amount.
    *
     */
    public int getDonationAmount(int donorID, String number, int creditOrBankObject) {
        PaymentType searchObject = null;
        switch(creditOrBankObject){ // 2 == credit card, 3 == bank account
            case 2:
                searchObject = cccontrol.search(donorID, number);
                break;
            case 3:
                searchObject = bankAccountControl.search(donorID, number);
                break;
        }
        if (searchObject == null) {
            return PAYMENT_TYPE_NOT_FOUND;
        }
        return searchObject.getDonationAmount();

    }

    /**
     * Function to process donations.
     *
     * @param donorID Donor Id
     * @param number payment type number
     * @param donationAmount Donation Amount
     *
     * @return Transaction ID
     */
    public String processDonation(int donorID, String number, int donationAmount, String paymentType ) {
        Transaction transaction = transactionControl.newTransaction(donorID, number, donationAmount, paymentType);
        getDonor(donorID).addTransaction(transaction);
        return transactionControl.addTransaction(transaction);
    }

    /**
     * returns all expenses processed.
     * @return
     */
    public Iterator getExpensesProcessed() {
        return expenseControl.getAllExpensesProcessed();
    }
}
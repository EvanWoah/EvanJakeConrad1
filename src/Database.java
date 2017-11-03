/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */



/**
 *
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
    public static final int CREDIT_CARD_NOT_FOUND = -1;
    public static final int DONOR_NOT_FOUND = 2;
    public static final int BOOK_HAS_HOLD = 3;
    public static final int BOOK_ISSUED = 4;
    public static final int HOLD_PLACED = 5;
    public static final int NO_CREDIT_CARD_FOUND = 6;
    public static final int OPERATION_COMPLETED = 7;
    public static final int OPERATION_FAILED = 8;
    public static final int NO_SUCH_DONOR = 9;
    private static final int BANK_ACCOUNT_NOT_FOUND = 10;
    private static final int NO_BANK_ACCOUNT_FOUND = 11;
    private CCControl cccontrol;
    private BankAccountControl bankAccountControl;
    private DonorControl donorControl;
    private TransactionsControl transactionControl;
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
    public int removeCreditCard(int donorId, String creditCardNumber) {
        Donor donor = donorControl.search(donorId);
        if (donor == null) {
            return (NO_SUCH_DONOR);
        }
        CreditCard creditCard = cccontrol.search(donorId, creditCardNumber);
        if (creditCard == null) {
            return (CREDIT_CARD_NOT_FOUND);
        }
        donorControl.removeCreditCard(donorId, creditCardNumber);
        return cccontrol.removeCreditCard(donorId, creditCardNumber) ? OPERATION_COMPLETED : NO_CREDIT_CARD_FOUND;
    }

    public int removeBankAccount(int donorId, String bankAccountNumber) {
        Donor donor = donorControl.search(donorId);
        if (donor == null) {
            return (NO_SUCH_DONOR);
        }
        BankAccount bankAccount = bankAccountControl.search(donorId,bankAccountNumber);
        if (bankAccount == null) {
            return (BANK_ACCOUNT_NOT_FOUND);
        }
        donorControl.removeBankAccount(donorId, bankAccountNumber);
        return bankAccountControl.removeBankAccount(donorId, bankAccountNumber) ? OPERATION_COMPLETED : NO_BANK_ACCOUNT_FOUND;
    }

    /*
     * Removes Donor
     */
    public int removeDonor(int donorId) {
        Donor donor = donorControl.search(donorId);
        if (donor == null) {
            return (NO_SUCH_DONOR);
        }
        return donorControl.removeDonor(donorId) ? OPERATION_COMPLETED : NO_SUCH_DONOR;
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
     * Function to process donations.
     *
     * @param donorID Donor Id
     * @param creditCardNumber Credit Card Number
     * @param donationAmount Donation Amount
     *
     * @return Transaction ID
     */
    public String processDonation(int donorID, String creditCardNumber, int donationAmount ) {
        Transaction transaction = new Transaction(donorID, creditCardNumber, donationAmount);
        getDonor(donorID).addTransaction(transaction);
        return transactionControl.addTransaction(transaction);
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
            Donor newDonor = new Donor(name, phone);
            donorControl.insertDonor(newDonor);
            return newDonor;
        }catch(Exception e){return null;}

    }

    /**
     * Function to Add a Credit Card
     *
     * @param donorId Donor Id
     * @param creditCardNumber Credit Card Number
     * @param donationAmount Donation Amount
     */
    public void addCreditCard(int donorId, String creditCardNumber, int donationAmount) {
        cccontrol.addCreditCard(donorId, creditCardNumber, donationAmount);
        donorControl.addCreditCard(donorId, creditCardNumber, donationAmount);
    }

    /**
     * Function to Add a Bank Account
     *
     * @param donorId Donor Id
     * @param bankAccountNumber Bank Account Number
     * @param donationAmount Donation Amount
     */
    public void addBankAccount(int donorId, String bankAccountNumber, int donationAmount) {
        bankAccountControl.addBankAccount(donorId, bankAccountNumber, donationAmount);
        donorControl.addBankAccount(donorId, bankAccountNumber, donationAmount);
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

    /*
    * returns negative value as not found error, as any positive value could be a donation amount.
    *
     */
    public int getDonationAmount(int donorID, String creditCardNumber) {
        CreditCard creditCard = cccontrol.search(donorID, creditCardNumber);
        if (creditCard == null) {
            return (CREDIT_CARD_NOT_FOUND);
        }
        return Integer.parseInt(creditCard.getDonationAmount());
    }

    /**
     * Method to remove transactions
     * @param donorID Donor Id
     */
    public void removeTransactions(int donorID) {
        transactionControl.removeTransactions(donorID);
    }
}
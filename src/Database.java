/**
 * Created by evanwall on 9/21/17.
 */
//public class Database {
//
//    public static Database instance() {
//    }
//
//    public Donor addDonor(String name, String address, String phone) {
//    }
//
//    public static Database retrieve() {
//    }
//}



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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Database implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CREDIT_CARD_NOT_FOUND = 1;
    public static final int DONOR_NOT_FOUND = 2;
    public static final int BOOK_HAS_HOLD = 3;
    public static final int BOOK_ISSUED = 4;
    public static final int HOLD_PLACED = 5;
    public static final int NO_CREDIT_CARD_FOUND = 6;
    public static final int OPERATION_COMPLETED = 7;
    public static final int OPERATION_FAILED = 8;
    public static final int NO_SUCH_DONOR = 9;
    private CCControl cccontrol;
    private DonorControl donorControl;
    private TransactionsControl transactionControl;
    private static Database database;

    /**
     * Private for the singleton pattern Creates the catalog and member
     * collection objects
     */
    private Database() {
        cccontrol = CCControl.instance();
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
     * Searches for a given member
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
     * @param ccNumber
     *            book id
     * @return result of the operation
     */
    public int removeCreditCard(int donorId, String ccNumber) {
        Donor donor = donorControl.search(donorId);
        if (donor == null) {
            return (NO_SUCH_DONOR);
        }
        CreditCard creditCard = cccontrol.search(ccNumber);
        if (creditCard == null) {
            return (CREDIT_CARD_NOT_FOUND);
        }
        return cccontrol.removeCreditCard(ccNumber) ? OPERATION_COMPLETED : NO_CREDIT_CARD_FOUND;
    }

    /*
     * Removes all out-of-date holds
     */
    public int removeDonor(int donorId) {
        Donor donor = donorControl.search(donorId);
        if (donor == null) {
            return (NO_SUCH_DONOR);
        }
        return donorControl.removeDonor(donorId) ? OPERATION_COMPLETED : NO_SUCH_DONOR;
    }

    /**
     * Returns an iterator to the transactions for a specific member on a
     * certain date
     *
     * @param donorId
     *            donor id
     * @param date
     *            date of issue
     * @return iterator to the collection
     */
    public Iterator getTransactions(int donorId, Calendar date) {
        Donor donor = donorControl.search(donorId);
        if (donor == null) {
            return (null);
        }
        return donor.getTransactions(date);
    }

    /**
     * Retrieves a deserialized version of the library from disk
     *
     * @return a Library object
     */
    public static Database retrieve() {
        try {
            FileInputStream file = new FileInputStream("DatabaseData");
            ObjectInputStream input = new ObjectInputStream(file);
            database = (Database) input.readObject();
            return database;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }

    /**
     * Serializes the Library object
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

    public String processDonation(int donorID, String creditCardNumber, int donationAmount ) {
        return transactionControl.addTransaction(donorID, creditCardNumber, donationAmount);
    }

    public Donor addDonor(String name, String phone) {
        try{
            Donor newDonor = new Donor(name, phone);
            donorControl.insertDonor(newDonor);
            return newDonor;
        }catch(Exception e){return null;}

    }

    public void addCreditCard(int donorId, String creditCardNumber, int donationAmount) {
        cccontrol.addCreditCard(donorId, creditCardNumber, donationAmount);
    }

    public Donor getDonor(int donorId) {
        return donorControl.search(donorId);
    }

    public List getCreditCards(int donorID) {
       return cccontrol.getCreditCards(donorID);
    }

    public Iterator getDonors() {
        return donorControl.getDonors();
    }

    public Transaction getTransaction(String transactionID){
        return transactionControl.search(transactionID);
    }

    public int getDonationAmount(String creditCard) {
        return cccontrol.search(creditCard).getDonationAmount();
    }
}

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
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Donor implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int donorID;
    private String phoneNumber;
    private List transactions = new LinkedList();

    /**
     * Represents a single donor
     *
     * @param name
     *            name of the donor
     * @param phoneNumber
     *            phone number of the donor
     */
    public Donor(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.donorID = 10000+DonorControl.getDonorID();
    }

    /**
     * Gets an iterator to a collection of selected ransactions
     *
     * @param date
     *            the date for which the transactions have to be retrieved
     * @return the iterator to the collection
     */
    public Iterator getTransactions(Calendar date) {
        List result = new LinkedList();
        for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {
            Transaction transaction = (Transaction) iterator.next();
            if (transaction.onDate(date)) {
                result.add(transaction);
            }
        }
        return (result.iterator());
    }

    /**
     * Getter for name
     *
     * @return donor name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for phone number
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter for id
     *
     * @return donor id
     */
    public int getDonorID() {
        return donorID;
    }

    /**
     * Setter for name
     *
     * @param newName
     *            donor's new name
     */
    public void setName(String newName) {
        name = newName;
    }


    /**
     * Setter for phone
     *
     * @param newPhoneNumber
     *            donor's new phone
     */
    public void setPhone(String newPhoneNumber) {
        phoneNumber = newPhoneNumber;
    }

    /**
     * Checks whether the donor is equal to the one with the given id
     *
     * @param incomingDonorID
     *            of the Donor who should be compared
     * @return true iff the Donor ids match
     */
    public boolean equals(int incomingDonorID) {
        return this.donorID == (incomingDonorID);
    }

    /**
     * String form of the Donor
     *
     */
    @Override
    public String toString() {
        String string = "Donor name: " + name + ", id: " + donorID + ", phone number: " + phoneNumber;
        string += ", donated: [";
        string += "], transactions: [";
        for (Object transactionObject : transactions) {
            string += (Transaction) transactionObject;
        }
        string += "].";
        return string;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
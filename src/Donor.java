
/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Donor implements Serializable {
    private static final long serialVersionUID = 1L;
    private double donationSum;
    private String name;
    private int donorID;
    private String phoneNumber;
    private List transactions = new LinkedList();
    private List creditCards = new LinkedList();

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
     * Gets an iterator to a collection of selected transactions
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
     * String form of this Donor when listing a specific donor.
     */
    @Override
    public String toString() {
        String string = "Donor name: " + name +  ", phone number: " + phoneNumber;
        string += ", Credit cards on file: [";
        if (creditCards != null) {
            for (Object creditCardObject : creditCards) {
                CreditCard creditCard = (CreditCard) creditCardObject;
                string += creditCard.getCreditCardId();
                string += ": ";
                string += creditCard.getDonationAmount();
            }
        }
        string += "].";
        return string;
    }

    /**
     * When all donors are being listed, this string represents a donor.
     * @return
     */
    public String stringForAllDonors(){
        String string = "Donor name: " + name + ", id: " + donorID + ", phone number: " + phoneNumber;
        return string;
    }

    /**
     * Method to add a transaction
     * @param transaction Transaction
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        donationSum += transaction.getDonationAmount();
    }

    /**
     * Method to add a credit card
     * @param creditCardNumber Credit Card Number
     */
    public void addCreditCard(String creditCardNumber) {
        if (creditCards.contains(creditCardNumber)){
            return;
        }
        creditCards.add(creditCardNumber);
    }

    /**
     * Method to get credit cards
     * @return List of credit cards
     */
    public List getCreditCards() {
        return creditCards;
    }
}
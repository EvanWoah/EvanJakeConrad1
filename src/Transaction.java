
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
import java.util.GregorianCalendar;

/**
 * Represents a single Transaction (issue, renew, etc.)
 *
 * @author Brahma Dathan
 *
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private double donationAmount;
    private int donorIDOfDonation;
    private String transactionID;
    private int transactionCount = 0;
    private String title;
    private Calendar date;

    /**
     * Creates the transaction with a given amount and Donor. The date is the
     * current date.
     *
     * @param donationAmount
     *            The amount donated
     *
     */
    public Transaction(int donorIDOfDonation, String creditCard, int donationAmount) {
        transactionCount=transactionCount+1;
        this.donorIDOfDonation = donorIDOfDonation;
        this.donationAmount = donationAmount;
        this.date = new GregorianCalendar();
        this.transactionID = String.valueOf(donorIDOfDonation).concat("t").concat(String.valueOf(transactionCount));
        this.title = "Donor: " + donorIDOfDonation +
                "\n Donation Amount: " + donationAmount +
                "\n On: " + "Date: " + getDate();
    }

    /**
     * Checks whether this transaction is on the given date
     *
     * @param date
     *            The date for which transactions are being sought
     * @return true iff the dates match
     */
    public boolean onDate(Calendar date) {
        return ((date.get(Calendar.YEAR) == this.date.get(Calendar.YEAR))
                && (date.get(Calendar.MONTH) == this.date.get(Calendar.MONTH))
                && (date.get(Calendar.DATE) == this.date.get(Calendar.DATE)));
    }


    /**
     * Returns the title field
     *
     * @return title field
     */
    public double getDonationAmount() {
        return donationAmount;
    }

    /**
     * Returns the date as a String
     *
     * @return date with month, date, and year
     */
    public String getDate() {
        return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR);
    }

    /**
     * String form of the transaction
     *
     */
    @Override
    public String toString() {
        return title;
    }

    public String getTransactionID() {
        return transactionID;
    }
}
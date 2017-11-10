
/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Represents a single Transaction (issue, renew, etc.)
 *
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private double donationAmount;
    private String paymentTypeNumber;
    private String paymentType;
    private int donorIDOfDonation;
    private String transactionID;
    private String title;
    private Calendar date;

    /**
     * Creates the transaction with a given amount and Donor. The date is the
     * current date.
     *
     */
    public Transaction(){

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

    /**
     * Method to get a transaction id
     * @return Transaction Id
     */
    public String getTransactionID() {
        return transactionID;
    }

    public void setDonorIDOfDonation(int donorIDOfDonation) {
        this.donorIDOfDonation = donorIDOfDonation;
    }

    public void setDonationAmount(int donationAmount) {
        this.donationAmount = donationAmount;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public void setPaymentTypeNumber(String paymentTypeNumber) {
        this.paymentTypeNumber = paymentTypeNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
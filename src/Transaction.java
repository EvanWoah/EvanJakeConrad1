
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
    private String creditCardNumber;
    private String bankAccountNumber;
    private String paymentType;
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
    public Transaction(int donorIDOfDonation, String paymentTypeNumber, int donationAmount, String paymentType) {
        transactionCount=transactionCount+1;
        this.donorIDOfDonation = donorIDOfDonation;
        this.donationAmount = donationAmount;
        this.date = new GregorianCalendar();
        this.transactionID = String.valueOf(donorIDOfDonation).concat("t").concat(String.valueOf(transactionCount));
        if (paymentType.equals("Credit Card")) {
            this.creditCardNumber = paymentTypeNumber;
            this.title = "TransactionID: " + transactionID +
                "\n-> Card Number: " + creditCardNumber +
                ", Donation Amount: " + donationAmount +
                ", Date: " + getDate();
        }
        else {
            this.bankAccountNumber = paymentTypeNumber;
            this.title = "TransactionID: " + transactionID +
                    "\n-> Account Number: " + bankAccountNumber +
                    ", Donation Amount: " + donationAmount +
                    ", Date: " + getDate();
        }
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
    public String getDate() { //todo, this is giving us october dates.
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
}
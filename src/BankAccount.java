/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.Serializable;

/**
 * Represents a Single Bank Account
 */
public class BankAccount extends PaymentType implements Serializable{
    private String bankAccountNumber;
    private int donorId;
    private int donationAmount;

    /**
     * Constructor to create a new Bank Account
     * @param donorId Donor associated with the bank account number
     * @param baNumber Bank Account Number
     * @param donationAmount Donation Amount
     */
    public BankAccount(int donorId, String baNumber, int donationAmount) {
        this.donorId = donorId;
        this.bankAccountNumber = baNumber;
        this.donationAmount = donationAmount;
    }

    /**
     * Function to get the bankAccountNumber
     *
     * @return Bank Account Number
     */
    public String getBankAccountId() {return bankAccountNumber;}

    /**
     * Function to get the donor id
     *
     * @return donorId
     */
    public int getDonorId(){return donorId;};

    /**
     * Function to get the donation amount
     *
     * @return Donation Amount
     */
    public String getDonationAmount() {
        return String.valueOf(donationAmount);
    }
}

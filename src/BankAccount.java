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

    /**
     * Constructor to create a new Bank Account
     * @param donorId Donor associated with the bank account number
     * @param bankAccountNumber Bank Account Number
     * @param donationAmount Donation Amount
     */
    public BankAccount(int donorId, String bankAccountNumber, int donationAmount) {
        super.donorId = donorId;
        this.bankAccountNumber = bankAccountNumber;
        super.donationAmount = donationAmount;
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


    public String addedString() {
        return "BankAccount{" +
                "bankAccountNumber='" + bankAccountNumber + '\'' +
                "donationAmount='" + super.donationAmount + '\'' +
                "donorID='" + super.donorId + '\'' +

                '}';
    }
}

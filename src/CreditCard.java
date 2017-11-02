/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.Serializable;

/**
 * Represents a Single Credit Card
 */
public class CreditCard extends PaymentType implements Serializable{
    private String creditCardNumber;
    /**
     * Constructor to create a new Credit Card
     * @param donorId Donor associated with the credit card
     * @param ccNumber Credit Card Number
     * @param donationAmount Donation Amount
     */
    public CreditCard(int donorId, String ccNumber, int donationAmount) {
        super.donorId = donorId;
        this.creditCardNumber = ccNumber;
        super.donationAmount = donationAmount;
    }

    /**
     * Function to get the creditCard Number
     *
     * @return Credit Card Number
     */
    public String getCreditCardId() {return creditCardNumber;}

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

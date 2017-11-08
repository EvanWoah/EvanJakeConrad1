/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.Serializable;

/**
 * Represents a Single Payment Type
 */
public class PaymentType implements Serializable {
    protected int donorId;
    protected int donationAmount;
    /**
     * Function to get the donation amount
     *
     * @return Donation Amount
     */
    public int getDonationAmount() {
        return donationAmount;
    }
}
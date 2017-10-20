import java.io.Serializable;

public class CreditCard implements Serializable{
    private int donorId;
    private String creditCardNumber;
    private int donationAmount;

    public CreditCard(int donorId, String ccNumber, int donationAmount) {
        this.donorId = donorId;
        this.creditCardNumber = ccNumber;
        this.donationAmount = donationAmount;
    }

    public String getCreditCardId() {return creditCardNumber;}
    public int getDonorId(){return donorId;};

    public String getDonationAmount() {
        return String.valueOf(donationAmount);
    }
}

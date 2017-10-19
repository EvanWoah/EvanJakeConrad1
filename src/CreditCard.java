import java.io.Serializable;

public class CreditCard implements Serializable{
    private int donorId;
    private int creditCardNumber;
    private int donationAmount;

    public CreditCard(int donorId, int ccNumber, int donationAmount) {
        this.donorId = donorId;
        this.creditCardNumber = ccNumber;
        this.donationAmount = donationAmount;
    }

    public int getCreditCardId() {return this.creditCardNumber;}

}

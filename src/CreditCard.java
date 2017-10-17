import java.io.Serializable;

public class CreditCard implements Serializable{
    private int donorId;
    private int creditCardId;
    private String creditCardNumber;

    public CreditCard(int donorId, int creditCardId, String ccNumber) {
        this.donorId = donorId;
        this.creditCardId = creditCardId;
        this.creditCardNumber = ccNumber;
    }
}

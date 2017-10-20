import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class CCControl implements Serializable{
    private static final long serialVersionUID = 1L;
    private List creditCardNumbers = new LinkedList();
    private static CCControl cccontrol;

    private CCControl() {
    }

    public static CCControl instance() {
        if (cccontrol == null) {
            return (cccontrol = new CCControl());
        } else {
            return cccontrol;
        }
    }

    public CreditCard search(int donorID, String ccNumber) {
        for (Object creditCardObject : creditCardNumbers) {
            CreditCard creditCard = (CreditCard) creditCardObject;
            if (creditCard.getCreditCardId().equals(ccNumber) && creditCard.getDonorId()==donorID) {
                return creditCard;
            }
        }
        return null;
    }

    public boolean removeCreditCard(int donorID, String ccNumber) {
        CreditCard cc = search(donorID, ccNumber);
        if (cc == null) {
            return false;
        } else {
            return creditCardNumbers.remove(cc);
        }
    }

    public void addCreditCard(int donorId, String creditCardNumber, int donationAmount) {
        CreditCard cc = new CreditCard(donorId, creditCardNumber, donationAmount);
        creditCardNumbers.add(cc);
    }

    public List getCreditCards(int donorID) {
        List ccList = new LinkedList();
        for (Object creditCardObject : creditCardNumbers) {
            CreditCard creditCard = (CreditCard) creditCardObject;
            if (creditCard.getDonorId()==(donorID)) {
                ccList.add(creditCard);
            }
        }
        return ccList;
    }
}

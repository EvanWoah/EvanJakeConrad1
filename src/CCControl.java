import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CCControl implements Serializable{
    private static final long serialVersionUID = 1L;
    private List ccNumbers = new LinkedList();
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

    public CreditCard search(int ccNumber) {
        for (Iterator iterator = ccNumbers.iterator(); iterator.hasNext();) {
            CreditCard cc = (CreditCard) iterator.next();
            if (cc.getCreditCardId() == (ccNumber)) {
                return cc;
            }
        }
        return null;
    }

    public boolean removeCreditCard(int ccNumber) {
        CreditCard cc = search(ccNumber);
        if (cc == null) {
            return false;
        } else {
            return ccNumbers.remove(cc);
        }
    }

    public void addCreditCard(int donorId, int creditCardNumber, int donationAmount) {
        CreditCard cc = new CreditCard(donorId, creditCardNumber, donationAmount);
        ccNumbers.add(cc);
    }
}

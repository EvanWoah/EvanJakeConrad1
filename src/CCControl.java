/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * The collection class for Credit Card objects
 *
 */
public class CCControl implements Serializable{
    private static final long serialVersionUID = 1L;
    private List creditCardNumbers = new LinkedList();
    private static CCControl cccontrol;

    /**
     * Constructor
     */
    private CCControl() {
    }

    /**
     * Instance method for CCControl
     * @return CCControl
     */
    public static CCControl instance() {
        if (cccontrol == null) {
            return (cccontrol = new CCControl());
        } else {
            return cccontrol;
        }
    }

    /**
     * Method to search for a credit card
     * @param donorID Donor Id
     * @param ccNumber Credit card number
     * @return Credit Card
     */
    public CreditCard search(int donorID, String ccNumber) {
        for (Object creditCardObject : creditCardNumbers) {
            CreditCard creditCard = (CreditCard) creditCardObject;
            if (creditCard.getCreditCardId().equals(ccNumber) && creditCard.getDonorId()==donorID) {
                return creditCard;
            }
        }
        return null;
    }

    /**
     * Method to remove Credit Card
     * @param donorID Donor Id
     * @param ccNumber Credit Card Number
     * @return true if card removed, false otherwise
     */
    public CreditCard removeCreditCard(int donorID, String ccNumber) {
        CreditCard cc = search(donorID, ccNumber);
        if (cc == null) {
            return null;
        } else {
            creditCardNumbers.remove(cc);
            return cc;
        }
    }

    /**
     * Method to add a credit card
     * @param donorId Donor Id
     * @param creditCardNumber Credit Card Number
     * @param donationAmount Donation Amount
     */
    public CreditCard addCreditCard(int donorId, String creditCardNumber, int donationAmount) {
        CreditCard creditCard = new CreditCard(donorId, creditCardNumber, donationAmount);
        creditCardNumbers.add(creditCard);
        return creditCard;
    }

    /**
     * Metho to get credit cards
     * @param donorID Donor Id
     * @return List of credit cards
     */
    public List getCreditCards(int donorID) {
        List creditCardList = new LinkedList();
        for (Object creditCardObject : creditCardNumbers) {
            CreditCard creditCard = (CreditCard) creditCardObject;
            if (creditCard.getDonorId()==(donorID)) {
                creditCardList.add(creditCard);
            }
        }
        return creditCardList;
    }
}

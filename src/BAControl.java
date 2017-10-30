/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * The collection class for Bank Account objects
 *
 */
public class BAControl implements Serializable{
    private static final long serialVersionUID = 1L;
    private List bankAccountNumbers = new LinkedList();
    private static BAControl bacontrol;

    /**
     * Constructor
     */
    private BAControl() {
    }

    /**
     * Instance method for BAControl
     * @return BAControl
     */
    public static BAControl instance() {
        if (cccontrol == null) {
            return (cccontrol = new CCControl());
        } else {
            return cccontrol;
        }
    }

    /**
     * Method to search for a bank account
     * @param donorID Donor Id
     * @param baNumber Bank Account number
     * @return Bank Account
     */
    public BankAccount search(int donorID, String baNumber) {
        for (Object bankAccountObject : bankAccountNumbers) {
            BankAccount bankAccount = (BankAccount) bankAccountObject;
            if (bankAccount.getBankAccountId().equals(baNumber) && bankAccount.getDonorId()==donorID) {
                return bankAccount;
            }
        }
        return null;
    }

    /**
     * Method to remove Bank Account
     * @param donorID Donor Id
     * @param baNumber Bank Account Number
     * @return true if bank account removed, false otherwise
     */
    public boolean removeBankAccount(int donorID, String baNumber) {
        BankAccount ba = search(donorID, baNumber);
        if (ba == null) {
            return false;
        } else {
            return bankAccountNumbers.remove(ba);
        }
    }

    /**
     * Method to add a bank account
     * @param donorId Donor Id
     * @param bankAccountNumber Bank Account Number
     * @param donationAmount Donation Amount
     */
    public void addBankAccount(int donorId, String bankAccountNumber, int donationAmount) {
        BankAccount bankAccount = new BankAccount(donorId, bankAccountNumber, donationAmount);
        bankAccountNumbers.add(bankAccount);
    }

    /**
     * Metho to get bank accounts
     * @param donorID Donor Id
     * @return List of bank accounts
     */
    public List getBankAccounts(int donorID) {
        List baList = new LinkedList();
        for (Object bankAccountObject : bankAccountNumbers) {
            BankAccoumt bankAccount = (BankAccount) bankAccountObject;
            if (bankAccount.getDonorId()==(donorID)) {
                baList.add(bankAccount);
            }
        }
        return baList;
    }
}

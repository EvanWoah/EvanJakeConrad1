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
public class BankAccountControl implements Serializable{
    private static final long serialVersionUID = 1L;
    private List bankAccountNumbers = new LinkedList();
    private static BankAccountControl bankAccountControl;


    /**
     * Constructor
     */
    private BankAccountControl() {
    }

    /**
     * Instance method for BAControl
     * @return BAControl
     */
    public static BankAccountControl instance() {
        if (bankAccountControl == null) {
            return (bankAccountControl = new BankAccountControl());
        } else {
            return bankAccountControl;
        }
    }

    /**
     * Method to search for a bank account
     * @param donorID Donor Id
     * @param bankAccountNumber Bank Account number
     * @return Bank Account
     */
    public BankAccount search(int donorID, String bankAccountNumber) {
        for (Object bankAccountObject : bankAccountNumbers) {
            BankAccount bankAccount = (BankAccount) bankAccountObject;
            if (bankAccount.getBankAccountId().equals(bankAccountNumber) && bankAccount.getDonorId()==donorID) {
                return bankAccount;
            }
        }
        return null;
    }

    /**
     * Method to remove Bank Account
     * @param donorID Donor Id
     * @param bankAccountNumber Bank Account Number
     * @return true if bank account removed, false otherwise
     */
    public boolean removeBankAccount(int donorID, String bankAccountNumber) {
        BankAccount bankAccount = search(donorID, bankAccountNumber);
        if (bankAccount == null) {
            return false;
        } else {
            return bankAccountNumbers.remove(bankAccount);
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
     * Method to get bank accounts
     * @param donorID Donor Id
     * @return List of bank accounts
     */
    public List getBankAccounts(int donorID) {
        List bankAccountList = new LinkedList();
        for (Object bankAccountObject : bankAccountNumbers) {
            BankAccount bankAccount = (BankAccount) bankAccountObject;
            if (bankAccount.getDonorId() == (donorID)) {
                bankAccountList.add(bankAccount);
            }
        }
        return bankAccountList;
    }
}

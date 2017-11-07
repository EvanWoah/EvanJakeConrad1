/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Represents a Single Donor
 */
public class Donor implements Serializable {
    private static final long serialVersionUID = 1L;
    private int donationSum;
    private String name;
    private int donorID;
    private String phoneNumber;
    private List<Transaction> transactions = new LinkedList();
    private List<CreditCard> creditCards = new LinkedList();
    private List<BankAccount> bankAccounts = new LinkedList();

    /**
     * Represents a single donor
     *
     * @param name
     *            name of the donor
     * @param phoneNumber
     *            phone number of the donor
     */
    public Donor(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.donorID = 10000+DonorControl.getDonorID();
    }

    /**
     * Gets an iterator to a collection of selected transactions
     *
     * @param date
     *            the date for which the transactions have to be retrieved
     * @return the iterator to the collection
     */
    public Iterator getTransactions(Calendar date) {
        List result = new LinkedList();
        for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {
            Transaction transaction = (Transaction) iterator.next();
            if (transaction.onDate(date)) {
                result.add(transaction);
            }
        }
        return (result.iterator());
    }

    /**
     * Getter for name
     *
     * @return donor name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for phone number
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter for id
     *
     * @return donor id
     */
    public int getDonorID() {
        return donorID;
    }

    /**
     * Setter for name
     *
     * @param newName
     *            donor's new name
     */
    public void setName(String newName) {
        name = newName;
    }


    /**
     * Setter for phone
     *
     * @param newPhoneNumber
     *            donor's new phone
     */
    public void setPhone(String newPhoneNumber) {
        phoneNumber = newPhoneNumber;
    }

    /**
     * Checks whether the donor is equal to the one with the given id
     *
     * @param incomingDonorID
     *            of the Donor who should be compared
     * @return true iff the Donor ids match
     */
    public boolean equals(int incomingDonorID) {
        return this.donorID == (incomingDonorID);
    }

    /**
     * String form of this Donor when listing a specific donor.
     */
    @Override
    public String toString() {
        String string = "Donor name: " + name + ", DonorID: "+donorID+ ", phone number: " + phoneNumber;
        string += ", Credit cards on file: [";
        Iterator result = creditCards.iterator();
        while (result.hasNext()) {
            CreditCard creditCard = ((CreditCard)result.next());
            string += creditCard.getCreditCardId();
            string += ": $";
            string += creditCard.getDonationAmount();
            string += ".00, ";
        }
        string += "]";
        string += ", Bank Accounts on file: [";
        result = bankAccounts.iterator();
        while (result.hasNext()) {
            BankAccount bankAccount = ((BankAccount) result.next());
            string += bankAccount.getBankAccountId();
            string += ": $";
            string += bankAccount.getDonationAmount();
            string += ".00, ";
        }
        string += "].";
        return string;
    }

    /**
     * When all donors are being listed, this string represents a donor.
     * @return
     */
    public String stringForAllDonors(){
        String string = "Donor name: " + name + ", id: " + donorID + ", phone number: " + phoneNumber;
        return string;
    }

    /**
     * Method to add a transaction
     * @param transaction Transaction
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        donationSum += transaction.getDonationAmount();
    }

    /**
     * Method to add a credit card
     * @param creditCardNumber Credit Card Number
     */
    public void addCreditCard(String creditCardNumber, int donationAmount) {
        CreditCard creditCard = new CreditCard(getDonorID(), creditCardNumber, donationAmount);
        if (creditCards.contains(creditCard)){
            return;
        }
        creditCards.add(creditCard);
    }

    public void addBankAccount(String bankAccountNumber, int donationAmount) {
        BankAccount bankAccount = new BankAccount(getDonorID(), bankAccountNumber, donationAmount);
        if (creditCards.contains(bankAccount)){
            return;
        }
        bankAccounts.add(bankAccount);
    }

    /**
     * Method to get credit cards
     * @return List of credit cards
     */
    public List getCreditCards() {
        if (creditCards!=null){
            return creditCards;
        }
        return null;
    }

    public List getBankAccounts() {
        if (bankAccounts!=null){
            return bankAccounts;
        }
        return null;
    }

    public int getDonationSum() {
        return donationSum;
    }

    public void setDonationSum(int donationSum) {
        this.donationSum = donationSum;
    }

    public void removeCreditCard(String creditCardNumber) {
        Iterator result = creditCards.iterator();
        if (result != null){
            while (result.hasNext()){
                CreditCard resultCreditCard = (CreditCard) result.next();
                if (resultCreditCard.getCreditCardId().equals(creditCardNumber)){
                    creditCards.remove(resultCreditCard);
                }
            }
        }
    }

    public void removeBankAccount(String bankAccountNumber) {
        Iterator result = bankAccounts.iterator();
        if (result != null){
            while (result.hasNext()){
                BankAccount resultBankAccount = (BankAccount) result.next();
                if (resultBankAccount.getBankAccountId().equals(bankAccountNumber)){
                    bankAccounts.remove(resultBankAccount);
                }
            }
        }
    }
}
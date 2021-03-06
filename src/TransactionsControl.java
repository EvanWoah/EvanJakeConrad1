/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The collection class for transaction objects
 *
 */
public class TransactionsControl implements Serializable{
    private static final long serialVersionUID = 1L;
    private List transactions = new LinkedList();
    private static TransactionsControl transactionsControl;
    private int transactionCount = 0;


    /**
     * Constructor
     */
    private TransactionsControl() {
    }

    /**
     * This is responsible for creating a transaction object. Basically an external constructor.
     * This allows us to increment transactionCount
     * without it being static, therefore it is serializeable.
     */
    public Transaction newTransaction(int donorIDOfDonation, String paymentTypeNumber, int donationAmount, String paymentType) {
        Transaction transaction = new Transaction();
        transactionCount = transactionCount + 1;
        transaction.setDonorIDOfDonation(donorIDOfDonation);
        transaction.setDonationAmount(donationAmount);
        transaction.setDate(new GregorianCalendar());
        transaction.setTransactionID(String.valueOf(donorIDOfDonation).concat("t").concat(String.valueOf(transactionCount)));
        transaction.setPaymentTypeNumber(paymentTypeNumber);
        transaction.setTitle("TransactionID: " + transaction.getTransactionID() +
                "\n-> "+paymentType+" Number: " + paymentTypeNumber +
                ", Donation Amount: " + donationAmount +
                ", Date: " + transaction.getDate());
        return transaction;
    }

    /**
     * Instance method for Transactions Control
     * @return Transaction Control Object
     */
    public static TransactionsControl instance() {
        if (transactionsControl == null) {
            return (transactionsControl = new TransactionsControl());
        } else {
            return transactionsControl;
        }
    }

    /**
     * Method to search for transactions
     * @param transactionID Transaction Id
     * @return Transaction, or null
     */
    public Transaction search(String transactionID) {
        for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {
            Transaction transaction = (Transaction) iterator.next();
            if (transaction.getTransactionID().equals(transactionID)) {
                return transaction;
            }
        }
        return null;
    }

    /**
     * Function to add transactions
     * @param transaction Transaction
     * @return Transaction ID
     */
    public String addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return transaction.getTransactionID();
    }

    /**
     * Method to get transactions
     * @return Iterator of transactions
     */
    public Iterator getTransactions(){
        try
        {
            return transactions.iterator();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Method to get transactions above a certain amount
     * @return Iterator of transactions
     */
    public Iterator getTransactionsAboveThreshold(int threshold) {
        try
        {
            LinkedList transactionsAboveThreshold = new LinkedList();
            Iterator allTransactions = transactions.iterator();
            while (allTransactions.hasNext()){
                Transaction transaction = (Transaction) allTransactions.next();
                if (transaction.getDonationAmount() > threshold){
                    transactionsAboveThreshold.add(transaction);
                }
            }
            return transactionsAboveThreshold.iterator();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Method to Remove Transactions
     * @param donorID Donor Id
     */
    public void removeTransactions(int donorID) {
        for (Object transactionObject : transactions){
            Transaction transaction = (Transaction) transactionObject;
            if (transaction.getTransactionID().contains(String.valueOf(donorID))){
                transactions.remove(transaction);
            }
        }
    }
}

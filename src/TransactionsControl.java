/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.Serializable;
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

    /**
     * Constructor
     */
    private TransactionsControl() {
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

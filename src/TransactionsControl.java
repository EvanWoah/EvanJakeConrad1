import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TransactionsControl implements Serializable{
    private static final long serialVersionUID = 1L;
    private List transactions = new LinkedList();
    private static TransactionsControl transactionsControl;

    private TransactionsControl() {
    }

    public static TransactionsControl instance() {
        if (transactionsControl == null) {
            return (transactionsControl = new TransactionsControl());
        } else {
            return transactionsControl;
        }
    }

    public Transaction search(String transactionID) {
        for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {
            Transaction transaction = (Transaction) iterator.next();
            if (transaction.getTransactionID().equals(transactionID)) {
                return transaction;
            }
        }
        return null;
    }


    public String addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return transaction.getTransactionID();
    }


}

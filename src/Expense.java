/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.Serializable;

/**
 * Represents a Single Expense
 */
public class Expense implements Serializable{

    private String expenseName;
    private int expenseAmount;

    /**
     * Constructor to create a new Expense
     * @param expenseAmount the amount the expense costs the organization
     * @param expenseName the name of this expense
     */
    public Expense(int expenseAmount, String expenseName) {
        this.expenseAmount = expenseAmount;
        this.expenseName = expenseName;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public int getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public void setExpenseAmount(int expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseName='" + expenseName + '\'' +
                ", expenseAmount=" + expenseAmount +
                '}';
    }


}

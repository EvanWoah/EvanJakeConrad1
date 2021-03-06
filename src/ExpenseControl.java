/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The collection class for Bank Account objects
 *
 */
public class ExpenseControl implements Serializable{
    private static final long serialVersionUID = 1L;
    private List expensesProcessed = new LinkedList();
    private static ExpenseControl expenseControl;

    /**
     * Constructor
     */
    private ExpenseControl() {
    }

    /**
     * Instance method for BAControl
     * @return BAControl
     */
    public static ExpenseControl instance() {
        if (expenseControl == null) {
            return (expenseControl = new ExpenseControl());
        } else {
            return expenseControl;
        }
    }

    /**
     * Method to search for an expense
     * @param
     */
    public Expense search(String expenseName, int expenseAmount) {
        for (Object expenseObject : expensesProcessed) {
            Expense expense = (Expense) expenseObject;
            if (expense.getExpenseName().equals(expenseName) && expense.getExpenseAmount()==expenseAmount) {
                return expense;
            }
        }
        return null;
    }

    public void addExpense(int expenseAmount, String expenseName) {
        Expense expense = new Expense(expenseAmount, expenseName);
        expensesProcessed.add(expense);
    }

    public Iterator getAllExpensesProcessed() {
        if (expensesProcessed.iterator() == null) {
            return (null);
        }
        return expensesProcessed.iterator();
    }
}

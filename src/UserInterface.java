
/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * //TODO Wrap each method in a whileYesOrdNO() loop right? Maybe Reprint what the user jsut requested "8 entered, add payment info"
 * This class implements the user interface for the Donation project. The
 * commands are encoded as integers using a number of static final variables. A
 * number of utility methods exist to make it easier to parse the input.
 *
 */
public class UserInterface {
    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Database database;
    private static final int EXIT = 0;
    private static final int ADD_DONOR = 1;
    private static final int ADD_PAYMENT_METHOD = 2;
    private static final int PROCESS_DONATIONS = 3;
    private static final int LIST_TRANSACTIONS = 4;
    private static final int LIST_DONORS = 5;
    private static final int LIST_DONOR = 6;
    private static final int REMOVE_DONOR = 7;
    private static final int REMOVE_CREDIT_CARD = 8;
    private static final int REMOVE_BANK_ACCOUNT = 9;
    private static final int ADD_EXPENSES = 10;
    private static final int ORGANIZATION_INFO = 11;
    private static final int LIST_PAYMENT_METHOD_INFO = 12;
    private static final int LIST_EXPENSES = 13;
    private static final int SAVE = 14;
    private static final int HELP = 15;

    /**
     * Object Types
     */
    private static final int DONOR_OBJECT = 0;
    private static final int PAYMENT_OBJECT = 1;
    private static final int CREDIT_CARD_OBJECT = 2;
    private static final int BANK_ACCOUNT_OBJECT = 3;
    private static final int EXPENSE_OBJECT = 4;
    private static final int TRANSACTION_OBJECT = 5;
    private static final int ALL_DONORS_OBJECT = 6;
    private static final int PAYMENT_METHOD_INFO = 7;

    /**
     * Made private for singleton pattern. Conditionally looks for any saved
     * data. Otherwise, it gets a singleton Library object.
     */
    private UserInterface() {
        if (yesOrNo("Look for saved data and  use it?")) {
            retrieve();
        } else {
            database = Database.instance();
        }
    }

    /**
     * Supports the singleton pattern
     *
     * @return the singleton object
     */
    public static UserInterface instance() {
        if (userInterface == null) {
            return userInterface = new UserInterface();
        } else {
            return userInterface;
        }
    }

    /**
     * Gets a token after prompting
     *
     * @param prompt
     *            - whatever the user wants as prompt
     * @return - the token from the keyboard
     *
     */
    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    /**
     * Queries for a yes or no and returns true for yes and false for no
     *
     * @param prompt
     *            The string to be prepended to the yes/no prompt
     * @return true for yes and false for no
     *
     */
    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }

    /**
     * Converts the string to a number
     *
     * @param prompt
     *            the string for prompting
     * @return the integer corresponding to the string
     *
     */
    public int getNumber(String prompt) {
        do {
            try {
                String item = getToken(prompt);
                Integer number = Integer.valueOf(item);
                return number.intValue();
            } catch (NumberFormatException nfe) {
                System.out.println("Please input a number ");
            }
        } while (true);
    }

    /**
     * Prompts for a date and gets a date object
     *
     * @param prompt
     *            the prompt
     * @return the data as a Calendar object
     */
    public Calendar getDate(String prompt) {
        do {
            try {
                Calendar date = new GregorianCalendar();
                String item = getToken(prompt);
                DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
                date.setTime(dateFormat.parse(item));
                return date;
            } catch (Exception fe) {
                System.out.println("Please input a date as mm/dd/yy");
            }
        } while (true);
    }

    /**
     * Prompts for a command from the keyboard
     *
     * @return a valid command
     *
     */
    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
                if (value >= EXIT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }

    /**
     * Displays the help screen
     *
     */
    public void help() {
        System.out.println("Enter a number between 0 and 15 as explained below:");
        System.out.println(EXIT + " to Exit\n");
        System.out.println(ADD_DONOR + " to  add a donor");
        System.out.println(ADD_PAYMENT_METHOD + " to  add a payment method");
        System.out.println(PROCESS_DONATIONS + " to  process donations");
        System.out.println(LIST_TRANSACTIONS + " to  list transactions ");
        System.out.println(LIST_DONORS + " to  list all donors ");
        System.out.println(LIST_DONOR + " to  list a specific donor");
        System.out.println(REMOVE_DONOR + " to  remove a donor");
        System.out.println(REMOVE_CREDIT_CARD + " to  remove a credit card");
        System.out.println(REMOVE_BANK_ACCOUNT + " to  remove a bank account");
        System.out.println(ADD_EXPENSES + " to  add an expense");
        System.out.println(ORGANIZATION_INFO + " to  list the organizations info");
        System.out.println(LIST_PAYMENT_METHOD_INFO + " to  list payment method info");
        System.out.println(LIST_EXPENSES + " to  list expenses");
        System.out.println(SAVE + " to  save data");
        System.out.println(HELP + " to  list this again");
    }

//    /**
//     * Method to be called for adding a donor. Prompts the user for the
//     * appropriate values and uses the appropriate Database method for adding the
//     * donor.
//     *
//     */
//    public void addDonor() {
//        do {
//            String name = getToken("Enter donor name");
//            String phone = getToken("Enter phone");
//            Donor result;
//            result = database.addDonor(name, phone);
//            if (result == null) {
//                System.out.println("Could not add donor");
//            }
//            System.out.println(result);
//        }while(yesOrNo("Add another donor?"));
//    }


//    /**
//     * Method to be called for displaying all transactions.
//     *
//     */
//    public void listTransactions() {
//        Iterator result = database.getTransactions();
//        while (result.hasNext()) {
//            Transaction transaction = (Transaction) result.next();
//            System.out.println(transaction.toString() + "\n");
//        }
//        System.out.println("\n  There are no more transactions \n");
//    }

//    /**
//     * Method to be called displaying all donors.
//     *
//     */
//    public void listDonors() {
//        Iterator result = database.getDonors();
//        while (result.hasNext()) {
//            Donor donor = (Donor) result.next();
//            System.out.println(donor.toString() + "\n");
//        }
//        System.out.println("\n  There are no more donors \n");
//    }

    //   /**
     //     * Method to print all transactions which are above the provided threshold
     //     */
//    private void listPaymentMethodInfo() {
//        int threshold = getNumber("Enter threshold amount as int");
//        Iterator result = database.getTransactionsAboveThreshold(threshold);
//        while (result.hasNext()) {
//            Transaction transaction = (Transaction) result.next();
//            System.out.println(transaction.toString() + "\n");
//        }
//        System.out.println("\n  There are no more transactions \n");
//    }


//    /**
//     * Method to be called for removing a donor. Prompts the user for the
//     * appropriate values and uses the appropriate Database method for removing
//     * a donor.
//     *
//     */
//    public void removeDonor() {
//        int donorID = Integer.parseInt(getToken("Enter donor id"));
//        int result =  database.removeDonor(donorID);
//        switch (result) {
//            case Database.NO_SUCH_DONOR:
//                System.out.println("Not a valid donor ID");
//                break;
//            default:
//                System.out.println("Donor " +donorID+ " has been removed");
//        }
//    }

//    /**
//     * Method to be called for removing a credit card. Prompts the user for the
//     * appropriate values and uses the appropriate Database method for removing a
//     * credit card.
//     */
//    public void removePaymentMethod(int incomingCase) {
//        int donorID;
//        int result;
//        switch (incomingCase) {
//            case 1:
//                donorID = getNumber("Enter donor id");
//                String ccNumber = getToken("Enter credit card number");
//                result = database.removeCreditCard(donorID, ccNumber);
//                switch (result) {
//                    case Database.CREDIT_CARD_NOT_FOUND:
//                        System.out.println("No such Credit Card");
//                        break;
//                    case Database.NO_SUCH_DONOR:
//                        System.out.println("Not a valid donor ID");
//                        break;
//                    case Database.OPERATION_COMPLETED:
//                        System.out.println("The card has been removed");
//                        break;
//                    default:
//                        System.out.println("An error has occurred");
//                }
//            case 2:
//                donorID = getNumber("Enter donor id");
//                String bankAccountNumber = getToken("Enter bank account number");
//                result = database.removeBankAccount(donorID, bankAccountNumber);
//                switch (result) {
//                    case Database.BANK_ACCOUNT_NOT_FOUND:
//                        System.out.println("No such Bank Account");
//                        break;
//                    case Database.NO_SUCH_DONOR:
//                        System.out.println("Not a valid donor ID");
//                        break;
//                    case Database.OPERATION_COMPLETED:
//                        System.out.println("The Bank Account has been removed");
//                        break;
//                    default:
//                        System.out.println("An error has occurred");
//                }
//        }
//    }

//    private void addExpenses() {
//        do {
//            String name = getToken("Enter expense name");
//            int amount = getNumber("Enter expense amount (int)");
//            Expense result;
//            result = database.addExpense(name, amount);
//            if (result == null) {
//                System.out.println("Could not add expense");
//            }
//            System.out.println(result);
//        }while(yesOrNo("Add another expense?"));
//
//    }


////TODO handle null iterator
//    private void listExpenses() {
//        Iterator expenseIterator = database.getExpensesProcessed();
//        while (expenseIterator.hasNext()) {
//            System.out.print(expenseIterator.next().toString());
//        }
//        System.out.println("\n  There are no more expenses \n");
//    }

    /**
     * Method to be called for saving the Database object. Uses the appropriate
     * Database method for saving.
     *
     */
    private void save() {
        if (database.save()) {
            System.out.println(" The database has been successfully saved in the file DatabaseData \n");
        } else {
            System.out.println(" There has been an error in saving \n");
        }
    }

    /**
     * Method to be called for retrieving saved data. Uses the appropriate
     * Database method for retrieval.
     *
     */
    private void retrieve() {
        try {
            if (database == null) {
                database = Database.retrieve();
                if (database != null) {
                    System.out.println(" The library has been successfully retrieved from the file LibraryData \n");
                } else {
                    System.out.println("File doesnt exist; creating new library");
                    database = Database.instance();
                }
            }
        } catch (Exception cnfe) {
            cnfe.printStackTrace();
        }
    }

    /**
     * Orchestrates the whole process. Calls the appropriate method for the
     * different functionalities.
     *
     */
    public void process() {

        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                case ADD_DONOR:
                    addObject(DONOR_OBJECT);
              //      addDonor();
                    break;
                case ADD_PAYMENT_METHOD:
                    addObject(PAYMENT_OBJECT);
                //    addPaymentMethod();
                    break;
                case PROCESS_DONATIONS:
                    processDonations();
                    break;
                case LIST_TRANSACTIONS:
                    listObject(TRANSACTION_OBJECT);
               //     listTransactions();
                    break;
                case LIST_DONOR:
                    listDonor();
                    break;
                case LIST_DONORS:
                    listObject(ALL_DONORS_OBJECT);
               //     listDonors();
                    break;
                case REMOVE_DONOR:
                    removeObject(DONOR_OBJECT);
                //    removeDonor();
                    break;
                case REMOVE_CREDIT_CARD:
                    removeObject(CREDIT_CARD_OBJECT);
                //    removePaymentMethod(1);
                    break;
                case REMOVE_BANK_ACCOUNT:
                    removeObject(BANK_ACCOUNT_OBJECT);
                //    removePaymentMethod(2);
                    break;
                case ADD_EXPENSES:
                    addObject(EXPENSE_OBJECT);
                //    addExpenses();
                    break;
                case ORGANIZATION_INFO:
                    showOrganizationInfo();
                    break;
                case LIST_PAYMENT_METHOD_INFO:
                    listObject(PAYMENT_METHOD_INFO);
               //     listPaymentMethodInfo();
                    break;
                case LIST_EXPENSES:
                    listObject(EXPENSE_OBJECT);
               //     listExpenses();
                    break;
                case SAVE:
                    save();
                    break;
                case HELP:
                    help();
                    break;
            }
        }
        exit();
    }

    private void addObject(int objectType) {
        String name;
        String phoneNumber;
        int amount;
        int donorID;
        Object result = "";
        String objectTypeString = "";
        do {
            switch (objectType) {
                case DONOR_OBJECT:
                    objectTypeString = "donor";
                    name = getToken("Enter donor name");
                    phoneNumber = getToken("Enter phone");
                    result = database.addDonor(name, phoneNumber);
                    break;
                case PAYMENT_OBJECT:
                    objectTypeString = "payment type";
                    result = addPaymentMethod();
                    break;
                case EXPENSE_OBJECT:
                    objectTypeString = "expense";
                    name = getToken("Enter expense name");
                    amount = getNumber("Enter expense amount (int)");
                    result = database.addExpense(name, amount);
                    break;
            }
            if (result == null) {
                System.out.println("Could not add " + objectTypeString + ".");
            }else{
                System.out.println(result);
            }
        }while(yesOrNo("Add another " + objectTypeString + "?"));
    }


    /**
     * Method to add a Payment Method TODO could be refactored to less lines if we queue questions differently
     */
    private Object addPaymentMethod() {
        int donationAmount;
        int donorID = getNumber("Enter donor id");
        if (database.getDonor(donorID) != null) {
            int command = getNumber("Enter Either 0 For Credit Card or 1 For Bank Account");
            while (command != 0 || command != 1) {
                System.out.println("Entered value was neither 0 or 1. Please try again.");
                command = getNumber("Enter Either 0 For Credit Card or 1 For Bank Account");
                if (command == 0 || command == 1)
                    break;
            }
            switch (command) {
                case 0:
                    String creditCardNumber = getToken("Enter credit card number");
                    donationAmount = getNumber("Enter dollar donation amount as integer");
                    return database.addCreditCard(donorID, creditCardNumber, donationAmount).addedString();
                case 1:
                    String bankAccountNumber = getToken("Enter bank account number");
                    donationAmount = getNumber("Enter dollar donation amount as integer");
                    return database.addBankAccount(donorID, bankAccountNumber, donationAmount).addedString();
            }
        }else {
            System.out.println("No such donor.\n");
        }
        return null;
    }


    /**
     * Method to be called displaying a single donor.
     *
     */
    private void listDonor() {
        Donor resultDonor;
        do {
            int donorID = getNumber("Enter donor id");
            resultDonor = database.searchDonorList(donorID);
            if (resultDonor != null){
                System.out.println(resultDonor.stringForOneDonor());

            }else{
                System.out.println("No such donor");
            }
            if (!yesOrNo("Find another Donor?")) {
                break;
            }
        } while (true);
    }

    //TODO surround in list again yesOrNo();
    private void listObject(int objectType) {
        Iterator result;
        int queryInt;
        String caseObjectName = "objects (ERROR) ERROR ERROR!!!";
        switch (objectType){
            case PAYMENT_METHOD_INFO:
                int threshold = getNumber("Enter threshold amount as int");
                result = database.getTransactionsAboveThreshold(threshold);
                caseObjectName = "transactions";
                break;
            case EXPENSE_OBJECT:
                result = database.getExpensesProcessed();
                caseObjectName = "expenses";
                break;
            case TRANSACTION_OBJECT:
                result = database.getTransactions();
                caseObjectName = "transactions";
                break;
            case  ALL_DONORS_OBJECT:
                result = database.getDonors();
                caseObjectName = "donors";
                break;
            default:
                result = null;
        }
        while (result != null && result.hasNext()) {
            System.out.println(result.next().toString() + "\n");
        }
        System.out.println("\n  There are no more " + caseObjectName + ".\n");
    }

    //will go farther than should if invalid donor entered for removal of payment type
    private void removeObject(int objectType) {
        String objectTypeString = "";
        Object result = null;
        Object validDonor = null;
        do{
            int donorID = Integer.parseInt(getToken("Enter donor id"));
            validDonor = database.getDonor(donorID);
            if (validDonor == null){
                System.out.println("Invalid donor");
                return;
            }
            switch (objectType) {
                case DONOR_OBJECT:
                    objectTypeString = "donor";
                    result = database.removeDonor(donorID);
                    break;
                case CREDIT_CARD_OBJECT:
                    objectTypeString = "credit card";
                    String ccNumber = getToken("Enter credit card number");
                    result = database.removeCreditCard(donorID, ccNumber);
                    break;
                case BANK_ACCOUNT_OBJECT:
                    objectTypeString = "bank account";
                    String bankAccountNumber = getToken("Enter bank account number");
                    result = database.removeBankAccount(donorID, bankAccountNumber);
                    break;
            }
            if (result == null){ //This is problematic. Try removing or adding, weve got problems.
                System.out.println("Invalid " + objectTypeString + ".");
            }else{
                System.out.println("The " + objectTypeString + " has been removed");
            }
        }while(yesOrNo("Remove another " + objectTypeString + "?"));
    }

    /**
     *
     */
    private void showOrganizationInfo() {
        int totalAmountDonated = 0;
        int totalAmountSpent = 0;
        Iterator donatedResult = database.getDonors();
        while (donatedResult.hasNext()) {
            Donor donor = (Donor) donatedResult.next();
            totalAmountDonated =+ donor.getDonationSum();
        }
        Iterator spentResult = database.getExpensesProcessed();
        while (spentResult.hasNext()) {
            Expense expense = (Expense) spentResult.next();
            totalAmountSpent =+ expense.getExpenseAmount();
        }
        int balance = totalAmountDonated - totalAmountSpent;
        System.out.println("Organization Info \nTotal amount donated: " + totalAmountDonated + "\nTotal amount spent (expenses): " + totalAmountSpent + "\nBalance: " + balance +".");
    }

    /**
     * Function to process donations. //TODO get working, refactor
     */
    public void processDonations() {
        String creditOrBankString = "";
        String number;
        String transactionID;
        int donorID = getNumber("Enter donor id");
        int donationAmount;
        int caseNumber = -1;
        Iterator resultCard = database.getCreditCards(donorID);
        Iterator resultBankAccount = database.getBankAccounts(donorID);
        if (resultCard != null && resultCard.hasNext() && resultBankAccount != null && resultBankAccount.hasNext()) { //if the donor has bank accounts and credit cards
            caseNumber = getNumber("Process Credit Card: enter 1, Process bank account: enter 2:");
        } else if (resultCard != null && resultCard.hasNext()) {
            caseNumber = CREDIT_CARD_OBJECT;
        } else if (resultBankAccount != null && resultBankAccount.hasNext()) {
            caseNumber = BANK_ACCOUNT_OBJECT;
        }
        System.out.println(creditOrBankString + "s Available:");
        switch (caseNumber) {
            case CREDIT_CARD_OBJECT:
                creditOrBankString = "Credit Card";
                while (resultCard.hasNext()) {
                    CreditCard creditCard = (CreditCard) resultCard.next();
                    System.out.println(creditCard.getCreditCardId() + "\n");
                }
                break;
            case BANK_ACCOUNT_OBJECT:
                creditOrBankString = "Bank Account";
                while (resultBankAccount.hasNext()) {
                    BankAccount bankAccount = (BankAccount) resultBankAccount.next();
                    System.out.println(bankAccount.getBankAccountId() + "\n");
                }
                break;
            default:
                System.out.println("None available\n");
                return;
        }

        number = getToken("Enter " + creditOrBankString + " to process: ");
        donationAmount = database.getDonationAmount(donorID, number, caseNumber);
        switch (donationAmount) {
            case Database.PAYMENT_TYPE_NOT_FOUND:
                System.out.println("No matching " + creditOrBankString + " found");
                break;
            default:
                transactionID = database.processDonation(donorID, number, donationAmount, creditOrBankString);
                System.out.print("Donation amount: $" + donationAmount + ".00, Transaction ID: " + transactionID + "\n");
        }
    }



    /**
     * Method to exit the system.
     */
    private void exit() {
        int command = getNumber("Enter 14 if you'd like to save before exiting, enter any other number to exit");
        switch (command){
            case SAVE:
                save();
                break;
        }
    }

    /**
     * The method to start the application. Simply calls process().
     *
     * @param args
     *            not used
     */
    public static void main(String[] args) {
        UserInterface.instance().process();
    }


}

/**
 *
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010

 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *
 * This class implements the user interface for the Library project. The
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
    private static final int ADD_CREDIT_CARD = 2;
    private static final int PROCESS_DONATIONS = 3;
    private static final int LIST_TRANSACTIONS = 4;
    private static final int LIST_DONORS = 5;
    private static final int LIST_DONOR = 6;
    private static final int REMOVE_DONOR = 7;
    private static final int REMOVE_CREDIT_CARD = 8;
    private static final int SAVE = 9;
    private static final int HELP = 10;

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
        System.out.println("Enter a number between 0 and 12 as explained below:");
        System.out.println(EXIT + " to Exit\n");
        System.out.println(ADD_DONOR + " to  add a donor");
        System.out.println(ADD_CREDIT_CARD + " to  add a credit card");
        System.out.println(PROCESS_DONATIONS + " to  process donations");
        System.out.println(LIST_TRANSACTIONS + " to  list transactions ");
        System.out.println(LIST_DONORS + " to  list all donors ");
        System.out.println(LIST_DONOR + " to  list a specific donor");
        System.out.println(REMOVE_DONOR + " to  remove a donor");
        System.out.println(REMOVE_CREDIT_CARD + " to  remove a credit card");
        System.out.println(SAVE + " to  save data");
    }

    /**
     * Method to be called for adding a donor. Prompts the user for the
     * appropriate values and uses the appropriate Library method for adding the
     * donor.
     *
     */
    public void addDonor() {
        do {
            String name = getToken("Enter donor name");
            String phone = getToken("Enter phone");
            Donor result;
            result = database.addDonor(name, phone);
            if (result == null) {
                System.out.println("Could not add donor");
            }
            System.out.println(result);
        }while(yesOrNo("Add another donor?"));
    }

    /**
     * TODO
     */
    public void processDonations() {
        int donorID = getNumber("Enter donor id");
        System.out.println("Cards Available\n");
        for (Object objectCredit : database.getCreditCards(donorID)) {
            CreditCard creditNumber = (CreditCard) objectCredit;
            System.out.print(creditNumber.getCreditCardId()+"\n");
        }
        String creditCardNumber = getToken("Enter card to process");
        int donationAmount = database.getDonationAmount(creditCardNumber);
        String transactionID = database.processDonation(donorID, creditCardNumber, donationAmount);
        System.out.print("Donation amount: $" + donationAmount + ".00, Transaction ID: " + transactionID +"\n");
    }

    /**
     * Method to be called for displaying all transactions.
     *
     */
    public void listTransactions() {
        int donorID = getNumber("Enter donor id");
        Calendar date = getDate("Enter date mm/dd/yy");
        Iterator result = database.getTransactions(donorID, date);
        while (result.hasNext()) {
            Transaction transaction = (Transaction) result.next();
            System.out.println(transaction.toString() + "\n");
        }
        System.out.println("\n  There are no more transactions \n");
    }

    /**
     * Method to be called displaying all donors.
     *
     */
    public void listDonors() {
        Iterator result = database.getDonors();
        while (result.hasNext()) {
            Donor donor = (Donor) result.next();
            System.out.println(donor.toString() + "\n");
        }
        System.out.println("\n  There are no more donors \n");
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
            System.out.println(resultDonor);
            if (!yesOrNo("Find another Donor?")) {
                break;
            }
        } while (true);
    }

    /**
     * Method to be called for placing a hold. Prompts the user for the
     * appropriate values and uses the appropriate Library method for placing a
     * hold.
     *
     */
    public void removeDonor() {
        int donorID = Integer.parseInt(getToken("Enter donor id"));
        int result =  database.removeDonor(donorID);
        switch (result) {
            case Database.NO_SUCH_DONOR:
                System.out.println("Not a valid donor ID");
                break;
            default:
                System.out.println("An error has occurred");
        }
    }

    /**
     * Method to be called for removing a holds. Prompts the user for the
     * appropriate values and uses the appropriate Library method for removing a
     * hold.
     *
     */
    public void removeCreditCard() {
        int donorID =   getNumber("Enter donor id");
        String ccNumber = getToken("Enter credit card number");
        int result = database.removeCreditCard(donorID, ccNumber);
        switch (result) {
            case Database.CREDIT_CARD_NOT_FOUND:
                System.out.println("No such Credit Card");
                break;
            case Database.NO_SUCH_DONOR:
                System.out.println("Not a valid donor ID");
                break;
            case Database.OPERATION_COMPLETED:
                System.out.println("The hold has been removed");
                break;
            default:
                System.out.println("An error has occurred");
        }
    }

    /**
     * Method to be called for displaying transactions. Prompts the user for the
     * appropriate values and uses the appropriate Library method for displaying
     * transactions.
     *
     */
    public void getTransactions() {
        int donorID = getNumber("Enter donor id");
        Calendar date = getDate("Please enter the date for which you want records as mm/dd/yy");
        Iterator result = database.getTransactions(donorID, date);
        if (result == null) {
            System.out.println("Invalid Donor ID");
        } else {
            while (result.hasNext()) {
                Transaction transaction = (Transaction) result.next();
                System.out.println(transaction.toString() + "\n");
            }
            System.out.println("\n  There are no more transactions \n");
        }
    }

    /**
     * Method to be called for saving the Library object. Uses the appropriate
     * Library method for saving.
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
     * Library method for retrieval.
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
                    addDonor();
                    break;
                case ADD_CREDIT_CARD:
                    addCreditCard();
                    break;
                case PROCESS_DONATIONS:
                    processDonations();
                    break;
                case LIST_TRANSACTIONS:
                    listTransactions();
                    break;
                case LIST_DONOR:
                    listDonor();
                    break;
                case LIST_DONORS:
                    listDonors();
                    break;
                case REMOVE_DONOR:
                    removeDonor();
                    break;
                case REMOVE_CREDIT_CARD:
                    removeCreditCard();
                    break;
                case SAVE:
                    save();
                    break;
                case HELP:
                    help();
                    break;
            }
        }
    }

    private void addCreditCard() {
        int donorID = getNumber("Enter donor id");
        String creditCardNumber = getToken("Enter credit card number");
        int donationAmount = getNumber("Enter even dollar donation amount as integer");
        database.addCreditCard(donorID, creditCardNumber, donationAmount);
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
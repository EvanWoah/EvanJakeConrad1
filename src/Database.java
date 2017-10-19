/**
 * Created by evanwall on 9/21/17.
 */
//public class Database {
//
//    public static Database instance() {
//    }
//
//    public Donor addDonor(String name, String address, String phone) {
//    }
//
//    public static Database retrieve() {
//    }
//}



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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;

public class Database implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CREDIT_CARD_NOT_FOUND = 1;
    public static final int BOOK_NOT_ISSUED = 2;
    public static final int BOOK_HAS_HOLD = 3;
    public static final int BOOK_ISSUED = 4;
    public static final int HOLD_PLACED = 5;
    public static final int NO_HOLD_FOUND = 6;
    public static final int OPERATION_COMPLETED = 7;
    public static final int OPERATION_FAILED = 8;
    public static final int NO_SUCH_DONOR = 9;
    private CCControl cccontrol;
    private DonorList donorList;
    private static Database database;

    /**
     * Private for the singleton pattern Creates the catalog and member
     * collection objects
     */
    private Library() {
        catalog = Catalog.instance();
        memberList = MemberList.instance();
    }

    /**
     * Supports the singleton pattern
     *
     * @return the singleton object
     */
    public static Library instance() {
        if (library == null) {
            MemberIdServer.instance(); // instantiate all singletons
            return (library = new Library());
        } else {
            return library;
        }
    }

    /**
     * Organizes the operations for adding a book
     *
     * @param title
     *            book title
     * @param author
     *            author name
     * @param id
     *            book id
     * @return the Book object created
     */
    public Book addBook(String title, String author, String id) {
        Book book = new Book(title, author, id);
        if (catalog.insertBook(book)) {
            return (book);
        }
        return null;
    }

    /**
     * Organizes the operations for adding a member
     *
     * @param name
     *            member name
     * @param address
     *            member address
     * @param phone
     *            member phone
     * @return the Member object created
     */
    public Member addMember(String name, String address, String phone) {
        Member member = new Member(name, address, phone);
        if (memberList.insertMember(member)) {
            return (member);
        }
        return null;
    }

    /**
     * Organizes the placing of a hold
     *
     * @param memberId
     *            member's id
     * @param bookId
     *            book's id
     * @param duration
     *            for how long the hold should be valid in days
     * @return indication on the outcome
     */
    public int placeHold(String memberId, String bookId, int duration) {
        Book book = catalog.search(bookId);
        if (book == null) {
            return (BOOK_NOT_FOUND);
        }
        if (book.getBorrower() == null) {
            return (BOOK_NOT_ISSUED);
        }
        Member member = memberList.search(memberId);
        if (member == null) {
            return (NO_SUCH_MEMBER);
        }
        Hold hold = new Hold(member, book, duration);
        book.placeHold(hold);
        member.placeHold(hold);
        return (HOLD_PLACED);
    }

    /**
     * Searches for a given member
     *
     * @param memberId
     *            id of the member
     * @return true iff the member is in the member list collection
     */
    public Member searchMembership(String memberId) {
        return memberList.search(memberId);
    }

    /**
     * Processes holds for a single book
     *
     * @param bookId
     *            id of the book
     * @return the member who should be notified
     */
    public Member processHold(String bookId) {
        Book book = catalog.search(bookId);
        if (book == null) {
            return (null);
        }
        Hold hold = book.getNextHold();
        if (hold == null) {
            return (null);
        }
        hold.getMember().removeHold(bookId);
        hold.getBook().removeHold(hold.getMember().getId());
        return (hold.getMember());
    }

    /**
     * Removes a hold for a specific book and member combincation
     *
     * @param donorId
     *            id of the member
     * @param ccNumber
     *            book id
     * @return result of the operation
     */
    public int removeCreditCard(int donorId, int ccNumber) {
        Donor donor = donorList.search(memberId);
        if (donor == null) {
            return (NO_SUCH_DONOR);
        }
        CreditCard creditCard = cccontrol.search(ccNumber);
        if (book == null) {
            return (CREDIT_CARD_NOT_FOUND);
        }
        return member.removeHold(bookId) && book.removeHold(memberId) ? OPERATION_COMPLETED : NO_HOLD_FOUND;
    }

    /*
     * Removes all out-of-date holds
     */
    private void removeInvalidHolds() {
        for (Iterator catalogIterator = catalog.getBooks(); catalogIterator.hasNext();) {
            for (Iterator iterator = ((Book) catalogIterator.next()).getHolds(); iterator.hasNext();) {
                Hold hold = (Hold) iterator.next();
                if (!hold.isValid()) {
                    hold.getBook().removeHold(hold.getMember().getId());
                    hold.getMember().removeHold(hold.getBook().getId());
                }
            }
        }
    }

    /**
     * Organizes the issuing of a book
     *
     * @param memberId
     *            member id
     * @param bookId
     *            book id
     * @return the book issued
     */
    public Book issueBook(String memberId, String bookId) {
        Book book = catalog.search(bookId);
        if (book == null) {
            return (null);
        }
        if (book.getBorrower() != null) {
            return (null);
        }
        Member member = memberList.search(memberId);
        if (member == null) {
            return (null);
        }
        if (!(book.issue(member) && member.issue(book))) {
            return null;
        }
        return (book);
    }

    /**
     * Renews a book
     *
     * @param bookId
     *            id of the book to be renewed
     * @param memberId
     *            member id
     * @return the book renewed
     */
    public Book renewBook(String bookId, String memberId) {
        Book book = catalog.search(bookId);
        if (book == null) {
            return (null);
        }
        Member member = memberList.search(memberId);
        if (member == null) {
            return (null);
        }
        if ((book.renew(member) && member.renew(book))) {
            return (book);
        }
        return (null);
    }

    /**
     * Returns an iterator to the books issued to a member
     *
     * @param memberId
     *            member id
     * @return iterator to the collection
     */
    public Iterator getBooks(String memberId) {
        Member member = memberList.search(memberId);
        if (member == null) {
            return (null);
        } else {
            return (member.getBooksIssued());
        }
    }

    /**
     * Removes a specific book from the catalog
     *
     * @param bookId
     *            id of the book
     * @return a code representing the outcome
     */
    public int removeBook(String bookId) {
        Book book = catalog.search(bookId);
        if (book == null) {
            return (BOOK_NOT_FOUND);
        }
        if (book.hasHold()) {
            return (BOOK_HAS_HOLD);
        }
        if (book.getBorrower() != null) {
            return (BOOK_ISSUED);
        }
        if (catalog.removeBook(bookId)) {
            return (OPERATION_COMPLETED);
        }
        return (OPERATION_FAILED);
    }

    /**
     * Returns a single book
     *
     * @param bookId
     *            id of the book to be returned
     * @return a code representing the outcome
     */
    public int returnBook(String bookId) {
        Book book = catalog.search(bookId);
        if (book == null) {
            return (BOOK_NOT_FOUND);
        }
        Member member = book.returnBook();
        if (member == null) {
            return (BOOK_NOT_ISSUED);
        }
        if (!(member.returnBook(book))) {
            return (OPERATION_FAILED);
        }
        if (book.hasHold()) {
            return (BOOK_HAS_HOLD);
        }
        return (OPERATION_COMPLETED);
    }

    /**
     * Returns an iterator to the transactions for a specific member on a
     * certain date
     *
     * @param memberId
     *            member id
     * @param date
     *            date of issue
     * @return iterator to the collection
     */
    public Iterator getTransactions(String memberId, Calendar date) {
        Member member = memberList.search(memberId);
        if (member == null) {
            return (null);
        }
        return member.getTransactions(date);
    }

    /**
     * Retrieves a deserialized version of the library from disk
     *
     * @return a Library object
     */
    public static Library retrieve() {
        try {
            FileInputStream file = new FileInputStream("LibraryData");
            ObjectInputStream input = new ObjectInputStream(file);
            library = (Library) input.readObject();
            MemberIdServer.retrieve(input);
            return library;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }

    /**
     * Serializes the Library object
     *
     * @return true iff the data could be saved
     */
    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("LibraryData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(library);
            output.writeObject(MemberIdServer.instance());
            file.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    /**
     * String form of the library
     *
     */
    @Override
    public String toString() {
        return catalog + "\n" + memberList;
    }
}
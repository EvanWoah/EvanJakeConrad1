/**
 * @author Conrad Thompson, Evan Wall, Jake Flodquist
 * @Copyright (c) 2017
 */
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The collection class for donor objects
 *
 */
public class DonorControl implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Donor> donors = new LinkedList();
    private static DonorControl donorControl;
    public static int DONOR_ID_COUNT = 0;


    /*
     * Private constructor for singleton pattern
     * 
     */
    private DonorControl() {
    }

    /**
     * Supports the singleton pattern
     *
     * @return the singleton object
     */
    public static DonorControl instance() {
        if (donorControl == null) {
            return (donorControl = new DonorControl());
        } else {
            return donorControl;
        }
    }

    /**
     * Checks whether a donor with a given donor id exists.
     *
     * @param donorID
     *            the id of the member
     * @return true if member exists
     *
     */
    public Donor search(int donorID) {
        for (Iterator iterator = donors.iterator(); iterator.hasNext();) {
            Donor donor = (Donor) iterator.next();
            if (donor.getDonorID()==(donorID)) {
                return donor;
            }
        }
        return null;
    }

    /**
     * Method to get Donor Id
     * @return Donor Id
     */
    public static int getDonorID() {
        DONOR_ID_COUNT=DONOR_ID_COUNT+1;
        return DONOR_ID_COUNT;
    }

    /**
     * Inserts a donor into the collection
     *
     * @param donor
     *            the donor to be inserted
     * @return true iff the donor could be inserted. Currently always true
     */
    public boolean insertDonor(Donor donor) {
        donors.add(donor);
        return true;
    }

    /**
     * Method to remove a donor
     * @param donorID Donor Id
     * @return Boolean true if donor removed
     */
    public Boolean removeDonor(int donorID) {
        for (Donor donor: donors) {
            if (donor.getDonorID()==(donorID)){
                donors.remove(donor);
                return true;
            }
        }
        return false;
    }

    /**
     * String form of the collection
     *
     */
    @Override
    public String toString() {
        return donors.toString();
    }

    public Iterator getDonors() {
        if (donors.iterator() == null) {
            return (null);
        }
        return donors.iterator();
    }

    /**
     * Method to add credit cards
     * @param donorID Donor id
     * @param creditCardNumber Credit Card Number
    */
    public void addCreditCard(int donorID, String creditCardNumber, int donationAmount) {
        for (Donor donor: donors) {
            if (donor.getDonorID()==(donorID)){
                donor.addCreditCard(creditCardNumber, donationAmount);
            }
        }
    }

    public void removeCreditCard(int donorID, String creditCardNumber) {
        for (Donor donor: donors) {
            if (donor.getDonorID()==(donorID)){
                donor.removeCreditCard(creditCardNumber);
            }
        }
    }
}
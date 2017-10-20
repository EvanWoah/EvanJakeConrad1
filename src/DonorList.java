
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
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The collection class for Member objects
 *
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class DonorList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Donor> donors = new LinkedList();
    private static DonorList donorList;
    public static int DONOR_ID_COUNT = 0;


    /*
     * Private constructor for singleton pattern
     * 
     */
    private DonorList() {
    }

    /**
     * Supports the singleton pattern
     *
     * @return the singleton object
     */
    public static DonorList instance() {
        if (donorList == null) {
            return (donorList = new DonorList());
        } else {
            return donorList;
        }
    }

    /**
     * Checks whether a member with a given member id exists.
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
}
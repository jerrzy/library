package business;

import java.io.Serializable;
import java.time.LocalDate;


import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable{
    private String memberId;

    private CheckoutRecord checkoutRecord;

    public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
        super(fname, lname, tel, add);
        this.memberId = memberId;
        this.checkoutRecord = new CheckoutRecord(memberId);
    }


    public String getMemberId() {
        return memberId;
    }

    public CheckoutRecord getCheckoutRecord() {
        return checkoutRecord;
    }

    public String getAddressStr() {
        return getAddress().getStreet() + "\n" + getAddress().getCity()
                + "\n" + getAddress().getState() + "\n" + getAddress().getZip();
    }

    @Override
    public String toString() {
        return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + ", " + getTelephone() + " " + getAddress();
    }

    private static final long serialVersionUID = -2226197306790714013L;
}

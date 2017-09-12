package business;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CheckoutRecord implements Serializable{
    private static final long serialVersionUID = -2226197306788814013L;
    private String memberId;

    private List<CheckoutRecordEntry> checkoutRecordEntries;

    public CheckoutRecord(String memberId) {
        this.memberId = memberId;

        checkoutRecordEntries = new LinkedList<>();
    }

    public void addCheckoutRecordEntry(BookCopy bookCopy, Date dateOfCheckout, Date dueDate) {
        CheckoutRecordEntry checkoutRecordEntry = new CheckoutRecordEntry(bookCopy,dateOfCheckout,dueDate);

        checkoutRecordEntries.add(checkoutRecordEntry);
    }

    public String getMemberId() {
        return memberId;
    }

    public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
        return checkoutRecordEntries;
    }

//    @Override
//    public String toString(){
//        return "memberId:"+memberId+"\n checkoutRecordEntries:"+checkoutRecordEntries.toString();
//    }
}

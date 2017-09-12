package business;

import java.io.Serializable;
import java.util.Date;

public class CheckoutRecordEntry implements Serializable{
    private static final long serialVersionUID = -2226197306009814013L;

    private BookCopy bookCopy;
    private Date dateOfCheckout;
    private Date dueDate;

    CheckoutRecordEntry(BookCopy bookCopy, Date dateOfCheckout, Date dueDate){
        this.bookCopy = bookCopy;
        this.dateOfCheckout = dateOfCheckout;
        this.dueDate = dueDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public Date getDateOfCheckout() {
        return dateOfCheckout;
    }

    public Date getDueDate() {
        return dueDate;
    }
}

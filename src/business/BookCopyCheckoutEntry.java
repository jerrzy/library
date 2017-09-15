package business;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class BookCopyCheckoutEntry{
    private BookCopy bookCopy;
    private LibraryMember member;
    private CheckoutRecordEntry checkoutRecordEntry;

    public BookCopyCheckoutEntry(BookCopy bookCopy, LibraryMember member, CheckoutRecordEntry checkoutRecordEntry) {
        this.bookCopy = bookCopy;
        this.member = member;
        this.checkoutRecordEntry = checkoutRecordEntry;
    }

    public int getCopyNum() {
        return bookCopy == null ? -1 : bookCopy.getCopyNum();
    }

    public boolean getAvailable() {
        return bookCopy == null ? false : bookCopy.isAvailable();
    }

    public String getBorrowerId() {
        return member == null ? "" : member.getMemberId();
    }

    public String getBorrowerName() {
        return member == null ? "" : String.format("%s %s", member.getFirstName(), member.getLastName());
    }

    public LocalDate getCheckoutDate() {
        return checkoutRecordEntry == null ? LocalDate.of(1970, 1, 1) :
                checkoutRecordEntry.getDateOfCheckout();
    }

    public LocalDate getDueDate(){
        return checkoutRecordEntry == null ? LocalDate.of(1970, 1, 1) :
                checkoutRecordEntry.getDueDate();
    }

}

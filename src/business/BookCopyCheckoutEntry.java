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

    public String getCheckoutDate() {
        return checkoutRecordEntry == null ?"":
                checkoutRecordEntry.getDateOfCheckout().toString();
    }

    public String getDueDate(){
        return checkoutRecordEntry == null ? "" :
                checkoutRecordEntry.getDueDate().toString();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(getCopyNum()).append("\t");

        sb.append(getAvailable()).append("\t");
        sb.append(getBorrowerId()).append("\t");
        sb.append(getBorrowerName()).append("\t");
        sb.append(getCheckoutDate()).append("\t");
        sb.append(getDueDate()).append("\t");

        return sb.toString();
    }

}

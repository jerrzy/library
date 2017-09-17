package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class CheckoutRecordEntry implements Serializable{
    private static final long serialVersionUID = -2226197306009814013L;

    private BookCopy bookCopy;
    private LocalDate dateOfCheckout;
    private LocalDate dueDate;

    CheckoutRecordEntry(BookCopy bookCopy, LocalDate dateOfCheckout, LocalDate dueDate){
        this.bookCopy = bookCopy;
        this.dateOfCheckout = dateOfCheckout;
        this.dueDate = dueDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public LocalDate getDateOfCheckout() {
        return dateOfCheckout;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getBookTile(){
        return this.bookCopy.getBook().getTitle();
    }

    public String getBookIsbn(){
        return this.bookCopy.getBook().getIsbn();
    }

    public int getBookCopyNum(){
        return this.bookCopy.getCopyNum();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(getBookTile()).append("\t");
        sb.append(getBookIsbn()).append("\t");
        sb.append(getBookCopyNum()).append("\t");
        sb.append(getDateOfCheckout()).append("\t");
        sb.append(getDueDate()).append("\t");

        return sb.toString();
    }

}

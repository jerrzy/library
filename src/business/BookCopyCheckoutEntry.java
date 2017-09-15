package business;

public class BookCopyCheckoutEntry{
    private BookCopy bookCopy;
    private LibraryMember member;
    private CheckoutRecordEntry checkoutRecordEntry;

    public BookCopyCheckoutEntry(BookCopy bookCopy, LibraryMember member, CheckoutRecordEntry checkoutRecordEntry) {
        this.bookCopy = bookCopy;
        this.member = member;
        this.checkoutRecordEntry = checkoutRecordEntry;
    }



}

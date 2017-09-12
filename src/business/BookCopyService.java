package business;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class BookCopyService{

    public void addBookCopy(String isbn) throws LibrarySystemException {
        if (isEmpty(isbn)) {
            throw new LibrarySystemException("isbn can not be empty!");
        }
        DataAccess da = new DataAccessFacade();

        Book book = da.findBookByIsbn(isbn);

        if (book == null) {
            throw new LibrarySystemException("The book whose isbn is " + isbn + " doesn't exist!");
        }

        book.addCopy();

        da.saveBook(book);
    }

    public void checkoutBook(String memberId, String isbn) throws LibrarySystemException {
        if (isEmpty(memberId)) {
            throw new LibrarySystemException("memberId can not be empty!");
        }

        if (isEmpty(isbn)) {
            throw new LibrarySystemException("isbn can not be empty!");
        }

        DataAccess da = new DataAccessFacade();

        LibraryMember member = da.findMemberById(memberId);
        if (member == null) {
            throw new LibrarySystemException("Member doesn't exist! memberId=" + memberId);
        }

        Book book = da.findBookByIsbn(isbn);
        if (book == null) {
            throw new LibrarySystemException("Book doesn't exist! isbn=" + isbn);
        }

        if (!book.isAvailable()) {
            throw new LibrarySystemException("Book is not available! isbn=" + isbn);
        }

        BookCopy bookCopy = book.getNextAvailableCopy();

        bookCopy.changeAvailability();

        Date dateOfCheckout = new Date();
        Date dueDate = Date.from(LocalDateTime.from(dateOfCheckout.toInstant()).plus(book.getMaxCheckoutLength(), ChronoUnit.DAYS).toInstant(ZoneOffset.of("-06:00")));

        member.getCheckoutRecord().addCheckoutRecordEntry(bookCopy,dateOfCheckout,dueDate);

        da.saveNewMember(member);

        da.saveBook(book);
    }

    private boolean isEmpty(String content) {
        if (content == null || content.trim().equals("")) {
            return true;
        }

        return false;
    }
}

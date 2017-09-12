package business;

import java.util.Date;

public class MemberService{

    public void checkout(BookCopy bookCopy, Date dateOfCheckout, Date dueDate)
            throws LibrarySystemException{
        //TODO
        if(bookCopy == null || bookCopy.isAvailable()){
            throw new LibrarySystemException("bookCopy is not available!");
        }

    }
}

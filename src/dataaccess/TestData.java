package dataaccess;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

import business.*;


public class TestData{
    List<LibraryMember> members = new ArrayList<LibraryMember>();
    @SuppressWarnings ("serial")


    List<Address> addresses = new ArrayList<Address>(){
        {
            add(new Address("101 S. Main", "Fairfield", "IA", "52556"));
            add(new Address("51 S. George", "Georgetown", "MI", "65434"));
            add(new Address("23 Headley Ave", "Seville", "Georgia", "41234"));
            add(new Address("1 N. Baton", "Baton Rouge", "LA", "33556"));
            add(new Address("5001 Venice Dr.", "Los Angeles", "CA", "93736"));
            add(new Address("1435 Channing Ave", "Palo Alto", "CA", "94301"));
            add(new Address("42 Dogwood Dr.", "Fairfield", "IA", "52556"));
            add(new Address("501 Central", "Mountain View", "CA", "94707"));
        }
    };
    @SuppressWarnings ("serial")
    public List<Author> allAuthors = new ArrayList<Author>(){
        {
            add(new Author("Joe", "Thomas", "641-445-2123", addresses.get(0), "1", "A happy man is he.", "1001"));
            add(new Author("Sandra", "Thomas", "641-445-2123", addresses.get(0), "2", "A happy wife is she.", "1002"));
            add(new Author("Nirmal", "Pugh", "641-919-3223", addresses.get(1), "3", "Thinker of thoughts.", "1003"));
            add(new Author("Andrew", "Cleveland", "976-445-2232", addresses.get(2), "6", "Author of childrens' books.", "1005"));
            add(new Author("Sarah", "Connor", "123-422-2663", addresses.get(3), "5", "Known for her clever style.", "1006"));
        }
    };


    @SuppressWarnings ("serial")
    List<Book> allBooks = new ArrayList<Book>(){
        {
            add(new Book("23-11451", "The Big Fish", 21, Arrays.asList(allAuthors.get(0), allAuthors.get(1))));
            add(new Book("28-12331", "Antartica", 7, Arrays.asList(allAuthors.get(2))));
            add(new Book("99-22223", "Thinking Java", 21, Arrays.asList(allAuthors.get(3))));
            add(new Book("48-56882", "Jimmy's First Day of School", 7, Arrays.asList(allAuthors.get(4))));

        }
    };


    @SuppressWarnings ("serial")
    List<User> allUsers = new ArrayList<User>(){
        {
            add(new User("101", "xyz", Auth.LIBRARIAN));
            add(new User("102", "abc", Auth.ADMIN));
            add(new User("103", "111", Auth.BOTH));
        }
    };

    public static void main(String[] args) {
        TestData td = new TestData();
        //		td.bookData();
        //		td.libraryMemberData();
        //		td.userData();
        //		DataAccess da = new DataAccessFacade();
        //		System.out.println(da.readBooksMap());
        //		System.out.println(da.readUserMap());

        //        td.initUniqueId();
        //		td.loadBookCopyToMemberId();

        td.loadAuthors();
    }

    public void initUniqueId() {
        DataAccessFacade.loadUniqueId(5000);
    }

    ///create books
    public void bookData() {
        allBooks.get(0).addCopy();
        allBooks.get(0).addCopy();
        allBooks.get(1).addCopy();
        allBooks.get(3).addCopy();
        allBooks.get(2).addCopy();
        allBooks.get(2).addCopy();
        DataAccessFacade.loadBookMap(allBooks);
    }

    public void userData() {
        DataAccessFacade.loadUserMap(allUsers);
    }

    public void loadAuthors() {
        DataAccess da = DataAccessFactory.getInstance();
        HashMap<String, Book> isbnBookMap = da.readBooksMap();

        List<Author> authors = new LinkedList<>();
        for (Map.Entry<String, Book> e : isbnBookMap.entrySet()) {
            authors.addAll(e.getValue().getAuthors());
        }

        DataAccessFacade.loadAuthors(authors);
    }

    //create library members

    public void libraryMemberData() {
        LibraryMember libraryMember = new LibraryMember("1001", "Andy", "Rogers", "641-223-2211", addresses.get(4));

        Book book = allBooks.get(0);
        BookCopy bookCopy = book.getNextAvailableCopy();
        bookCopy.changeAvailability();
        LocalDate dateOfCheckout = LocalDate.now();

        LocalDate dueDate = dateOfCheckout.plus(book.getMaxCheckoutLength(), ChronoUnit.DAYS);

        libraryMember.getCheckoutRecord().addCheckoutRecordEntry(bookCopy, dateOfCheckout, dueDate);

        members.add(libraryMember);
        libraryMember = new LibraryMember("1002", "Drew", "Stevens", "702-998-2414", addresses.get(5));
        members.add(libraryMember);

        libraryMember = new LibraryMember("1003", "Sarah", "Eagleton", "451-234-8811", addresses.get(6));
        members.add(libraryMember);

        libraryMember = new LibraryMember("1004", "Ricardo", "Montalbahn", "641-472-2871", addresses.get(7));
        members.add(libraryMember);

        DataAccessFacade.loadMemberMap(members);


    }


    public void loadBookCopyToMemberId() {
        DataAccess da = DataAccessFactory.getInstance();

        HashMap<String, LibraryMember> idMemberMap = da.readMemberMap();

        Map<String, String> bookCopyToMemberIdMap = new HashMap<>();

        for (Map.Entry<String, LibraryMember> e : idMemberMap.entrySet()) {
            LibraryMember member = e.getValue();
            List<CheckoutRecordEntry> checkoutRecordEntries = member.getCheckoutRecord().getCheckoutRecordEntries();

            for (CheckoutRecordEntry cte : checkoutRecordEntries) {
                bookCopyToMemberIdMap.put(Utils.getBookCopyUniqueKey(cte.getBookCopy().getBook(), cte.getBookCopy()), member.getMemberId());
            }

        }

        DataAccessFactory.getInstance().saveBookCopyToMember(bookCopyToMemberIdMap);

    }
}

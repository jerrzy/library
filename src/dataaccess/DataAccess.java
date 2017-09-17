package dataaccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.Author;
import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess {
	String getUniqueId();
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();

    LibraryMember findMemberById(String memberId);

	public void saveNewMember(LibraryMember member);

	void saveBook(Book book);

	Book findBookByIsbn(String isbn);

	void saveBookCopyToMember(Map<String, String> bookCopyToMemberIdMap);
	HashMap<String, String> readBookCopyToMember();

	void save(Author author);
    HashMap<String, Author> readAUthors();
}

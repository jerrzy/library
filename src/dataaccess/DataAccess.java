package dataaccess;

import java.util.HashMap;

import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();

    LibraryMember findMemberById(String memberId);

	public void saveNewMember(LibraryMember member);

	void saveBook(Book book);

	Book findBookByIsbn(String isbn);
}

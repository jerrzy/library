package dao;

import java.util.ArrayList;
import java.util.List;

import Domain.Book;
import Domain.Member;
import Domain.RecordEntry;

public class DataAccess {

	private List<Book> books;
	private List<Member> members;
	private List<RecordEntry> records;
	private static DataAccess dataAccess;
	
	public static DataAccess instance(){
		if(dataAccess == null){
			dataAccess = new DataAccess();
		}
		return dataAccess;
	}
	
	private DataAccess() {
		super();
		
		books = new ArrayList<>();
		books.add(new Book("1", "shuling","shuling", "shuling"));
		
		members = new ArrayList<>();
		members.add(new Member("1", "shuling", "shuling", "shuling"));
	}


	public Book loadBook(String id){
		for(Book book : books){
			if(book.id.equals(id)){
				return book;
			}
		}
		return null;
	}
	
	public Member loadMember(String id){
		for(Member book : members){
			if(book.id.equals(id)){
				return book;
			}
		}
		return null;
	}
	
	public List<Book> getBooks(){
		return books;
	}
	
	public List<Member> getMembers(){
		return members;
		
	}
	
	public void addMember(Member m) {
		members.add(m);
	}
	
	public void addBook(Book book) {
		books.add(book);
	}
	
	public boolean issue(String bookId, String memId){
		for(Book book : books){
			if(book.id.equals(bookId)){
				book.isAviable = false;
			}
		}
		records.add(new RecordEntry(bookId, memId));
		return true;
	}
	
	public void returnBook(String bookId){
		for(Book book : books){
			if(book.id.equals(bookId)){
				book.isAviable = true;
				break;
			}
		}
		RecordEntry recordEntry = null;
		for(RecordEntry record : records){
			if(record.bookId.equals(bookId)){
				recordEntry = record;
				break;
			}
		}
		if(recordEntry!=null){
			records.remove(recordEntry);
		}
	}
	
	public RecordEntry getIssueRecord(String bookId){
		for(RecordEntry record : records){
			if(record.bookId.equals(bookId)){
				return record;
			}
		}
		return null;
	}
}

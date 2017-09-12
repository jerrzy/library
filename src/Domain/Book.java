package Domain;

public class Book {

	public String id;
	public String name;
	public String author;
	public String publisher;
	public boolean isAviable;
	public Book(String id, String name, String author, String publisher) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.isAviable = true;
	}
	
	public Book clone(){
		return new Book(id, name, author, publisher);
	}
}

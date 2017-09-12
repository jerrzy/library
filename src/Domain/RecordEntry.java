package Domain;

import java.time.LocalDateTime;

public class RecordEntry {

	public String menId;
	public String bookId;
	public String date;
	public int reNewCount;
	
	public RecordEntry(String menId, String bookId) {
		super();
		this.menId = menId;
		this.bookId = bookId;
		this.date = LocalDateTime.now().toString();
		this.reNewCount = 0;
	}
	
}

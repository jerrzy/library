package Domain;

public class Member {

	public String id;
	public String name;
	public String mobile;
	public String email;
	public Member(String id, String name, String mobile, String email) {
		super();
		this.id = id;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
	}
	
	public Member clone(){
		return new Member(id, name, mobile, email);
	}
}

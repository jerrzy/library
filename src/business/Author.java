package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private String credentials;
	private String bio;

	public String getCredentials() {
		return credentials;
	}

	public String getBio() {
		return bio;
	}
	

	public Author(String f, String l, String t, Address a, String credentials, String bio) {
		super(f, l, t, a);
        this.credentials = credentials;
		this.bio = bio;
	}

	private static final long serialVersionUID = 7508481940058530471L;

}

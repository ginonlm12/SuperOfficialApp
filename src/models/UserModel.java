package models;

public class UserModel {
    private String username;
    private String passwd;

    private String name;

    public UserModel(String username, String passwd, String name) {
        this.username = username;
        this.passwd = passwd;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UserModel() {
    }

    public UserModel(String username, String passwd) {
		this.username = username;
		this.passwd = passwd;
	}

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
}

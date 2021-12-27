package common;

import java.io.Serializable;

public class W4CNormal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private User user;
	
	public W4CNormal(int code,User user)
	{
		this.code=code;
		this.user=user;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "W4C [code=" + code + ", user=" + user + "]";
	}
	
	
}

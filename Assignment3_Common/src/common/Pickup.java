package common;

public class Pickup {
	private User user;
	private String date;
	private String hour;
	private String minute;
	
	public Pickup(User user, String date, String hour, String minute) {
		this.user=user;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	@Override
	public String toString() {
		return "Pickup [user=" + user + ", date=" + date + ", hour=" + hour + ", minute=" + minute + "]";
	}
}

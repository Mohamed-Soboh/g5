package common;

public class W4CBussiness extends W4CNormal {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double money;

	public W4CBussiness(int code, User user, double money) {
		super(code, user);
		this.money = money;
	}
	
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return super.toString()+" W4CBussiness [money=" + money + "]";
	}

}

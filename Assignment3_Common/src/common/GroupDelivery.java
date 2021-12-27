package common;

import java.io.Serializable;

public class GroupDelivery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int groupNum;
	int groupSize;
	
	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

	public int getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}

	public GroupDelivery(int groupNum, int groupSize) {
		this.groupNum = groupNum;
		this.groupSize = groupSize;
	}
	
	 
}

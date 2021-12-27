package common;

import java.io.Serializable;

public class MessagesClass implements Serializable
{
	public Normal getMsgData2() {
		return msgData2;
	}
	public void setMsgData2(Normal msgData2) {
		this.msgData2 = msgData2;
	}
	private static final long serialVersionUID = 1L;

	private Messages msgType;
	private Object msgData;
	private Object msgData1;

	private int month;

	private int year;

	private int ResID;

	private Normal msgData2;
	public MessagesClass(Messages msgType,Visa visa,User user1,Normal norUser1) {
		this.msgType = msgType;
		this.msgData=visa;
		this.msgData1=user1;
		this.msgData2=norUser1;
	}
	public MessagesClass(Messages msgType,int res,int month,int year) {
		this.msgType = msgType;
		this.month=month;
		this.year=year;
		this.ResID=res;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getResID() {
		return ResID;
	}
	public void setResID(int resID) {
		ResID = resID;
	}
	public MessagesClass(Messages msgType,Object msgData,int month,int year) {
		this.msgType = msgType;
		setMsgData(msgData);
		this.month=month;
		this.year=year;
	}

	public MessagesClass(Messages msgType, Object msgData) {
		this.msgType = msgType;
		this.msgData = msgData;
	}
	@Override
	public String toString() {
		return "MessagesClass [msgType=" + msgType + "]";
	}
	public MessagesClass(Messages msgType,Object msgData,Object msgData1) {
		this.msgType = msgType;
		this.msgData = msgData;
		this.setMsgData1(msgData1);
	}
	public Messages getMsgType() {
		return msgType;
	}
	public void setMsgData(Object msgData) {
		this.msgData = msgData;
	}
	public Object getMsgData() {
		return msgData;
	}
	public Object getMsgData1() {
		return msgData1;
	}
	public void setMsgData1(Object msgData1) {
		this.msgData1 = msgData1;
	}
	
	


}
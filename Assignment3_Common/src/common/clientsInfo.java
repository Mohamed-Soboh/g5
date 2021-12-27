package common;

import ocsf.server.ConnectionToClient;

public class clientsInfo {
	ConnectionToClient client;
	int code;
	
	
	
	public clientsInfo(ConnectionToClient client, int code) {
		super();
		this.client = client;
		this.code = code;
	}
	public ConnectionToClient getClient() {
		return client;
	}
	public void setClient(ConnectionToClient client) {
		this.client = client;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}

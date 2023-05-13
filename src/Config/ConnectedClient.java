package Config;

public class ConnectedClient {
    private String ip;
    private String clientname;
    // other fields as needed
    
    public ConnectedClient(String ip, String clientname) {
        this.ip = ip;
        this.clientname = clientname;
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String username) {
		this.clientname = username;
	}
    
    
}

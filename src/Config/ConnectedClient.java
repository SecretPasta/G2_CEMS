package Config;

public class ConnectedClient {
    private String ip;
    private String username;
    // other fields as needed
    
    public ConnectedClient(String ip, String username) {
        this.ip = ip;
        this.username = username;
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
    
}

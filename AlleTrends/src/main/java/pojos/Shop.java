package pojos;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Shop {

	private String login;
	private String name;

	@Transient
	private String sessionPort;

	@Transient
	private long sessionPortTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@Id
	private Long id;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSessionPort() {
		return sessionPort;
	}

	public long getSessionPortTime() {
		return sessionPortTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSessionPort(String sessionHandlePart) {
		this.sessionPort = sessionHandlePart;
	}

	public void setSessionPortTime(long serverTime) {
		this.sessionPortTime = serverTime;

	}

	public void setId(long userId) {
		this.id = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}



package org.javahispano.javaleague.server.dao.domain;

import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.dto.BaseEntity;
import org.javahispano.javaleague.shared.dto.UserDto;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Index
@Entity
public class User extends BaseEntity {
	private String userName;
	private String hashPassword;
	private String email;
	private boolean active;
	private boolean tacticOK;
	private boolean awaitingMatch;
	private String token;
	private String teamName;

	@Load
	private Ref<UserProperties> userProperties;

	public User() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the userProperties
	 */
	public UserProperties getUserProperties() {
		return Deref.deref(userProperties);
	}

	/**
	 * @param userProperties
	 *            the userProperties to set
	 */
	public void setUserProperties(UserProperties userProperties) {
		if (userProperties != null) {
			this.userProperties = Ref.create(userProperties);
		} else {
			this.userProperties = null;
		}
	}

	/**
	 * @return the tacticOK
	 */
	public boolean isTacticOK() {
		return tacticOK;
	}

	/**
	 * @param tacticOK
	 *            the tacticOK to set
	 */
	public void setTacticOK(boolean tacticOK) {
		this.tacticOK = tacticOK;
	}

	/**
	 * @return the awaitingMatch
	 */
	public boolean isAwaitingMatch() {
		return awaitingMatch;
	}

	/**
	 * @param awaitingMatch
	 *            the awaitingMatch to set
	 */
	public void setAwaitingMatch(boolean awaitingMatch) {
		this.awaitingMatch = awaitingMatch;
	}

	@Override
	public String toString() {
		String s = "{ User ";
		s += "id=" + id + " ";
		s += "username=" + userName + " ";
		s += "hasPassword=" + hashPassword + " ";
		s += "email=" + email + " ";
		s += "active=" + active + " ";
		s += "tacticOK=" + tacticOK + " ";
		s += "awaitingMatch" + awaitingMatch + " ";
		s += "token= " + token + " ";
		s += "teamName=" + teamName + " ";
		s += "}";
		return s;
	}

	public static UserDto createDto(User user) {
		if (user == null) {
			return null;
		}
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUserName(user.getUserName());
		userDto.setEmail(user.getEmail());
		userDto.setActive(user.isActive());
		userDto.setTacticOK(user.isTacticOK());
		userDto.setAwaitingMatch(user.isAwaitingMatch());
		userDto.setToken(user.getToken());
		userDto.setTeamName(user.getTeamName());

		return userDto;
	}

	public static User create(UserDto userDto) {
		if (userDto == null) {
			return null;
		}

		User user = new User();
		user.setHashPassword(userDto.getHashPassword());
		user.setId(userDto.getId());
		user.setUserName(userDto.getUserName());
		user.setEmail(userDto.getEmail());
		user.setActive(userDto.isActive());
		user.setTacticOK(userDto.isTacticOK());
		user.setAwaitingMatch(userDto.isAwaitingMatch());
		user.setToken(userDto.getToken());
		user.setTeamName(userDto.getTeamName());

		return user;
	}
}

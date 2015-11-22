/*
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.javahispano.javaleague.shared.dto;

public class UserDto extends BaseEntity {
    private String userName;
    private String hashPassword;
    private String email;
    private boolean active;
    private boolean tacticOK;
    private String token;
    private String teamName;

    public UserDto() {
    }

    public UserDto(
            String username,
            String hashPassword,
            String email,
            boolean active,
            boolean tacticOK,
            String token,
            String teamName) {
        this.userName = username;
        this.hashPassword = hashPassword;
        this.email = email;
        this.active = active;
        this.tacticOK = tacticOK;
        this.token = token;
        this.teamName = teamName;
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
	 * @param email the email to set
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
	 * @param active the active to set
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
	 * @param token the token to set
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
	 * @return the tacticOK
	 */
	public boolean isTacticOK() {
		return tacticOK;
	}

	/**
	 * @param tacticOK the tacticOK to set
	 */
	public void setTacticOK(boolean tacticOK) {
		this.tacticOK = tacticOK;
	}

	@Override
    public String toString() {
        String s = " { User ";
        s += "id=" + id + " ";
        s += "username=" + userName + " ";
        s += "hasPassword=" + hashPassword + " ";
        s += "email=" + email + " ";
        s += "active=" + active + " ";
        s += "tacticOK=" + tacticOK + " ";
        s += "token=" + token + " ";
        s += "teamName=" + teamName + " ";
        s += " User } ";
        return s;
    }
}

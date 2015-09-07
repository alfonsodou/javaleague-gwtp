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

package org.javahispano.javaleague.server.dao.domain;

import org.javahispano.javaleague.shared.dto.BaseEntity;
import org.javahispano.javaleague.shared.dto.UserDto;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Index
@Entity
public class User extends BaseEntity {
    private String userName;
    private String hashPassword;
    private String email;
    private boolean active;
    private String token;
    private String teamName;

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

	@Override
    public String toString() {
        String s = "{ User ";
        s += "id=" + id + " ";
        s += "username=" + userName + " ";
        s += "hasPassword=" + hashPassword + " ";
        s += "email=" + email + " ";
        s += "active=" + active + " ";
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
        user.setToken(userDto.getToken());
        user.setTeamName(userDto.getTeamName());

        return user;
    }
}

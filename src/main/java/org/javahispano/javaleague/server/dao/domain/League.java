/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import org.javahispano.javaleague.shared.dto.BaseEntity;
import org.javahispano.javaleague.shared.dto.LeagueDto;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * @author alfonso
 *
 */
@Index
@Entity
public class League extends BaseEntity {
	private String description;
	
	public League() {
		this.description = "";
	}
	
	public League(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static LeagueDto createDto(League league) {
		if (league == null) {
			return null;
		}
		
		LeagueDto leagueDto = new LeagueDto();
		leagueDto.setId(league.getId());
		
		return leagueDto;
	}
}

/**
 * 
 */
package org.javahispano.javaleague.shared.dto;

/**
 * @author adou
 *
 */
public class LeagueDto extends BaseEntity {
	private String description;
	
	public LeagueDto() {
		this.description = "";
	}

	public LeagueDto(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}

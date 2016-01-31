/**
 * 
 */
package org.javahispano.javaleague.server.dao;

import java.util.List;

import org.javahispano.javaleague.server.dao.domain.Journey;
import org.javahispano.javaleague.server.dao.domain.League;

/**
 * @author alfonso
 *
 */
public class LeagueDao extends BaseDao<League> {
	public LeagueDao() {
		super(League.class);
	}

}

/**
 * 
 */
package org.javahispano.javaleague.server.dao;

import java.util.List;

import org.javahispano.javaleague.server.dao.domain.FinalMatch;
import org.javahispano.javaleague.server.dao.domain.FinalMatchType;

/**
 * @author alfonso
 *
 */
public class FinalMatchDao extends BaseDao<FinalMatch> {
	public FinalMatchDao() {
		super(FinalMatch.class);
	}

	public List<FinalMatch> findByType(FinalMatchType finalMatchType) {
		return ofy().load().type(FinalMatch.class)
				.filter("type", finalMatchType).order("order").orderKey(true)
				.list();
	}
}

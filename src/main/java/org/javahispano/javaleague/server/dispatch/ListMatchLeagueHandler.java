/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.JourneyDao;
import org.javahispano.javaleague.server.dao.LeagueDao;
import org.javahispano.javaleague.server.dao.MatchDao;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchLeagueAction;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchLeagueResult;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author alfonso
 *
 */
public class ListMatchLeagueHandler extends
		AbstractActionHandler<ListMatchLeagueAction, ListMatchLeagueResult> {
	private final Logger logger;
	private final MatchDao matchDao;
	private final LeagueDao leagueDao;
	private final JourneyDao journeyDao;

	@Inject
	ListMatchLeagueHandler(Logger logger, MatchDao matchDao, LeagueDao leagueDao, JourneyDao journeyDao) {
		super(ListMatchLeagueAction.class);
		this.logger = logger;
		this.matchDao = matchDao;
		this.leagueDao = leagueDao;
		this.journeyDao = journeyDao;
	}

	@Override
	public ListMatchLeagueResult execute(ListMatchLeagueAction arg0,
			ExecutionContext arg1) throws ActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo(ListMatchLeagueAction arg0, ListMatchLeagueResult arg1,
			ExecutionContext arg2) throws ActionException {
		// TODO Auto-generated method stub
		
	}
}

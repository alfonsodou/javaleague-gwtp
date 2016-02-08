/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.JourneyDao;
import org.javahispano.javaleague.server.dao.LeagueDao;
import org.javahispano.javaleague.server.dao.domain.League;
import org.javahispano.javaleague.shared.dispatch.journey.ListJourneyAction;
import org.javahispano.javaleague.shared.dispatch.journey.ListJourneyResult;
import org.javahispano.javaleague.shared.dto.LeagueDto;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author adou
 *
 */
public class ListJourneyHandler extends
		AbstractActionHandler<ListJourneyAction, ListJourneyResult> {
	private final Logger logger;
	private final LeagueDao leagueDao;
	private final JourneyDao journeyDao;

	@Inject
	ListJourneyHandler(Logger logger, LeagueDao leagueDao, JourneyDao journeyDao) {
		super(ListJourneyAction.class);
		this.logger = logger;
		this.leagueDao = leagueDao;
		this.journeyDao = journeyDao;
	}

	@Override
	public ListJourneyResult execute(ListJourneyAction arg0,
			ExecutionContext arg1) throws ActionException {
		League league = leagueDao.get(arg0.getLeagueId());
		LeagueDto leagueDto = League.createDto(league);

		return new ListJourneyResult(leagueDto.getJourneys());
	}

	@Override
	public void undo(ListJourneyAction arg0, ListJourneyResult arg1,
			ExecutionContext arg2) throws ActionException {

	}

}

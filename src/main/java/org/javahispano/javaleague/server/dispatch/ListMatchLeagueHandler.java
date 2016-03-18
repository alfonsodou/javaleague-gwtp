/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.JourneyDao;
import org.javahispano.javaleague.server.dao.LeagueDao;
import org.javahispano.javaleague.server.dao.MatchDao;
import org.javahispano.javaleague.server.dao.domain.Journey;
import org.javahispano.javaleague.server.dao.domain.League;
import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchLeagueAction;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchLeagueResult;
import org.javahispano.javaleague.shared.dto.MatchDto;

import com.googlecode.objectify.Ref;
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
		League league = leagueDao.get(arg0.getLeagueId());
		List<Ref<Match>> listMatchs = new ArrayList<Ref<Match>>();
		for(Ref<Journey> refJourney : league.getJourneys()) {
			Journey journey = Deref.deref(refJourney);
			listMatchs.addAll(journey.getMatchs());
		}
		List<MatchDto> listMatchDto = new ArrayList<MatchDto>();
		for(Ref<Match> refMatch : listMatchs) {
			Match match = Deref.deref(refMatch);
			listMatchDto.add(Match.createDto(match));
		}
		ListMatchLeagueResult listMatchLeagueResult = new ListMatchLeagueResult(listMatchDto, new Date());
		
		return listMatchLeagueResult;
	}

	@Override
	public void undo(ListMatchLeagueAction arg0, ListMatchLeagueResult arg1,
			ExecutionContext arg2) throws ActionException {
		// TODO Auto-generated method stub
		
	}
}

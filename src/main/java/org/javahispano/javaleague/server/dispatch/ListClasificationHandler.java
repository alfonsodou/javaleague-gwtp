/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.LeagueDao;
import org.javahispano.javaleague.server.dao.domain.Clasification;
import org.javahispano.javaleague.server.dao.domain.Journey;
import org.javahispano.javaleague.server.dao.domain.League;
import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.dispatch.clasification.ListClasificationAction;
import org.javahispano.javaleague.shared.dispatch.clasification.ListClasificationResult;
import org.javahispano.javaleague.shared.dto.ClasificationDto;

import com.googlecode.objectify.Ref;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author alfonso
 *
 */
public class ListClasificationHandler extends
		AbstractActionHandler<ListClasificationAction, ListClasificationResult> {
	private final Logger logger;
	private final LeagueDao leagueDao;

	@Inject
	ListClasificationHandler(Logger logger, LeagueDao leagueDao) {
		super(ListClasificationAction.class);
		this.logger = logger;
		this.leagueDao = leagueDao;
	}

	@Override
	public ListClasificationResult execute(ListClasificationAction arg0,
			ExecutionContext arg1) throws ActionException {
		League league = leagueDao.get(arg0.getLeagueId());
		List<Ref<Clasification>> listClasification = new ArrayList<Ref<Clasification>>();

		Journey journey = Deref.deref(league.getJourneys().get(
				league.getRound() - 1));
		listClasification.addAll(journey.getClasifications());

		List<ClasificationDto> listClasificationDto = new ArrayList<ClasificationDto>();
		for (Ref<Clasification> refClasification : listClasification) {
			listClasificationDto.add(Clasification.createDto(Deref
					.deref(refClasification)));
		}
		return new ListClasificationResult(listClasificationDto,
				league.getRound());
	}

	@Override
	public void undo(ListClasificationAction arg0,
			ListClasificationResult arg1, ExecutionContext arg2)
			throws ActionException {

	}

}

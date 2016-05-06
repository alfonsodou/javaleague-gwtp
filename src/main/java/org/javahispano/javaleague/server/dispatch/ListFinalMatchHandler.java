/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.FinalMatchDao;
import org.javahispano.javaleague.server.dao.domain.FinalMatch;
import org.javahispano.javaleague.shared.dispatch.match.ListFinalMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.ListFinalMatchResult;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author alfonso
 *
 */
public class ListFinalMatchHandler extends
		AbstractActionHandler<ListFinalMatchAction, ListFinalMatchResult> {
	private final Logger logger;
	private final FinalMatchDao finalMatchDao;

	@Inject
	ListFinalMatchHandler(Logger logger, FinalMatchDao finalMatchDao) {
		super(ListFinalMatchAction.class);
		this.logger = logger;
		this.finalMatchDao = finalMatchDao;
	}

	@Override
	public ListFinalMatchResult execute(ListFinalMatchAction arg0,
			ExecutionContext arg1) throws ActionException {
		List<FinalMatch> listFinalMatch;
		if (arg0.getFinalMatchType() == null) {
			listFinalMatch = finalMatchDao.getAll();
		} else {
			listFinalMatch = finalMatchDao.findByType(arg0.getFinalMatchType());
		}

		if (listFinalMatch == null) {
			return new ListFinalMatchResult(null);
		}
		
		return new ListFinalMatchResult(FinalMatch.createList(listFinalMatch));
	}

	@Override
	public void undo(ListFinalMatchAction arg0, ListFinalMatchResult arg1,
			ExecutionContext arg2) throws ActionException {
	}

}

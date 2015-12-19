/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.MatchDao;
import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchResult;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author alfonso
 *
 */
public class ListMatchHandler extends AbstractActionHandler<ListMatchAction, ListMatchResult> {
	private final Logger logger;
	private final MatchDao matchDao;
	private final UserDao userDao;
	
	@Inject
	ListMatchHandler(Logger logger, MatchDao matchDao, UserDao userDao) {
		super(ListMatchAction.class);
		this.logger = logger;
		this.matchDao = matchDao;
		this.userDao = userDao;
	}
	
	@Override
	public ListMatchResult execute(ListMatchAction arg0, ExecutionContext arg1)
			throws ActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo(ListMatchAction arg0, ListMatchResult arg1,
			ExecutionContext arg2) throws ActionException {
		// TODO Auto-generated method stub
		
	}

}

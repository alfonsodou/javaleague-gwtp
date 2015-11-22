/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.MatchDao;
import org.javahispano.javaleague.server.dao.RegisterMatchDao;
import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.shared.dispatch.match.RegisterMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.RegisterMatchResult;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author adou
 *
 */
public class RegisterMatchHandler extends
		AbstractActionHandler<RegisterMatchAction, RegisterMatchResult> {
	
	private final Logger logger;
	private final MatchDao matchDao;
	private final RegisterMatchDao registerMatchDao;

	@Inject
	RegisterMatchHandler(Logger logger, MatchDao matchDao, RegisterMatchDao registerMatchDao) {
		super(RegisterMatchAction.class);
		this.logger = logger;
		this.matchDao = matchDao;
		this.registerMatchDao = registerMatchDao;
	}
	
	@Override
	public RegisterMatchResult execute(RegisterMatchAction arg0,
			ExecutionContext arg1) throws ActionException {
		Match match = new Match();

		return null;
	}

	@Override
	public void undo(RegisterMatchAction arg0, RegisterMatchResult arg1,
			ExecutionContext arg2) throws ActionException {
	}

}

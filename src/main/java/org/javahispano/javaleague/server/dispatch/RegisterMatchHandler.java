/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.Date;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.MatchDao;
import org.javahispano.javaleague.server.dao.RegisterMatchDao;
import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.server.dao.domain.RegisterMatch;
import org.javahispano.javaleague.server.dao.domain.User;
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
	private final UserDao userDao;

	@Inject
	RegisterMatchHandler(Logger logger, MatchDao matchDao,
			RegisterMatchDao registerMatchDao, UserDao userDao) {
		super(RegisterMatchAction.class);
		this.logger = logger;
		this.matchDao = matchDao;
		this.registerMatchDao = registerMatchDao;
		this.userDao = userDao;
	}

	@Override
	public RegisterMatchResult execute(RegisterMatchAction arg0,
			ExecutionContext arg1) throws ActionException {
		RegisterMatch registerMatch = registerMatchDao.findByMatch();
		if (registerMatch != null) {
			Match match = new Match();
			match.setDate(new Date());
			match.setFriendly(true);
			User userAway = registerMatch.getUser();
			userAway.setAwaitingMatch(false);
			match.setUserAway(userAway);
			User userHome = userDao.get(arg0.getUserDto().getId());
			userHome.setAwaitingMatch(false);
			match.setUserHome(userHome);
			matchDao.put(match);
			userDao.put(userHome);
			userDao.put(userAway);
			registerMatch.setMatch(match);
			registerMatchDao.put(registerMatch);
			
			return new RegisterMatchResult(RegisterMatch.createDto(match));
		} else {
			registerMatch = new RegisterMatch();
			registerMatch.setDate(new Date());
			User user = userDao.get(arg0.getUserDto().getId());
			user.setAwaitingMatch(true);
			registerMatch.setUser(user);
			userDao.put(user);
			registerMatchDao.put(registerMatch);
			
			return new RegisterMatchResult(null);
		}
	}

	@Override
	public void undo(RegisterMatchAction arg0, RegisterMatchResult arg1,
			ExecutionContext arg2) throws ActionException {
	}

}

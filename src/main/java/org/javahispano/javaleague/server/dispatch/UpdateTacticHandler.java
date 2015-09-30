/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.server.authentication.CurrentUserDtoProvider;
import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticAction;
import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticResult;
import org.javahispano.javaleague.shared.dto.CurrentUserDto;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author alfonso
 *
 */
public class UpdateTacticHandler extends
		AbstractActionHandler<UpdateTacticAction, UpdateTacticResult> {
	private final Logger logger;
	private final UserDao userDao;
	private final CurrentUserDtoProvider currentUserDtoProvider;

	@Inject
	UpdateTacticHandler(Logger logger, UserDao userDao,
			CurrentUserDtoProvider currentUserDtoProvider) {
		super(UpdateTacticAction.class);

		this.logger = logger;
		this.userDao = userDao;
		this.currentUserDtoProvider = currentUserDtoProvider;
	}

	@Override
	public UpdateTacticResult execute(UpdateTacticAction action,
			ExecutionContext context) throws ActionException {
		User user = userDao.get(action.getId());
		if (user != null) {
			user.setTeamName(action.getTeamName());

			userDao.put(user);

			logger.info("UpdateTacticHandler: Actualizado nombre del equipo :: Id Usuario -> "
					+ user.getId()
					+ " :: Nombre Equipo -> "
					+ user.getTeamName());
		}

		return new UpdateTacticResult(User.createDto(user));
	}

	@Override
	public void undo(UpdateTacticAction action, UpdateTacticResult result,
			ExecutionContext context) throws ActionException {
	}

}

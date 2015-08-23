/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.logging.Logger;

import org.javahispano.javaleague.server.authentication.PasswordSecurity;
import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.server.utils.SessionIdentifierGenerator;
import org.javahispano.javaleague.shared.dispatch.register.RegisterAction;
import org.javahispano.javaleague.shared.dispatch.register.RegisterResult;
import org.javahispano.javaleague.shared.dto.UserDto;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author alfonso
 *
 */
public class RegisterHandler extends
		AbstractActionHandler<RegisterAction, RegisterResult> {
	private final Logger logger;
	private final UserDao userDao;
	private final PasswordSecurity passwordSecurity;

	@Inject
	RegisterHandler(Logger logger, UserDao userDao,
			PasswordSecurity passwordSecurity) {
		super(RegisterAction.class);

		this.logger = logger;
		this.userDao = userDao;
		this.passwordSecurity = passwordSecurity;

	}

	@Override
	public RegisterResult execute(RegisterAction action,
			ExecutionContext context) throws ActionException {
		User user = userDao.findByEmail(action.getEmail());

		if (user == null) {
			user = new User();
			SessionIdentifierGenerator userTokenGenerator = new SessionIdentifierGenerator();
			user.setUsername(action.getUserName());
			user.setHashPassword(passwordSecurity.hashPassword(action.getPassword()));
			user.setEmail(action.getEmail());
			user.setActive(false);
			user.setToken(userTokenGenerator.nextSessionId());
			userDao.put(user);

			return new RegisterResult(User.createDto(user), true);
		} else {
			return new RegisterResult(User.createDto(user), false);
		}
	}

	@Override
	public void undo(RegisterAction action, RegisterResult result,
			ExecutionContext context) throws ActionException {

	}

}

/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.javahispano.javaleague.server.authentication.PasswordSecurity;
import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.server.utils.ServletUtils;
import org.javahispano.javaleague.server.utils.SessionIdentifierGenerator;
import org.javahispano.javaleague.server.utils.VelocityHelper;
import org.javahispano.javaleague.shared.dispatch.register.RegisterAction;
import org.javahispano.javaleague.shared.dispatch.register.RegisterResult;

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
			user.setUserName(action.getUserName());
			user.setHashPassword(passwordSecurity.hashPassword(action
					.getPassword()));
			user.setEmail(action.getEmail());
			user.setActive(false);
			user.setToken(userTokenGenerator.nextSessionId());
			userDao.put(user);

			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("username", user.getUserName());
			velocityContext.put("url", ServletUtils.getBaseUrl()
					+ "authenticate?token=" + user.getToken() + "&email="
					+ user.getEmail());

			VelocityEngine ve = VelocityHelper.getVelocityEngine();

			// Finds template in WEB-INF/classes
			Template template = ve.getTemplate("emailTemplate_es.vm");

			StringWriter writer = new StringWriter();
			template.merge(velocityContext, writer);

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			Message msg = new MimeMessage(session);
			try {
				msg.setFrom(new InternetAddress("javaleague@gmail.com",
						"Administrador javaleague"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						user.getEmail(), user.getUserName()));
				msg.setSubject("Bienvenido a javaleague!");

				msg.setContent(writer.toString(), "text/html; charset=utf-8");
				msg.setSentDate(new Date());

				Transport.send(msg);
			} catch (UnsupportedEncodingException | MessagingException e) {
				logger.warning("RegisterHandler: Sending mail error :: " + e.getMessage());
				logger.warning(e.toString());
			}

			return new RegisterResult(User.createDto(user), true);
		} else {
			return new RegisterResult(null, false);
		}
	}

	@Override
	public void undo(RegisterAction action, RegisterResult result,
			ExecutionContext context) throws ActionException {
		User user = userDao.findByEmail(action.getEmail());

		if (user != null) {
			userDao.delete(user);
		}
	}

}

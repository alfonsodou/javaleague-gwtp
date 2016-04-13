/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author alfonso
 *
 */
public class SendEmailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(SendEmailServlet.class
			.getName());;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req);
	}

	private void processRequest(HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String email = req.getParameter("email");
			String username = req.getParameter("username");

			String url = "http://javaleague.appspot.com/authenticate?token="
					+ token + "&email=" + email;
			String mensaje = "Hola "
					+ username
					+ ". Gracias por registrarte en javaleague!. Pulsa en este enlace "
					+ url
					+ " para verificar tu cuenta de correo y activar tu usuario. Bienvenido!";
			logger.warning(token + " :: " + email + " :: " + username + " :: "
					+ url + " :: " + mensaje);

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("javaleague@gmail.com",
					"Administrador javaleague"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email, username));
			msg.setSubject("Bienvenido a javaleague!");

			Multipart mp = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(mensaje, "text/html");
			mp.addBodyPart(htmlPart);

			msg.setText(mensaje);
			// msg.setContent(mp);

			Transport.send(msg);
		} catch (Exception e) {
			logger.warning("SendEmailServlet: Sending mail error :: "
					+ e.getMessage());
			logger.warning(e.toString());
		}

	}
}

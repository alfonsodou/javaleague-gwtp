/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.urlshortener.model.Url;
import com.google.api.services.urlshortener.Urlshortener;
import com.google.api.services.urlshortener.UrlshortenerScopes;

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

			String url = "javaleague.appspot.com/authenticatetoken?token="
					+ token + "&email=" + email;
			
			Urlshortener shortener = newUrlshortener();
			Url toInsert = new Url().setLongUrl(url);		
			
			String mensaje = "<p>Hola "
					+ username
					+ ".</p><p>Gracias por registrarte en javaleague!. Pulsa en este "
					+ shortener.url().insert(toInsert).execute().getId()
					+ " para verificar tu cuenta de correo y activar tu usuario.</p><p>Bienvenido!</p>";
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
			msg.setText(mensaje);

			Transport.send(msg);

			logger.warning("SendEmailServlet: Ok!");
		} catch (Exception e) {
			logger.warning("SendEmailServlet: Sending mail error :: "
					+ e.getMessage());
			logger.warning(e.toString());
		}

	}
	
	  static Urlshortener newUrlshortener() {
		    AppIdentityCredential credential =
		        new AppIdentityCredential(Arrays.asList(UrlshortenerScopes.URLSHORTENER));
		    return new Urlshortener.Builder(new UrlFetchTransport(), new JacksonFactory(), credential)
		        .build();
		  }
}

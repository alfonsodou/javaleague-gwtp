/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * @author alfonso
 *
 */
public class FrameworkServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Logger logger;
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	@Inject
	FrameworkServlet(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		String urlString = req.getParameter("url");
		String name = req.getParameter("name");

		logger.warning("action: " + action + " :: url: " + urlString
				+ " :: name: " + name);

		if (action.equals("add")) {
			if (!urlString.isEmpty()) {
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setInstanceFollowRedirects(false);
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(10000);
				Integer code = connection.getResponseCode();
				if (code == HttpURLConnection.HTTP_OK) {
					InputStream is = connection.getInputStream();
					byte[] frameworkBytes = IOUtils.toByteArray(is);
					if (frameworkBytes != null) {
						GcsFilename fileName = new GcsFilename(
								"javaleague.appspot.com/framework", name);
						writeToFile(fileName, frameworkBytes);
					}
				}
			}
		}
	}

	private void writeToFile(GcsFilename fileName, byte[] content)
			throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName,
				GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}
}

/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import gwtupload.server.exceptions.UploadActionException;
import gwtupload.server.gae.AppEngineUploadAction;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.javahispano.javaleague.server.authentication.Authenticator;
import org.javahispano.javaleague.server.dao.UserSessionDao;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * @author adou
 *
 */
@Singleton
public class GWTUploadTacticServlet extends AppEngineUploadAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8341308719085191264L;
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());
	private final Authenticator authenticator;
	private final UserSessionDao loginCookieDao;
	private final Logger logger;

	@Inject
	GWTUploadTacticServlet(Logger logger, Authenticator authenticator,
			UserSessionDao loginCookieDao) {
		this.logger = logger;
		this.authenticator = authenticator;
		this.loginCookieDao = loginCookieDao;
	}

	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		String out = super.executeAction(request, sessionFiles);

		for (FileItem item : sessionFiles) {
			try {
				byte[] tacticBytes = IOUtils.toByteArray(item.getInputStream());
				if (tacticBytes != null) {
					Long userSessionKey = authenticator.getUserSessionKey();
					GcsFilename fileName = new GcsFilename(
							"javaleague.appspot.com/usuarios/" + userSessionKey,
							"tactica.jar");
					writeToFile(fileName, tacticBytes);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return out;
	}

	private void writeToFile(GcsFilename fileName, byte[] content)
			throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName,
				GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}
}

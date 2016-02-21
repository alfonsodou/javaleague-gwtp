/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.shared.parameters.UploadParameters;

import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * @author alfonso
 *
 */
public class ServeTeamImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	private final Logger logger;

	@Inject
	ServeTeamImageServlet(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long id = Long.parseLong(req.getParameter("id").replace("_", ""));
		GcsFilename fileName;

		if (req.getParameter("min").equals("OK")) {
			fileName = new GcsFilename(UploadParameters.getGCS_BUCKET()
					+ UploadParameters.getGCS_USERS() + id,
					UploadParameters.getFILENAMEIMAGEMIN());
		} else {
			fileName = new GcsFilename(UploadParameters.getGCS_BUCKET()
					+ UploadParameters.getGCS_USERS() + id,
					UploadParameters.getFILENAMEIMAGE());
		}

		resp.setHeader("ETag", Long.toString(id));// Establece header ETag
		resp.setHeader("Content-disposition",
				"inline; filename=" + Long.toString(id));
		resp.getOutputStream().write(readFile(fileName));
		resp.flushBuffer();
	}

	private byte[] readFile(GcsFilename fileName) {
		int fileSize;
		ByteBuffer result = null;

		try {
			fileSize = (int) gcsService.getMetadata(fileName).getLength();
			result = ByteBuffer.allocate(fileSize);

			GcsInputChannel readChannel = gcsService.openReadChannel(fileName,
					0);
			readChannel.read(result);

		} catch (Exception e) {
			logger.warning(e.toString());
		}

		return result.array();
	}

}

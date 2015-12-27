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

import org.javahispano.javaleague.server.dao.MatchDao;
import org.javahispano.javaleague.server.dao.domain.Match;
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
public class ServeMatchServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	private final MatchDao matchDao;
	private final Logger logger;

	@Inject
	ServeMatchServlet(Logger logger, MatchDao matchDao) {
		this.logger = logger;
		this.matchDao = matchDao;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long id = Long.parseLong(req.getParameter("id").replace("_", ""));
		Match p = matchDao.get(id);
		String pathName;

		if (p.isFriendly()) {
			pathName = UploadParameters.getGCS_BUCKET()
					+ UploadParameters.getGCS_MATCHS()
					+ UploadParameters.getGCS_FRIENDLY() + p.getId().toString();
		} else {
			pathName = UploadParameters.getGCS_BUCKET()
					+ UploadParameters.getGCS_MATCHS()
					+ UploadParameters.getGCS_LEAGUE() + p.getId().toString();
		}

		GcsFilename filename = new GcsFilename(pathName, p.getUserHome()
				.getTeamName() + "-" + p.getUserAway().getTeamName() + ".jvc");

		resp.setHeader("ETag", p.getId().toString());// Establece header ETag
		resp.setHeader("Content-disposition", "attachment; filename="
				+ p.getId().toString() + ".jvc");

		resp.getOutputStream().write(readFile(filename));
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

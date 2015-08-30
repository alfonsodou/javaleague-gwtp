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

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.javahispano.javaleague.server.dao.UserDao;

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
public class UploadTacticServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8164183204537334891L;
	private final Logger logger;
	private final UserDao userDao;
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	@Inject
	UploadTacticServlet(Logger logger, UserDao userDao) {
		this.logger = logger;
		this.userDao = userDao;
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iter;
		try {
			iter = upload.getItemIterator(req);

			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				if (item.isFormField()) {

				} else {
					if (!item.getName().isEmpty()) {
						byte[] tacticBytes = IOUtils.toByteArray(item
								.openStream());
						if (tacticBytes != null) {
							GcsFilename fileName = new GcsFilename(
									"javaleague.appspot.com", item.getName());
							writeToFile(fileName, tacticBytes);
						}
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

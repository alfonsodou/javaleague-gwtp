/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import gwtupload.server.exceptions.UploadActionException;
import gwtupload.server.gae.AppEngineUploadAction;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.javahispano.javaleague.javacup.shared.Agent;
import org.javahispano.javaleague.server.authentication.Authenticator;
import org.javahispano.javaleague.server.classloader.MyDataStoreClassLoader;
import org.javahispano.javaleague.server.dao.UserSessionDao;
import org.javahispano.javaleague.server.utils.ServletUtils;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
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
	private MyDataStoreClassLoader myDataStoreClassLoader;
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
					String result = validateTactic(tacticBytes, userSessionKey);
					if (result.equals("OK")) {
						out = "OK";
						GcsFilename fileName = new GcsFilename(
								"javaleague.appspot.com/usuarios/"
										+ userSessionKey, "tactica.jar");
						writeToFile(fileName, tacticBytes);
					} else {
						out = result;
						logger.warning("Exception: " + out);
					}
				}
			} catch (Throwable e) {
				if (e instanceof UploadActionException) {
					throw (UploadActionException) e;
				} else {
					out = ServletUtils.stackTraceToString(e);
					logger.warning("Exception: " + out);
					return out;
				}
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

	private byte[] readFile(GcsFilename fileName) {
		int fileSize;
		ByteBuffer result = null;

		logger.warning(fileName.toString());

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

	private String validateTactic(byte[] tactic, Long userSessionKey)
			throws Exception {
		String result = "OK";
		Map<String, byte[]> byteStream;

		myDataStoreClassLoader = new MyDataStoreClassLoader(this.getClass()
				.getClassLoader());

		// Cargamos el framework
		GcsFilename fileNameFramework = new GcsFilename(
				"javaleague.appspot.com/framework", "framework_20150901.jar");

		myDataStoreClassLoader
				.addClassJarFramework(readFile(fileNameFramework));

		Class<? extends Agent> cz = Class.forName(
				"org.javahispano.javacup.model.engine.AgentPartido", true,
				myDataStoreClassLoader).asSubclass(Agent.class);

		Agent a = cz.newInstance();

		result = loadClass(tactic, a, "org.javahispano.javaleague.tactic.ID_"
				+ userSessionKey);

		return result;
	}

	private String loadClass(byte[] tactic, Agent a, String packagePath)
			throws Exception {
		Class<?> cz = null;
		Class<?> result = null;
		Map<String, byte[]> byteStream;
		boolean errorPackageName, existInterfaceTactic;
		String errorValidate = "OK";
		Object objectTactic = null;

		byteStream = myDataStoreClassLoader.addClassJar(tactic);

		Iterator it = byteStream.entrySet().iterator();
		errorPackageName = false;
		while (it.hasNext() && !errorPackageName) {

			Map.Entry e = (Map.Entry) it.next();

			String name = new String((String) e.getKey());

			if (!name.contains(packagePath)) {
				errorPackageName = true;

				return "KO";
			} else {
				myDataStoreClassLoader.addClass(name, (byte[]) e.getValue());
			}

		}

		Iterator it1 = byteStream.entrySet().iterator();
		existInterfaceTactic = false;
		while (!errorPackageName && it1.hasNext() && !existInterfaceTactic) {
			Map.Entry e = (Map.Entry) it1.next();

			String name = new String((String) e.getKey());

			cz = myDataStoreClassLoader.loadClass(name);

			if (a.isTactic(cz)) {
				objectTactic = cz.newInstance();
				existInterfaceTactic = true;
				break;
			}

		}

		if (existInterfaceTactic == false) {
			return "KO";
		} else {
			a.testTactic(objectTactic, objectTactic, 3600);
		}

		return errorValidate;

	}
}

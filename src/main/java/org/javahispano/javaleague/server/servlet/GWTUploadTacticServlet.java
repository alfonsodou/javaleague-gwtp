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
import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.server.utils.ServletUtils;
import org.javahispano.javaleague.shared.parameters.UploadParameters;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
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
	private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	private MyDataStoreClassLoader myDataStoreClassLoader;
	private final Authenticator authenticator;
	private final UserDao userDao;
	private final Logger logger;

	@Inject
	GWTUploadTacticServlet(Logger logger, Authenticator authenticator, UserDao userDao) {
		this.logger = logger;
		this.authenticator = authenticator;
		this.userDao = userDao;
	}

	@Override
	public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
		String out = super.executeAction(request, sessionFiles);

		for (FileItem item : sessionFiles) {
			try {
				byte[] tacticBytes = IOUtils.toByteArray(item.getInputStream());
				if (tacticBytes != null) {
					Long userSessionKey = authenticator.getUserSessionKey();
					if (item.getName().substring(item.getName().lastIndexOf('.') + 1).equals("jar")) {

						String result = validateTactic(tacticBytes, userSessionKey);
						if (result.equals(UploadParameters.getVALIDATETACTICOK())) {
							GcsFilename fileName = new GcsFilename(
									UploadParameters.getGCS_BUCKET() + UploadParameters.getGCS_USERS() + userSessionKey,
									UploadParameters.getFILENAMETACTIC());
							writeToFile(fileName, tacticBytes);

							User user = userDao.get(userSessionKey);
							user.setTacticOK(true);
							userDao.put(user);
						} else {
							logger.warning("Exception: " + out);
						}
						out = result;
					} else {

						resizeImage(tacticBytes, 150, 150, userSessionKey, UploadParameters.getFILENAMEIMAGE());
						resizeImage(tacticBytes, 30, 30, userSessionKey, UploadParameters.getFILENAMEIMAGEMIN());

						User user = userDao.get(userSessionKey);
						user.setLogo(true);
						userDao.put(user);
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

	private void resizeImage(byte[] bytesImage, int width, int height, Long userSessionKey, String name)
			throws IOException {
		ImagesService imagesService = ImagesServiceFactory.getImagesService();

		Image oldImage = ImagesServiceFactory.makeImage(bytesImage);
		Transform resize = ImagesServiceFactory.makeResize(width, height);

		Image newImage = imagesService.applyTransform(resize, oldImage);

		GcsFilename fileName = new GcsFilename(
				UploadParameters.getGCS_BUCKET() + UploadParameters.getGCS_USERS() + userSessionKey, name);
		writeToFile(fileName, newImage.getImageData());
	}

	private void writeToFile(GcsFilename fileName, byte[] content) throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}

	private byte[] readFile(GcsFilename fileName) {
		int fileSize;
		ByteBuffer result = null;

		try {
			fileSize = (int) gcsService.getMetadata(fileName).getLength();
			result = ByteBuffer.allocate(fileSize);

			GcsInputChannel readChannel = gcsService.openReadChannel(fileName, 0);
			readChannel.read(result);

		} catch (Exception e) {
			logger.warning(e.toString());
		}

		return result.array();
	}

	private String validateTactic(byte[] tactic, Long userSessionKey) throws Exception {
		String result = UploadParameters.getVALIDATETACTICOK();

		myDataStoreClassLoader = new MyDataStoreClassLoader(this.getClass().getClassLoader());

		// Cargamos el framework
		GcsFilename fileNameFramework = new GcsFilename(
				UploadParameters.getGCS_BUCKET() + UploadParameters.getGCS_FRAMEWORK(),
				UploadParameters.getFILENAMEFRAMEWORK());

		myDataStoreClassLoader.addClassJarFramework(readFile(fileNameFramework));

		Class<? extends Agent> cz = Class.forName(UploadParameters.getAGENTCLASS(), true, myDataStoreClassLoader)
				.asSubclass(Agent.class);

		Agent a = cz.newInstance();

		result = loadClass(tactic, a, UploadParameters.getPACKAGENAME() + userSessionKey);

		return result;
	}

	private String loadClass(byte[] tactic, Agent a, String packagePath) throws Exception {
		Class<?> cz = null;
		Map<String, byte[]> byteStream;
		boolean errorPackageName, existInterfaceTactic;
		Object objectTactic = null;
		Object objectTacticSample = null;

		byteStream = myDataStoreClassLoader.addClassJar(tactic);

		Iterator it = byteStream.entrySet().iterator();
		errorPackageName = false;
		while (it.hasNext() && !errorPackageName) {

			Map.Entry e = (Map.Entry) it.next();

			String name = new String((String) e.getKey());

			if (!name.contains(packagePath)) {
				errorPackageName = true;

				return UploadParameters.getERRORPACKAGENAME();
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
			return UploadParameters.getERRORINTERFACETACTIC();
		} else {
			// Cargamos la táctica de ejemplo
			GcsFilename fileNameTacticSample = new GcsFilename(
					UploadParameters.getGCS_BUCKET() + UploadParameters.getGCS_FRAMEWORK(),
					UploadParameters.getFILENAMETACTICSAMPLE());

			byteStream = myDataStoreClassLoader.addClassJar(readFile(fileNameTacticSample));

			Iterator it3 = byteStream.entrySet().iterator();
			while (it3.hasNext()) {

				Map.Entry e = (Map.Entry) it3.next();

				String name = new String((String) e.getKey());

				myDataStoreClassLoader.addClass(name, (byte[]) e.getValue());
			}

			Iterator it2 = byteStream.entrySet().iterator();
			while (it2.hasNext()) {
				Map.Entry e = (Map.Entry) it2.next();

				String name = new String((String) e.getKey());

				cz = myDataStoreClassLoader.loadClass(name);

				if (a.isTactic(cz)) {
					objectTacticSample = cz.newInstance();
					break;
				}

			}

			a.testTactic(objectTactic, objectTacticSample, UploadParameters.getNUMITER());
		}

		return UploadParameters.getVALIDATETACTICOK();

	}
}

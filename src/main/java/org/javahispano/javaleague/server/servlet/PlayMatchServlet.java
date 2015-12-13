/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.javacup.shared.Agent;
import org.javahispano.javaleague.javacup.shared.MatchShared;
import org.javahispano.javaleague.server.classloader.MyDataStoreClassLoader;
import org.javahispano.javaleague.server.dao.MatchDao;
import org.javahispano.javaleague.server.dao.MatchPropertiesDao;
import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.server.dao.domain.MatchProperties;
import org.javahispano.javaleague.shared.parameters.MatchParameters;
import org.javahispano.javaleague.shared.parameters.UploadParameters;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * @author alfonso
 *
 */
@Singleton
public class PlayMatchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546988653656302663L;

	private final Logger logger;
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());
	private final UserDao userDao;
	private final MatchDao matchDao;
	private final MatchPropertiesDao matchPropertiesDao;
	private MyDataStoreClassLoader myDataStoreClassLoader;

	@Inject
	PlayMatchServlet(Logger logger, UserDao userDao, MatchDao matchDao,
			MatchPropertiesDao matchPropertiesDao) {
		this.logger = logger;
		this.userDao = userDao;
		this.matchDao = matchDao;
		this.matchPropertiesDao = matchPropertiesDao;
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Object lo = null;
		Object vo = null;
		Long matchID = Long.parseLong(req.getParameter("matchID").replace("_",
				""));
		Match match = matchDao.get(matchID);
		logger.info("/***** Comenzando ejecución partido " + matchID
				+ " *****/");
		if (match != null) {
			// Cargamos el framework
			GcsFilename fileName = new GcsFilename(
					UploadParameters.getGCS_BUCKET()
							+ UploadParameters.getGCS_FRAMEWORK(),
					UploadParameters.getFILENAMEFRAMEWORK());
			try {
				myDataStoreClassLoader = new MyDataStoreClassLoader(this
						.getClass().getClassLoader());
				myDataStoreClassLoader.addClassJarFramework(readFile(fileName));

				Class<? extends Agent> cz;

				cz = Class.forName(UploadParameters.getAGENTCLASS(), true,
						myDataStoreClassLoader).asSubclass(Agent.class);

				Agent a = cz.newInstance();

				// Cargamos las tácticas
				lo = loadClass(match.getUserHome().getId(), a);
				vo = loadClass(match.getUserAway().getId(), a);

				MatchShared matchShared = a.execute(lo, vo, Long.MAX_VALUE);
				logger.info("/***** Partido ejecutado " + matchID
						+ " *****/");

				if (match.isFriendly()) {
					logger.info("/***** Grabando partido .jvc " + matchID
							+ " *****/");

					fileName = new GcsFilename(UploadParameters.getGCS_BUCKET()
							+ UploadParameters.getGCS_MATCHS()
							+ UploadParameters.getGCS_FRIENDLY()
							+ match.getId(), match.getId().toString() + ".jvc");
					writeToFile(fileName, matchShared.getMatch());
					
					logger.info("/***** Grabando partido .bin " + matchID
							+ " *****/");

					fileName = new GcsFilename(UploadParameters.getGCS_BUCKET()
							+ UploadParameters.getGCS_MATCHS()
							+ UploadParameters.getGCS_FRIENDLY()
							+ match.getId(), match.getId().toString() + ".bin");
					writeToFile(fileName, matchShared.getMatchBin());
				} else {
					logger.info("/***** Grabando partido .jvc " + matchID
							+ " *****/");

					fileName = new GcsFilename(UploadParameters.getGCS_BUCKET()
							+ UploadParameters.getGCS_MATCHS()
							+ UploadParameters.getGCS_LEAGUE() + match.getId(),
							match.getId().toString() + ".jvc");
					writeToFile(fileName, matchShared.getMatch());
					
					logger.info("/***** Grabando partido .bin " + matchID
							+ " *****/");

					fileName = new GcsFilename(UploadParameters.getGCS_BUCKET()
							+ UploadParameters.getGCS_MATCHS()
							+ UploadParameters.getGCS_LEAGUE() + match.getId(),
							match.getId().toString() + ".bin");
					writeToFile(fileName, matchShared.getMatchBin());
				}
				match.setState(MatchParameters.getMATCHSTATE_FINISH());
				MatchProperties matchProperties = new MatchProperties();
				matchProperties.setGoalsAway(matchShared.getGoalsVisiting());
				matchProperties.setGoalsHome(matchShared.getGoalsLocal());
				matchProperties.setPosessionHome(matchShared
						.getPosessionLocal());
				matchProperties.setMatch(match);
				matchPropertiesDao.put(matchProperties);
				match.setProperties(matchProperties);
				matchDao.put(match);
			} catch (Exception e) {
				logger.warning(e.getMessage());
			}

		} else {
			logger.warning("/***** Partido " + matchID
					+ " no encontrado para ejecutar *****/");
		}
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

	private Object loadClass(Long userId, Agent a) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> cz = null;
		Class<?> result = null;
		Map<String, byte[]> byteStream;

		GcsFilename fileName = new GcsFilename(UploadParameters.getGCS_BUCKET()
				+ UploadParameters.getGCS_USERS() + userId,
				UploadParameters.getFILENAMETACTIC());

		byteStream = myDataStoreClassLoader.addClassJar(readFile(fileName));

		Iterator it = byteStream.entrySet().iterator();
		while (it.hasNext()) {

			try {
				Map.Entry e = (Map.Entry) it.next();

				String name = new String((String) e.getKey());

				myDataStoreClassLoader.addClass(name, (byte[]) e.getValue());

			} catch (Exception e) {
				logger.warning(e.toString());
			}
		}

		Iterator it1 = byteStream.entrySet().iterator();
		while (it1.hasNext()) {

			try {
				Map.Entry e = (Map.Entry) it1.next();

				String name = new String((String) e.getKey());

				cz = myDataStoreClassLoader.loadClass(name);

				if (a.isTactic(cz)) {
					result = cz;
				}
			} catch (Exception e) {
				logger.warning(e.toString());
			}
		}

		if (result != null)
			return result.newInstance();

		return null;
	}
}

/**
 * 
 */
package org.javahispano.javaleague.shared.parameters;

/**
 * @author adou
 *
 */
public class UploadParameters {
	private static String GCS_BUCKET = "javaleague.appspot.com";
	private static String GCS_USERS = "/usuarios/";
	private static String GCS_FRAMEWORK = "/framework/";
	private static String PACKAGENAME = "org.javahispano.javaleague.tactic.ID_";
	private static String AGENTCLASS = "org.javahispano.javacup.model.engine.AgentPartido";
	private static String FILENAMETACTIC = "tactica.jar";
	private static String FILENAMEFRAMEWORK = "framework_20150901.jar";
	private static int NUMITER = 600;
	private static String VALIDATETACTICOK = "OK";
	private static String VALIDATETACTICKO = "KO";
	private static String ERRORPACKAGENAME = "errorPackageName";
	private static String ERRORINTERFACETACTIC = "errorExistInterfaceTactic";
	
	public static String getGCS_BUCKET() {
		return GCS_BUCKET;
	}
	public static String getGCS_USERS() {
		return GCS_USERS;
	}
	public static String getGCS_FRAMEWORK() {
		return GCS_FRAMEWORK;
	}
	public static String getPACKAGENAME() {
		return PACKAGENAME;
	}
	public static String getAGENTCLASS() {
		return AGENTCLASS;
	}
	public static String getFILENAMETACTIC() {
		return FILENAMETACTIC;
	}
	public static String getFILENAMEFRAMEWORK() {
		return FILENAMEFRAMEWORK;
	}
	
	public static int getNUMITER() {
		return NUMITER;
	}
	public static String getVALIDATETACTICOK() {
		return VALIDATETACTICOK;
	}
	public static String getVALIDATETACTICKO() {
		return VALIDATETACTICKO;
	}
	public static String getERRORPACKAGENAME() {
		return ERRORPACKAGENAME;
	}
	public static String getERRORINTERFACETACTIC() {
		return ERRORINTERFACETACTIC;
	}
	
	
}

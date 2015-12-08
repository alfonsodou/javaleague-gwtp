/**
 * 
 */
package org.javahispano.javaleague.shared.parameters;

/**
 * @author alfonso
 *
 */
public class MatchParameters {
	private static Integer MATCHSTATE_WAIT = 0;
	private static Integer MATCHSTATE_QUEUE = 1;
	private static Integer MATCHSTATE_FINISH = 2;
	/**
	 * @return the mATCHSTATE_WAIT
	 */
	public static Integer getMATCHSTATE_WAIT() {
		return MATCHSTATE_WAIT;
	}
	/**
	 * @return the mATCHSTATE_QUEUE
	 */
	public static Integer getMATCHSTATE_QUEUE() {
		return MATCHSTATE_QUEUE;
	}
	/**
	 * @return the mATCHSTATE_FINISH
	 */
	public static Integer getMATCHSTATE_FINISH() {
		return MATCHSTATE_FINISH;
	}
	
	
}

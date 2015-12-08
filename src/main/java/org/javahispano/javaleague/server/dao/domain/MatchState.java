/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author alfonso
 *
 */
public enum MatchState implements IsSerializable {
	WAIT,
	QUEUE,
	FINISH
}

/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.match;

import org.javahispano.javaleague.server.dao.domain.FinalMatchType;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author alfonso
 *
 */
public class ListFinalMatchAction extends ActionImpl<ListFinalMatchResult> {
	private FinalMatchType finalMatchType;
	
	protected ListFinalMatchAction() {
		
	}
	
	public ListFinalMatchAction(FinalMatchType finalMatchType) {
		this.finalMatchType = finalMatchType;
	}
	
	/**
	 * @return the finalMatchType
	 */
	public FinalMatchType getFinalMatchType() {
		return finalMatchType;
	}

	/**
	 * @param finalMatchType the finalMatchType to set
	 */
	public void setFinalMatchType(FinalMatchType finalMatchType) {
		this.finalMatchType = finalMatchType;
	}

	@Override
	public boolean isSecured() {
		return false;
	}
}

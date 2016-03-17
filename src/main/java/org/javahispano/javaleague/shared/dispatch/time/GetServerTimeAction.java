/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.time;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author alfonso
 *
 */
public class GetServerTimeAction extends ActionImpl<GetServerTimeResult> {
	public GetServerTimeAction() {
		
	}
	
	@Override
	public boolean isSecured() {
		return false;
	}
}

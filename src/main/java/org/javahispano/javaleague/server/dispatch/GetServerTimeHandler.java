/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import java.util.Date;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.shared.dispatch.time.GetServerTimeAction;
import org.javahispano.javaleague.shared.dispatch.time.GetServerTimeResult;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author alfonso
 *
 */
public class GetServerTimeHandler extends
		AbstractActionHandler<GetServerTimeAction, GetServerTimeResult> {
	private final Logger logger;

	@Inject
	GetServerTimeHandler(Logger logger) {
		super(GetServerTimeAction.class);
		this.logger = logger;
	}

	@Override
	public GetServerTimeResult execute(GetServerTimeAction arg0,
			ExecutionContext arg1) throws ActionException {
		return new GetServerTimeResult(new Date());
	}

	@Override
	public void undo(GetServerTimeAction arg0, GetServerTimeResult arg1,
			ExecutionContext arg2) throws ActionException {
		// TODO Auto-generated method stub

	}

}

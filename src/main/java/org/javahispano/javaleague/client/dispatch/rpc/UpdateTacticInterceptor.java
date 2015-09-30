/**
 * 
 */
package org.javahispano.javaleague.client.dispatch.rpc;

import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticAction;
import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticResult;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.interceptor.ExecuteCommand;
import com.gwtplatform.dispatch.rpc.client.interceptor.AbstractRpcInterceptor;
import com.gwtplatform.dispatch.rpc.client.interceptor.UndoCommand;
import com.gwtplatform.dispatch.shared.DispatchRequest;

/**
 * @author alfonso
 *
 */
public class UpdateTacticInterceptor extends AbstractRpcInterceptor<UpdateTacticAction, UpdateTacticResult> {

	UpdateTacticInterceptor() {
		super(UpdateTacticAction.class);
	}

	@Override
	public DispatchRequest undo(UpdateTacticAction action,
			UpdateTacticResult result, AsyncCallback<Void> callback,
			UndoCommand<UpdateTacticAction, UpdateTacticResult> undoCommand) {
		
		return undoCommand.undo(action, result, callback);
	}

	@Override
	public DispatchRequest execute(
			UpdateTacticAction action,
			AsyncCallback<UpdateTacticResult> resultCallback,
			ExecuteCommand<UpdateTacticAction, UpdateTacticResult> executeCommand) {
		
		return executeCommand.execute(action, resultCallback);
	}

}

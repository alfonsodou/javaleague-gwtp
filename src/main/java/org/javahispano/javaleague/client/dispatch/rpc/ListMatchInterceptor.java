/**
 * 
 */
package org.javahispano.javaleague.client.dispatch.rpc;

import org.javahispano.javaleague.shared.dispatch.match.ListMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchResult;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.interceptor.ExecuteCommand;
import com.gwtplatform.dispatch.rpc.client.interceptor.AbstractRpcInterceptor;
import com.gwtplatform.dispatch.rpc.client.interceptor.UndoCommand;
import com.gwtplatform.dispatch.shared.DispatchRequest;

/**
 * @author alfonso
 *
 */
public class ListMatchInterceptor extends AbstractRpcInterceptor<ListMatchAction, ListMatchResult> {
	
	ListMatchInterceptor() {
		super(ListMatchAction.class);
	}

	@Override
	public DispatchRequest execute(ListMatchAction action,
			AsyncCallback<ListMatchResult> resultCallback,
			ExecuteCommand<ListMatchAction, ListMatchResult> executeCommand) {
		return executeCommand.execute(action, resultCallback);
	}

	@Override
	public DispatchRequest undo(ListMatchAction action,
			ListMatchResult result, AsyncCallback<Void> callback,
			UndoCommand<ListMatchAction, ListMatchResult> undoCommand) {
		return undoCommand.undo(action, result, callback);
	}
}

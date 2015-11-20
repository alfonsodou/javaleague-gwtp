package org.javahispano.javaleague.client.dispatch.rpc;

import org.javahispano.javaleague.shared.dispatch.match.RegisterMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.RegisterMatchResult;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.interceptor.ExecuteCommand;
import com.gwtplatform.dispatch.rpc.client.interceptor.AbstractRpcInterceptor;
import com.gwtplatform.dispatch.rpc.client.interceptor.UndoCommand;
import com.gwtplatform.dispatch.shared.DispatchRequest;


public class RegisterMatchInterceptor extends AbstractRpcInterceptor<RegisterMatchAction, RegisterMatchResult> {

	RegisterMatchInterceptor() {
		super(RegisterMatchAction.class);
	}

	@Override
	public DispatchRequest undo(RegisterMatchAction action,
			RegisterMatchResult result, AsyncCallback<Void> callback,
			UndoCommand<RegisterMatchAction, RegisterMatchResult> undoCommand) {
		return undoCommand.undo(action, result, callback);
	}

	@Override
	public DispatchRequest execute(
			RegisterMatchAction action,
			AsyncCallback<RegisterMatchResult> resultCallback,
			ExecuteCommand<RegisterMatchAction, RegisterMatchResult> executeCommand) {
		return executeCommand.execute(action, resultCallback);
	}

}

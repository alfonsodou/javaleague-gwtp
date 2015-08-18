/**
 * 
 */
package org.javahispano.javaleague.client.dispatch.rpc;

import org.javahispano.javaleague.shared.dispatch.register.RegisterAction;
import org.javahispano.javaleague.shared.dispatch.register.RegisterResult;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.interceptor.ExecuteCommand;
import com.gwtplatform.dispatch.rpc.client.interceptor.AbstractRpcInterceptor;
import com.gwtplatform.dispatch.rpc.client.interceptor.UndoCommand;
import com.gwtplatform.dispatch.shared.DispatchRequest;


/**
 * @author alfonso
 *
 */
public class RegisterInterceptor extends AbstractRpcInterceptor<RegisterAction, RegisterResult> {

	RegisterInterceptor() {
		super(RegisterAction.class);
		
	}

	@Override
	public DispatchRequest undo(RegisterAction action, RegisterResult result,
			AsyncCallback<Void> callback,
			UndoCommand<RegisterAction, RegisterResult> undoCommand) {
		
		return undoCommand.undo(action, result, callback);
	}

	@Override
	public DispatchRequest execute(RegisterAction action,
			AsyncCallback<RegisterResult> resultCallback,
			ExecuteCommand<RegisterAction, RegisterResult> executeCommand) {
		
		return executeCommand.execute(action, resultCallback);
	}

}

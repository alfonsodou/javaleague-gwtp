/**
 * 
 */
package org.javahispano.javaleague.server.dispatch;

import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticAction;
import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticResult;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;


/**
 * @author alfonso
 *
 */
public class UpdateTacticHandler extends AbstractActionHandler<UpdateTacticAction, UpdateTacticResult> {

	public UpdateTacticHandler(Class<UpdateTacticAction> actionType) {
		super(actionType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public UpdateTacticResult execute(UpdateTacticAction arg0,
			ExecutionContext arg1) throws ActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo(UpdateTacticAction arg0, UpdateTacticResult arg1,
			ExecutionContext arg2) throws ActionException {
		// TODO Auto-generated method stub
		
	}

}

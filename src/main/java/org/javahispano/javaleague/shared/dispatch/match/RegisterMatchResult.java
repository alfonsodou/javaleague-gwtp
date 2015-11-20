/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.match;

import org.javahispano.javaleague.shared.dto.MatchDto;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * @author alfonso
 *
 */
public class RegisterMatchResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5922239386956984550L;
	private MatchDto mathDto;

	protected RegisterMatchResult() {

	}

	public RegisterMatchResult(MatchDto matchDto) {
		this.mathDto = matchDto;
	}

	public MatchDto getMathDto() {
		return mathDto;
	}

	public void setMathDto(MatchDto mathDto) {
		this.mathDto = mathDto;
	}
	
	
}

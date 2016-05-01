/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.match;

import java.util.List;

import org.javahispano.javaleague.shared.dto.FinalMatchDto;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * @author alfonso
 *
 */
public class ListFinalMatchResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<FinalMatchDto> listFinalMatchDto;
	
	protected ListFinalMatchResult() {
		
	}
	
	public ListFinalMatchResult(List<FinalMatchDto> listFinalMatchDto) {
		this.listFinalMatchDto = listFinalMatchDto;
	}

	/**
	 * @return the listFinalMatchDto
	 */
	public List<FinalMatchDto> getListFinalMatchDto() {
		return listFinalMatchDto;
	}

	/**
	 * @param listFinalMatchDto the listFinalMatchDto to set
	 */
	public void setListFinalMatchDto(List<FinalMatchDto> listFinalMatchDto) {
		this.listFinalMatchDto = listFinalMatchDto;
	}

	
}

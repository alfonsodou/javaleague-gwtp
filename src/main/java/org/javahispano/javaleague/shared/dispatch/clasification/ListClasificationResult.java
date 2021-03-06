/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.clasification;

import java.util.List;

import org.javahispano.javaleague.shared.dto.ClasificationDto;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * @author alfonso
 *
 */
public class ListClasificationResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ClasificationDto> listClasification;
	private int round;
	
	protected ListClasificationResult() {
		
	}
	
	public ListClasificationResult(List<ClasificationDto> listClasification, int round) {
		this.listClasification = listClasification;
		this.round = round;
	}

	/**
	 * @return the listClasification
	 */
	public List<ClasificationDto> getListClasification() {
		return listClasification;
	}

	/**
	 * @param listClasification the listClasification to set
	 */
	public void setListClasification(List<ClasificationDto> listClasification) {
		this.listClasification = listClasification;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	
}

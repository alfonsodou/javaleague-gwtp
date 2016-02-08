/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.journey;

import java.util.List;

import org.javahispano.javaleague.shared.dto.JourneyDto;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * @author adou
 *
 */
public class ListJourneyResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4764447591208770269L;
	
	private List<JourneyDto> listJourneyDto;
	
	protected ListJourneyResult() {
		
	}
	
	public ListJourneyResult(List<JourneyDto> listJourneyDto) {
		this.listJourneyDto = listJourneyDto;
	}

	public List<JourneyDto> getListJourneyDto() {
		return listJourneyDto;
	}

	public void setListJourneyDto(List<JourneyDto> listJourneyDto) {
		this.listJourneyDto = listJourneyDto;
	}

	
}

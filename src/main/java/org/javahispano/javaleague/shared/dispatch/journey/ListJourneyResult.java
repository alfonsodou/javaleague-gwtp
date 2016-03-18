/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.journey;

import java.util.Date;
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
	private Date serverDate;
	
	protected ListJourneyResult() {
		
	}
	
	public ListJourneyResult(List<JourneyDto> listJourneyDto, Date serverDate) {
		this.listJourneyDto = listJourneyDto;
		this.serverDate = serverDate;
	}

	public List<JourneyDto> getListJourneyDto() {
		return listJourneyDto;
	}

	public void setListJourneyDto(List<JourneyDto> listJourneyDto) {
		this.listJourneyDto = listJourneyDto;
	}

	public Date getServerDate() {
		return serverDate;
	}

	public void setServerDate(Date serverDate) {
		this.serverDate = serverDate;
	}
}

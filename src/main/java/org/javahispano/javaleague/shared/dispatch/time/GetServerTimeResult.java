/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.time;

import java.util.Date;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * @author alfonso
 *
 */
public class GetServerTimeResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6183469151724355156L;
	
	private Date date;
	
	protected GetServerTimeResult() {
		
	}

	public GetServerTimeResult(Date date) {
		this.date = date;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
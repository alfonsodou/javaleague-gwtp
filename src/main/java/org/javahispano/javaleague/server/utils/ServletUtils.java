package org.javahispano.javaleague.server.utils;

/* 
 * Copyright (C) 2006 Methodhead Software LLC.  All rights reserved.
 * 
 * This file is part of TransferCM.
 * 
 * TransferCM is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * TransferCM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * TransferCM; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA  02110-1301  USA
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class ServletUtils {

	/**
	 * NOT UNIT TESTED Returns the URL (including query parameters) minus the
	 * scheme, host, and context path. This method probably be moved to a more
	 * general purpose class.
	 */
	public static String getRelativeUrl(HttpServletRequest request) {

		String baseUrl = null;

		if ((request.getServerPort() == 80) || (request.getServerPort() == 443))
			baseUrl = request.getScheme() + "://" + request.getServerName()
					+ request.getContextPath();
		else
			baseUrl = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath();

		StringBuffer buf = request.getRequestURL();

		if (request.getQueryString() != null) {
			buf.append("?");
			buf.append(request.getQueryString());
		}

		return buf.substring(baseUrl.length());
	}

	/**
	 * NOT UNIT TESTED Returns the base url (e.g,
	 * <tt>http://myhost:8080/myapp</tt>) suitable for using in a base tag or
	 * building reliable urls.
	 */
	public static String getBaseUrl(HttpServletRequest request) {
		if ((request.getServerPort() == 80) || (request.getServerPort() == 443))
			return request.getScheme() + "://" + request.getServerName()
					+ request.getContextPath();
		else
			return request.getScheme() + "://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath();
	}

	/**
	 * Returns the file specified by <tt>path</tt> as returned by
	 * <tt>ServletContext.getRealPath()</tt>.
	 */
	public static File getRealFile(HttpServletRequest request, String path) {

		return new File(request.getSession().getServletContext()
				.getRealPath(path));
	}

	public static String getBaseUrl() {
		String hostUrl;
		String environment = System
				.getProperty("com.google.appengine.runtime.environment");
		if (StringUtils.equals("Production", environment)) {
			String applicationId = System
					.getProperty("com.google.appengine.application.id");
			String version = System
					.getProperty("com.google.appengine.application.version");
			hostUrl = "http://" + version + "." + applicationId
					+ ".appspot.com/";
		} else {
			hostUrl = "http://localhost:8888";
		}

		return hostUrl;
	}

	public static String stackTraceToString(Throwable e) {
		String retValue = null;
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			retValue = sw.toString();
		} finally {
			try {
				if (pw != null)
					pw.close();
				if (sw != null)
					sw.close();
			} catch (IOException ignore) {
			}
		}
		return retValue;
	}

}

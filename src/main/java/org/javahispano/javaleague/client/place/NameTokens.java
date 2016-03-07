/*
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.javahispano.javaleague.client.place;

public class NameTokens {
    public static final String UNAUTHORIZED = "unauthorized";
    public static final String LOGIN = "login";
    public static final String MANUFACTURER = "manufacturer";
    public static final String DETAIL_MANUFACTURER = "detailManufacturer";
    public static final String RATING = "rating";
    public static final String DETAIL_RATING = "detailRating";
    public static final String CARS = "cars";
    public static final String REPORT = "report";
    public static final String NEW_CAR = "newCar";
    public static final String STATS = "stats";
    public static final String HOME = "home";
    public static final String TOURNAMENT = "tournament";
    public static final String TACTIC = "tactic";
    public static final String DOWNLOAD = "download";
    public static final String RULES = "rules";
    public static final String PROFILE = "profile";
    public static final String CONFIRM_REGISTER = "confirmRegister"; 
    public static final String DOCUMENTATION = "documentation";

    public static String getOnLoginDefaultPage() {
        return TACTIC;
    }

    public static String getManufacturer() {
        return MANUFACTURER;
    }

    public static String getDetailManufacturer() {
        return DETAIL_MANUFACTURER;
    }

    public static String getRating() {
        return RATING;
    }

    public static String getDetailRating() {
        return DETAIL_RATING;
    }

    public static String getCars() {
        return CARS;
    }

    public static String getReport() {
        return REPORT;
    }
    
    public static String getHome() {
    	return HOME;
    }
    
    public static String getTactic() {
    	return TACTIC;
    }
    
    public static String getTournament() {
    	return TOURNAMENT;
    }
    
    public static String getDownload() {
    	return DOWNLOAD;
    }
    
    public static String getRules() {
    	return RULES;
    }
    
    public static String getProfile() {
    	return PROFILE;
    }
    
    public static String getConfirmRegister() {
    	return CONFIRM_REGISTER;
    }

	public static String getDocumentation() {
		return DOCUMENTATION;
	}
    
}

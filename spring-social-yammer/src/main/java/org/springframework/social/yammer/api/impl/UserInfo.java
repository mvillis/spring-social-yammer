/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.yammer.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Morten Andersen-Gott
 *
 */
public class UserInfo {

	private String email;
	private String fullName;
	private String jobTitle;
	private String location;
	private String imProvider;
	private String imUsername;
	private String workTelephone;
	private String workExtension;
	private String mobile;
	private String externalProfiles;
	private String significantOther;
	private String kidsNames;
	private String interests;
	private String summary;
	private String expertise;
	private List<Education> education = new ArrayList<Education>();
	private List<Experience> experience = new ArrayList<Experience>();
	
	public UserInfo(String email, String fullName, String jobTitle, String location, String imProvider,
			String imUsername, String workTelephone, String workExtension, String mobile, String externalProfiles,
			String significantOther, String kidsNames, String interests, String summary, String expertise) {
		this.email = email;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
		this.location = location;
		this.imProvider = imProvider;
		this.imUsername = imUsername;
		this.workTelephone = workTelephone;
		this.workExtension = workExtension;
		this.mobile = mobile;
		this.externalProfiles = externalProfiles;
		this.significantOther = significantOther;
		this.kidsNames = kidsNames;
		this.interests = interests;
		this.summary = summary;
		this.expertise = expertise;
	}
	

	public MultiValueMap<String, String> toParams(){
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		putIfSet("email",email, params);
		putIfSet("full_name",fullName, params);
		putIfSet("job_title",jobTitle, params);
		putIfSet("location",location, params);
		putIfSet("im_provider",imProvider, params);
		putIfSet("im_username",imUsername, params);
		putIfSet("work_telephone",workTelephone, params);
		putIfSet("external_profiles",externalProfiles, params);
		putIfSet("work_extension",workExtension, params);
		putIfSet("mobile_telephone",mobile, params);
		putIfSet("significant_other",significantOther, params);
		putIfSet("kids_names",kidsNames, params);
		putIfSet("interests",interests, params);
		putIfSet("summary",summary, params);
		putIfSet("expertise",expertise, params);
		putEducation(params);
		putExperience(params);
		return params;
	}

	private void putExperience(MultiValueMap<String, String> params) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Experience exp : experience) {
			if(!first){
				sb.append("&previous_companies[]=");
			}
			first=false;			
			if(exp.company!=null){
				sb.append(exp.company);				
			}
			sb.append(",");
			if(exp.position!=null){
				sb.append(exp.position);				
			}
			sb.append(",");
			if(exp.description!=null){
				sb.append(exp.description);				
			}
			sb.append(",");
			if(exp.startYear!=0){
				sb.append(exp.startYear);				
			}
			sb.append(",");
			if(exp.endYear!=0){
				sb.append(exp.endYear);				
			}
			sb.append(")");
		}
		
	}

	private void putEducation(MultiValueMap<String, String> params) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Education ed : education) {
			if(!first){
				sb.append("&education[]");
			}
			first=false;			
			if(ed.school!=null){
				sb.append(ed.school);				
			}
			sb.append(",");
			if(ed.degree!=null){
				sb.append(ed.degree);				
			}
			sb.append(",");
			if(ed.description!=null){
				sb.append(ed.description);				
			}
			sb.append(",");
			if(ed.starYear!=0){
				sb.append(ed.starYear);				
			}
			sb.append(",");
			if(ed.endYear!=0){
				sb.append(ed.endYear);				
			}
		}
	}


	/**
	 * @param string
	 * @param email2
	 * @param params
	 */
	private void putIfSet(String key, String value, MultiValueMap<String, String> params) {
		if(value!=null){
			params.set(key, value);
		}
		
	}


	public void addExperience(String company, String position, String description, int startYear, int endYear){
		experience.add(new Experience(company, position, description, startYear, endYear));
	}
	
	public void addEducation(String school, String degree, String description, int starYear, int endYear){
		education.add(new Education(school, degree, description, starYear, endYear));
	}
	
	
	static class Experience{
		String company,position,description;
		int startYear,endYear;

		public Experience(String company, String position, String description, int start_year, int end_year) {
			this.company = company;
			this.position = position;
			this.description = description;
			this.startYear = start_year;
			this.endYear = end_year;
		}
		
		
	}
	static class Education{
		String school,degree,description;
		int starYear,endYear;

		public Education(String school, String degree, String description, int starYear, int endYear) {
			this.school = school;
			this.degree = degree;
			this.description = description;
			this.starYear = starYear;
			this.endYear = endYear;
		}
	}
	
}

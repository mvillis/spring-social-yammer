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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;

/**
 * @author Morten Andersen-Gott
 *
 */
public class SearchTemplateTest extends AbstractYammerApiTest {

	@Test
	public void testSearchWithSearchStringOnly(){
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		mockServer.expect(requestTo("https://www.yammer.com/api/v1/search.json?search=foo&page=1&number_per_page=20")).andExpect(method(GET))
				.andRespond(withResponse(new ClassPathResource("yammer-search-results.json", getClass()), responseHeaders));
		SearchResults searchResults = yammerTemplate.searchOperations().search("foo");
		assertSearchResult(searchResults);
	}
	@Test
	public void testSearchWithSearchStringAndPage(){
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		mockServer.expect(requestTo("https://www.yammer.com/api/v1/search.json?search=foo&page=2&number_per_page=20")).andExpect(method(GET))
		.andRespond(withResponse(new ClassPathResource("yammer-search-results.json", getClass()), responseHeaders));
		SearchResults searchResults = yammerTemplate.searchOperations().search("foo",2);
		assertSearchResult(searchResults);
	}
	@Test
	public void testSearchWithSearchStringPageAndNumPerPage(){
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		mockServer.expect(requestTo("https://www.yammer.com/api/v1/search.json?search=foo&page=2&number_per_page=10")).andExpect(method(GET))
		.andRespond(withResponse(new ClassPathResource("yammer-search-results.json", getClass()), responseHeaders));
		SearchResults searchResults = yammerTemplate.searchOperations().search("foo",2,10);
		assertSearchResult(searchResults);
	}

	private void assertSearchResult(SearchResults searchResults) {
		assertThat(searchResults, notNullValue());
		assertThat(searchResults.getMessages(), notNullValue());
		assertThat(searchResults.getMessages().getMetadata(), notNullValue());
		assertThat(searchResults.getMessages().getMessages(), notNullValue());
		assertThat(searchResults.getMessages().getMessages().size(), not(equalTo(0)));
		assertThat(searchResults.getGroups(), notNullValue());
		assertThat(searchResults.getGroups().size(), not(equalTo(0)));
		assertThat(searchResults.getGroups().get(0).getName(), equalTo("yammer-test-group") );
		assertThat(searchResults.getUsers(), notNullValue());
		assertThat(searchResults.getUsers().size(), not((equalTo(0))));
		assertThat(searchResults.getUsers().get(0).getName(), equalTo("mikealrogers-guest") );
	}
	
}

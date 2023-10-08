
package com.example.student.dto;

import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "FirstName", "LastName", "DOB", "Id" })
public class StudentDTO {

	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("Id")
	private int id;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("DOB")
	private String dob;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
	
	
public StudentDTO() {
	
}
	public StudentDTO(String firstName, int id, String lastName, String dob) {
		
		this.firstName = firstName;
		this.id = id;
		this.lastName = lastName;
		this.dob = dob;
	}

	@JsonProperty("FirstName")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("FirstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("LastName")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("LastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("DOB")
	public String getDob() {
		return dob;
	}

	@JsonProperty("DOB")
	public void setDob(String dob) {
		this.dob = dob;
	}

	@JsonProperty("Id")
	public int getId() {
		return id;
	}

	@JsonProperty("Id")
	public void setId(int id) {
		this.id = id;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
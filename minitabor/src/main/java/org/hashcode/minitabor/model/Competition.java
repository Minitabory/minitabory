package org.hashcode.minitabor.model;

import java.util.Date;
import java.util.UUID;

import org.hashcode.minitabor.util.Util;

/**
 * Model přihlášky
 * 
 * @author Zigi
 *
 */
public class Competition {
	private String uuid;
	private String firstName;
	private String lastName;
	private Date born;
	private String street;
	private String city;
	private String zip;
	private String email;
	private String mobile;
	private String troubles;

	public Competition() {
		this.uuid = UUID.randomUUID().toString();
	}

	public String getUuid() {
		return uuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBorn() {
		return born;
	}

	public void setBorn(Date born) {
		this.born = born;
	}

	public Date getCreated() {
		return new Date();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTroubles() {
		return troubles;
	}

	public void setTroubles(String troubles) {
		this.troubles = troubles;
	}

	public String getUniqueId() {
		StringBuilder sb = new StringBuilder();
		if (firstName != null)
			sb.append(firstName.toUpperCase() + "-");
		if (lastName != null)
			sb.append(lastName.toUpperCase() + "-");
		if (born != null)
			sb.append(Util.formatDate(born).toUpperCase() + "-");
		return sb.toString();
	}

}

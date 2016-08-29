package com.pucsd.cd.model;

import org.apache.commons.lang3.StringUtils;

import com.pucsd.cd.exception.InvalidContactException;

/**
 * Contact model class keep contact information. It provides static factory methods 
 * to get contact object for input contact in string format.   
 * 
 * @author dnyanesh
 *
 */
public class Contact {

	public static final char SPACE_CHARACTER = ' ';
	public static final char DOT_CHARACTER = '.';
	
	public static final int MAX_CONTACT_LENGTH = 50;
 	
	private String firstName; 
	private String lastName;
	
	
	/**
	 * Factory method to create new contact instance for input full contact or first name
	 * @param contact first name or full contact (first name and last name separated by space) 
	 * @return contact instance
	 * @throws InvalidContactException if validation is failed. 
	 */
	
	public static Contact newInstance(String contact) throws InvalidContactException {
		
		// contact must not be null or blank
		if (StringUtils.isBlank(contact)) {
			throw new InvalidContactException("Contact can't be null or blank");
		}
		// ignore contact's start and end spaces
		contact = contact.trim();
		
		// contact length must be less than 50 characters 
		if (contact.length() > MAX_CONTACT_LENGTH) {
			throw new InvalidContactException("Contact length must be " 
					+ MAX_CONTACT_LENGTH + " characters");
		}
		
		// contact can't contain more than one space character
		if (StringUtils.countMatches(contact, SPACE_CHARACTER) > 1) {
			throw new InvalidContactException(
					"Contact can't have more than one space character");
		}
		
		//  valid characters in contact are A-Z, a-z, 0-9, '.' and  ' '(space)
		for (int i = 0; i < contact.length(); i++) {
			char c = contact.charAt(i);
			if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') 
					|| (c >= '0' && c <= '9') || c == '.' || c == ' ')) {
			
				throw new InvalidContactException("Inavlid characters in contact."
						+ " Valid characters in contact are A-Z, a-z, 0-9, '.' and  ' '");
			}
		}
		// store all contacts in lower case to minimize space
		contact = StringUtils.lowerCase(contact);
		
		String[] temp = contact.split(" ");
		if (temp.length  == 2) {
			return new Contact(temp[0], temp[1]);
		} 
	
		return new Contact(contact);
	}
	
	/**
	 * Factory method to create new contact instance for input first name and/or last name.
	 * @param firstname contact's first name.
	 * @param lastName  contact's last name.
	 * @return  contact instance
	 * @throws InvalidContactException if validation is failed. 
	 */
	public static Contact newInstance(String firstName, String lastName) throws InvalidContactException {
		if (StringUtils.isBlank(firstName)) {
			throw new InvalidContactException("Contact's first name can't be null or blank");
		}
		
		firstName = firstName.trim();
		if (lastName != null) {
			lastName = lastName.trim();
		}
		
		// contact first name and last name can't contain space
		if (StringUtils.countMatches(firstName, SPACE_CHARACTER) > 0) {
			throw new InvalidContactException(
					"Space character in contact's first name is not allowed");
		}
		
		if (lastName != null) {
			if (StringUtils.countMatches(lastName, SPACE_CHARACTER) > 0) {
				throw new InvalidContactException(
						"Space character in contact's last name is not allowed");
			}
		}
	
		String fullContact = fullContact(firstName, lastName);
		return newInstance(fullContact);
	}
	

	private Contact(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	private Contact(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String fullContact() {
		return Contact.fullContact(firstName, lastName);
	}

	@Override
	public String toString() {
		return Contact.fullContact(firstName, lastName);
	}
	
	
	
	/**
	 * Static method get full contact in string format for input first name and last name.
	 * if last name is null, it return first name
	 * if last name is not null, it return full contact by combing first name and last name with space. 
	 * @param firstName first name of contact
	 * @param lastName last name of contact
	 * @return full contact in string format 
	 */
	
	private static String fullContact(String firstName, String lastName) {
		if (StringUtils.isBlank(lastName)) {
			return firstName;
		}
		return firstName + SPACE_CHARACTER + lastName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (! (obj instanceof Contact)) {
			return false;
		}
		
		Contact c = (Contact)obj;
		
		if (lastName != null) {
			return firstName.equals(c.firstName) && lastName.equals(c.lastName);
		} else if (lastName == null && c.lastName== null) {
			return firstName.equals(c.firstName);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hc = 17;
		hc = 31 * hc + firstName.hashCode();
		if (lastName != null) {
			hc = 31 * hc + lastName.hashCode();
		}
		return hc;
	}

}

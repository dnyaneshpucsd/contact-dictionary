package com.pucsd.cd.model;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.pucsd.cd.exception.InvalidContactException;

public class ContactTest {

	@Test(expected = InvalidContactException.class)
	public void testBlankContact1() throws InvalidContactException {
		Contact.newInstance("");
	}

	@Test(expected = InvalidContactException.class)
	public void testBlankContact2() throws InvalidContactException {
		Contact.newInstance("", "");
	}

	@Test(expected = InvalidContactException.class)
	public void testNullContact1() throws InvalidContactException {
		Contact.newInstance(null);
	}

	@Test(expected = InvalidContactException.class)
	public void testNullContact2() throws InvalidContactException {
		Contact.newInstance(null, null);
	}


	@Test(expected = InvalidContactException.class)
	public void testExceedMaxLengthContact1() throws InvalidContactException {
		// allowed contact length is 50 characters. test contact length is 51
		// characters
		String contact = "firstname lastnamelastnamelastnamelastnamelastnameX";
		Contact.newInstance(contact);
	}

	@Test(expected = InvalidContactException.class)
	public void testExceedMaxLengthContact2() throws InvalidContactException {
		// allowed contact length is 50 characters. test contact length is 51
		// characters
		
		Contact.newInstance("firstname", "lastnamelastnamelastnamelastnamelastnameX");
	}
	
	@Test
	public void testMaxLengthContact1() throws InvalidContactException {
		// allowed contact length is 50 characters.test contact length is 50
		// characters
		String cString = "firstname lastnamelastnamelastnamelastnamelastname";
		Contact contact = Contact.newInstance(cString);

		Assert.assertEquals(cString.toLowerCase(), contact.fullContact());
	}
	
	@Test
	public void testMaxLengthContact2() throws InvalidContactException {
		// allowed contact length is 50 characters.test contact length is 50
		// characters
		String firstName = "firstname";
		String lastName = "lastnamelastnamelastnamelastnamelastname";
		Contact contact = Contact.newInstance(firstName, lastName);
		Assert.assertEquals(fullContact(firstName, lastName), 
				contact.fullContact());
	}


	private String fullContact(String firstName, String lastName) {
		if (StringUtils.isBlank(lastName)) {
			return firstName.toLowerCase();
		}
		return (firstName + Contact.SPACE_CHARACTER + lastName).toLowerCase();
	}


	@Test
	public void testContactStartWithSpace1() throws InvalidContactException {
		String contact = " firstname";
		Contact c =  Contact.newInstance(contact);
		Assert.assertEquals(contact.trim().toLowerCase(), c.fullContact());
	}

	@Test
	
	public void testContactStartWithSpace2() throws InvalidContactException {
		String firstName = " firstname";
		String lastName = "lastname ";
		Contact contact = Contact.newInstance(firstName, lastName);
		Assert.assertEquals(fullContact(firstName.trim(), lastName.trim()), 
				contact.fullContact());
	
	}

	@Test
	public void testContactStartWithDot1() throws InvalidContactException {
		String contact = ".firstname";
		Contact c =  Contact.newInstance(contact);
		Assert.assertEquals(contact.toLowerCase(), c.fullContact());
	}

	@Test
	public void testContactStartWithDot2() throws InvalidContactException {
		String firstName = ".firstname";
		String lastName = "lastname";
		Contact contact = Contact.newInstance(firstName, lastName);
		Assert.assertEquals(fullContact(firstName, lastName), 
				contact.fullContact());
	
	}

	@Test
	public void testBlankLastNameContact() throws InvalidContactException {
		String firstName = ".firstname";
		String lastName = "";
		Contact contact = Contact.newInstance(firstName, lastName);
		Assert.assertEquals(fullContact(firstName, lastName), 
				contact.fullContact());
	
	}

	@Test
	public void testValidNumberOfSpaceCharactersInContact1()
			throws InvalidContactException {
		String cString = "firstname lastname";
		Contact contact = Contact.newInstance(cString);
		Assert.assertEquals(cString.toLowerCase(), contact.fullContact());

	}

	@Test
	public void testValidNumberOfSpaceCharactersInContact2()
			throws InvalidContactException {
		String firstName = "firstname";
		String lastName = "lastname";
		Contact contact = Contact.newInstance(firstName, lastName);
		Assert.assertEquals(fullContact(firstName, lastName), contact.fullContact());

	}

	@Test(expected = InvalidContactException.class)
	public void testExceedNumberOfSpaceCharactersInContact1()
			throws InvalidContactException {
		String cString = "firstname lastname lastname";
		Contact.newInstance(cString);
	}

	@Test(expected = InvalidContactException.class)
	public void testExceedNumberOfSpaceCharactersInContact2()
			throws InvalidContactException {
		String firstName = "firstname lastname";
		String lastName = "lastname";
		Contact.newInstance(firstName, lastName);
	}

	// Valid characters in contact are A-Z, a-z, 0-9, '.' and ' '
	@Test
	public void testValidContact() {
		// valid contact with no space
		String cString = "Firstname";
		Contact contact;
		try {
			contact = Contact.newInstance(cString);
			Assert.assertEquals(cString.toLowerCase(), contact.fullContact());
		} catch (InvalidContactException e) {
			Assert.assertTrue(
					"Excepted no exception but exception is thrown for contact "
							+ cString, false);
		}

		// valid contact separated by single space
		cString = "Firstname lastname";
		try {
			contact = Contact.newInstance(cString);
			Assert.assertEquals(cString.toLowerCase(), contact.fullContact());
		} catch (InvalidContactException e) {
			Assert.assertTrue(
					"Excepted no exception but exception is thrown for contact "
							+ cString, false);
		}

		// valid character in contacts A-Z
		for (char c = 'A'; c <= 'Z'; c++) {
			try {
				cString = "firstname lastname"+ CharUtils.toString(c);
				contact = Contact.newInstance(cString);
				Assert.assertEquals(cString.toLowerCase(), contact.fullContact());
			} catch (InvalidContactException e) {
				Assert.assertTrue(
						"Excepted no exception but exception is thrown for contact "
								+ cString, false);
			}
		}

		// valid character in contacts a- z
		for (char c = 'a'; c <= 'z'; c++) {
			try {
				cString = "firstname" + CharUtils.toString(c);
				contact = Contact.newInstance(cString);
				Assert.assertEquals(cString.toLowerCase(), contact.fullContact());
			} catch (InvalidContactException e) {
				Assert.assertTrue(
						"Excepted no exception but exception is thrown for contact "
								+ cString, false);
			}
		}

		// valid character in contacts a- z
		for (char c = '0'; c <= '9'; c++) {
			try {
				cString = "firstname lastname" + CharUtils.toString(c);
				contact = Contact.newInstance(cString);
				Assert.assertEquals(cString.toLowerCase(), contact.fullContact());
			} catch (InvalidContactException e) {
				Assert.assertTrue(
						"Excepted no exception but exception is thrown for contact "
								+ cString, false);
			}
		}
		// valid character .
		try {
			cString = "d.g hinganikar";
			contact = Contact.newInstance(cString);
			Assert.assertEquals(cString.toLowerCase(), contact.fullContact());
		} catch (InvalidContactException e) {
			Assert.assertTrue(
					"Excepted no exception but exception is thrown for contact "
							+ cString, false);
		}
	}
	
	// Valid characters in contact are A-Z, a-z, 0-9, '.' and ' '. 
	// It thrown InvalidContactException for all other characters in contact
	@Test
	public void testInvalidContact() {
		// invalid character string
		String invalidCharactersString = ",/?!~$%^&*()-_+'`::\"";
		for (int i = 0; i < invalidCharactersString.length(); i++) {
			try {
				Contact.newInstance("First" + invalidCharactersString.charAt(i)
						+ "name");
				Assert.assertTrue(
						"Excepted exception but no exception is thrown for character : "
								+ invalidCharactersString.charAt(i), false);
			} catch (InvalidContactException e) {
				Assert.assertTrue(true);
			}
		}
	}

}

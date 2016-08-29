package com.pucsd.cd.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.pucsd.cd.exception.InvalidContactException;
import com.pucsd.cd.model.Contact;
import com.pucsd.cd.service.impl.ContactDictionaryServiceImpl;

public class ContactDictionaryServiceTest {
	
	@Test
	public void testContactAdd() throws InvalidContactException {
		ContactDictionaryService contactDictionary = new ContactDictionaryServiceImpl();
		
		Contact c1 = Contact.newInstance(".");
		contactDictionary.add(c1);
		
		Contact c2 = Contact.newInstance(". .");
		contactDictionary.add(c2);
		
		Contact c3 = Contact.newInstance(".", "");
		contactDictionary.add(c3);
	
		Contact c4 = Contact.newInstance(".", "");
		contactDictionary.add(c4);
		
	
		Contact c5 = Contact.newInstance("a");
		contactDictionary.add(c5);
		
		Contact c6 = Contact.newInstance("a a");
		contactDictionary.add(c6);
		
		Contact c7 = Contact.newInstance("a", null);
		contactDictionary.add(c7);
		
		Contact c9 = Contact.newInstance("a", "a");
		contactDictionary.add(c9);
			
		Contact c10 = Contact.newInstance("1a");
		contactDictionary.add(c10);
		
		Contact c11 = Contact.newInstance("a1");
		contactDictionary.add(c11);
		
		Contact c12 = Contact.newInstance("a1", "a0");
		contactDictionary.add(c12);
		
		Contact c13 = Contact.newInstance("1a", "0a");
		contactDictionary.add(c13);
			
		
		Contact c14 = Contact.newInstance("dnyanesh");
		contactDictionary.add(c14);
		
		Contact c15 = Contact.newInstance("dnyanesh Pucsd");
		contactDictionary.add(c15);
		
		Contact c16 = Contact.newInstance("dnyandeep", "Pucsd");
		contactDictionary.add(c16);
		
		Contact c17 = Contact.newInstance("deepak", "Pucsd");
		contactDictionary.add(c17);
		
		Contact c18 = Contact.newInstance("deepesh", "Puc");
		contactDictionary.add(c18);
		
		Contact c19 = Contact.newInstance("dnyanesh.Pucsd");
		contactDictionary.add(c19);
		
		Contact c20 = Contact.newInstance("dnyanesh.Pucsd", "gmail");
		contactDictionary.add(c20);
		
		Contact c21 = Contact.newInstance("dnyanesh.Pucsd dnyanesh");
		contactDictionary.add(c21);
		
		Contact c22 = Contact.newInstance("dnyanesh.Pucsd dnyanes");
		contactDictionary.add(c22);
		
		Contact c23 = Contact.newInstance("dnyanesh.Pucsd dny");
		contactDictionary.add(c23);
		
		Contact c24 = Contact.newInstance("dnyanesh.Pucsd dn");
		contactDictionary.add(c24);
		
		Contact c25 = Contact.newInstance("dnyanesh.Pucsd d");
		contactDictionary.add(c25);
					
	
		Contact c26 = Contact.newInstance("dnyanesh.Pucsd dnyanesh.Pucsd");
		contactDictionary.add(c26);
		
		Contact c27 = Contact.newInstance("dnyanesh dnyanesh.Pucsd");
		contactDictionary.add(c27);
		
		Contact c28 = Contact.newInstance("dnyanes dnyanesh.Pucsd");
		contactDictionary.add(c28);
		
		Contact c29 = Contact.newInstance("dny dnyanesh.Pucsd");
		contactDictionary.add(c29);
		
		Contact c30 = Contact.newInstance("dn dnyanesh.Pucsd");
		contactDictionary.add(c30);
		
		Contact c31 = Contact.newInstance("d dnyanesh.Pucsd");
		contactDictionary.add(c31);
		
		Contact c32 = Contact.newInstance("durgesh nitcalicut");
		contactDictionary.add(c32);
		
		Contact c33 = Contact.newInstance("nit", "mangesh");
		contactDictionary.add(c33);
		
		Contact c34 = Contact.newInstance("nitcalicut deepak");
		contactDictionary.add(c34);
		
		Contact c35 = Contact.newInstance("nit", "sachin");
		contactDictionary.add(c35);
		
		Contact c36 = Contact.newInstance("nitcalicut", "nitsurathkal");
		contactDictionary.add(c36);
		
		Contact c37 = Contact.newInstance("nitsurat nitsurathkal");
		contactDictionary.add(c37);
		
		Contact c38 = Contact.newInstance("mangesh pune");
		contactDictionary.add(c38);
		
		Contact c39 = Contact.newInstance("durgesh", "pune");
		contactDictionary.add(c39);
		
		Contact c40 = Contact.newInstance("pune", "mangesh");
		contactDictionary.add(c40);
		
		Contact c41 = Contact.newInstance("punemumbaidelhikolkatahyderabadbangalore", "jaipur");
		contactDictionary.add(c41);
		
		Contact c42 = Contact.newInstance("mumbaidelhikolkatahyderabadbangalore", "jaipur");
		contactDictionary.add(c42);
	
		Contact c43 = Contact.newInstance("1234567890", "jaipur");
		contactDictionary.add(c43);
		
		Contact c44 = Contact.newInstance("jaipur", "1234567890");
		contactDictionary.add(c44);

		Contact c45 = Contact.newInstance("0123", "jaipur");
		contactDictionary.add(c45);
		
		Contact c46 = Contact.newInstance("jaipur", "345");
		contactDictionary.add(c46);

	}

	// empty dictionary search
	@Test
	public void testEmptyDictionarySearch() throws InvalidContactException {
		ContactDictionaryService contactDictionary = new ContactDictionaryServiceImpl();
		
		List<Contact> searchList = contactDictionary.search("dnyanesh");
		
		Assert.assertTrue(searchList.size() == 0);
	}
	
	@Test
	public void testSingleEntryDictionarySearch1() throws InvalidContactException {
		ContactDictionaryService contactDictionary = new ContactDictionaryServiceImpl();

		Contact c1 = Contact.newInstance(".");
		contactDictionary.add(c1);
		List<Contact> searchList = contactDictionary.search(".");
		
		Assert.assertTrue(searchList.size() == 1);
		Assert.assertEquals(searchList.get(0), c1);
	}

	@Test
	public void testSingleEntryDictionarySearch2() throws InvalidContactException {
		ContactDictionaryService contactDictionary = new ContactDictionaryServiceImpl();
		
		Contact c1 = Contact.newInstance(".", ".");
		contactDictionary.add(c1);
		List<Contact> searchList = contactDictionary.search(".");
		
		Assert.assertTrue(searchList.size() == 1);
		Assert.assertEquals(searchList.get(0), c1);
	}
	
	@Test
	public void testPrefixOverlapSearch1() throws InvalidContactException {
		ContactDictionaryService contactDictionary = new ContactDictionaryServiceImpl();
		
		Contact c3 = Contact.newInstance("b aa");
		contactDictionary.add(c3);
		
		Contact c4 = Contact.newInstance("b a");
		contactDictionary.add(c4);
		
		Contact c5 = Contact.newInstance("y");
		contactDictionary.add(c5);
		
		Contact c6 = Contact.newInstance("y a");
		contactDictionary.add(c6);
		
		Contact c7 = Contact.newInstance("a", "aa");
		contactDictionary.add(c7);
		
		Contact c9 = Contact.newInstance("aa", "aaa");
		contactDictionary.add(c9);
			
		Contact c10 = Contact.newInstance("1a");
		contactDictionary.add(c10);
		
		Contact c11 = Contact.newInstance("1aaa");
		contactDictionary.add(c11);
		
		Contact c12 = Contact.newInstance("1aaa", "0");
		contactDictionary.add(c12);
		
		Contact c13 = Contact.newInstance("1aa", "0a");
		contactDictionary.add(c13);
		
		Contact c14 = Contact.newInstance("1", "0aa");
		contactDictionary.add(c14);
		
		
		List<Contact> searchList = contactDictionary.search("a");
		
		Assert.assertTrue(searchList.size() == 5);
		// first result "aaa" (c7) full name exact match	
		Assert.assertEquals(c7, searchList.get(0));
	
		// second result "ba" (c4) last name exact match + first name sorting	
		Assert.assertEquals(c4, searchList.get(1));
	
		// third result "ya" (c6) last name exact match + first name sorting	
		Assert.assertEquals(c6, searchList.get(2));
	
		// fourth result "aaaaa" (c9) first name prefix match
		Assert.assertEquals(c9, searchList.get(3));
		
		// fifth result "baa" (c3) last name prefix match
		Assert.assertEquals(c3, searchList.get(4));
	}
	
	@Test
	public void testPrefixOverlapSearch2() throws InvalidContactException {
		ContactDictionaryService contactDictionary = new ContactDictionaryServiceImpl();
		Contact c14 = Contact.newInstance("dnyanesh");
		contactDictionary.add(c14);
		Contact c15 = Contact.newInstance("dnyanesh Pucsd");
		contactDictionary.add(c15);
		Contact c16 = Contact.newInstance("dnyandeep", "Pucsd");
		contactDictionary.add(c16);
		Contact c17 = Contact.newInstance("deepak", "Pucsd");
		contactDictionary.add(c17);
		Contact c18 = Contact.newInstance("deepesh", "Puc");
		contactDictionary.add(c18);
	
		Contact c19 = Contact.newInstance("dnyanesh.Pucsd");
		contactDictionary.add(c19);
		Contact c20 = Contact.newInstance("dnyanesh.Pucsd", "gmail");
		contactDictionary.add(c20);
		Contact c21 = Contact.newInstance("dnyanesh.Pucsd dnyanesh");
		contactDictionary.add(c21);
		Contact c22 = Contact.newInstance("dnyanesh.Pucsd dnyanes");
		contactDictionary.add(c22);
		Contact c23 = Contact.newInstance("dnyanesh.Pucsd dny");
		contactDictionary.add(c23);
		Contact c24 = Contact.newInstance("dnyanesh.Pucsd dn");
		contactDictionary.add(c24);
		Contact c25 = Contact.newInstance("dnyanesh.Pucsd d");
		contactDictionary.add(c25);
					
	
		Contact c26 = Contact.newInstance("dnyanesh.Pucsd daa.Pucsd");
		contactDictionary.add(c26);
		Contact c27 = Contact.newInstance("dnyanesh dnyanesh.Pucsd");
		contactDictionary.add(c27);
		Contact c28 = Contact.newInstance("dnyanes dnyanesh.Pucsd");
		contactDictionary.add(c28);
		Contact c29 = Contact.newInstance("dny dnyanesh.Pucsd");
		contactDictionary.add(c29);
		Contact c30 = Contact.newInstance("dn dnyanesh.Pucsd");
		contactDictionary.add(c30);
		Contact c31 = Contact.newInstance("d dnyanesh.Pucsd");
		contactDictionary.add(c31);
		
		// search by last name
		List<Contact> searchList = contactDictionary.search("gmail");
		Assert.assertTrue(searchList.size() == 1);
		Assert.assertEquals(c20, searchList.get(0));
		
		// search by first name
		searchList = contactDictionary.search("deepak");
		Assert.assertTrue(searchList.size() == 1);
		Assert.assertEquals(c17, searchList.get(0));
		
		// search by full name
		searchList = contactDictionary.search("dnyanesh.pucsd gmail");
		Assert.assertTrue(searchList.size() == 1);
		Assert.assertEquals(c20, searchList.get(0));

		// search for prefix
		searchList = contactDictionary.search("d");
		
		Assert.assertTrue(searchList.size() == 18);
		// first result "d dnyanesh.Pucsd" (c31) first name  exact match	
		Assert.assertEquals(c31, searchList.get(0));
	
		// second result "dnyanesh.Pucsd d" (c25) last name exact match 	
		Assert.assertEquals(c25, searchList.get(1));
	
		// third result "deepak Pucsd" (c17) first name prefix match + sorting	
		Assert.assertEquals(c17, searchList.get(2));
	
		// fourth result "deepesh Puc" (c18) first name prefix match + sorting
		Assert.assertEquals(c18, searchList.get(3));

		// fifth result "dn dnyanesh.Pucsd" (c30) first name prefix match + sorting
		Assert.assertEquals(c30, searchList.get(4));


		searchList = contactDictionary.search("dnyanesh.");
		
		Assert.assertTrue(searchList.size() == 13);
		// first result "dnyanesh.Pucsd" (c19) first name  exact match	
		Assert.assertEquals(c19, searchList.get(0));
	
		// second result "dnyanesh.Pucsd d" (c25) last name exact match 	
		Assert.assertEquals(c25, searchList.get(1));
	
		// third result "dnyanesh.Pucsd daa.Pucsd" (c26) first name prefix match + sorting	
		Assert.assertEquals(c26, searchList.get(2));
	
		// third result "dnyanesh.Pucsd dn" (c24) first name prefix match + sorting	
		Assert.assertEquals(c24, searchList.get(3));

		// third result "dnyanesh.Pucsd dny" (c23) first name prefix match + sorting	
		Assert.assertEquals(c23, searchList.get(4));
	}
}

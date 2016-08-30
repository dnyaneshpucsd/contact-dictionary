package com.pucsd.cd.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pucsd.cd.bean.NameFrequency;
import com.pucsd.cd.exception.InvalidContactException;
import com.pucsd.cd.model.Contact;
import com.pucsd.cd.service.ContactDictionaryService;

/**
 *  Implementation of  <code> ContactDictionaryService </code>.
 *<p>
 * Implemented API:
 * <ul>
 *  <li> <strong>add </strong> API add each contact in dictionary two times 
 *    one for full contact (first name + space + last name) and 
 *    one for last name if last name is present. 
 *    full contact entry is used to search by by first name or full contact and 
 *    last name entry is used to search by last name.
 *    it doesn't perform any duplicate check. 
 * <br>
 * 
 *  	
 *  <li> <strong>search </strong> API implemented algorithm <br>
 *
 * traverse dictionary tree for each character in searchText. 
 *  if node is not found for current character, 
 *    return empty list.
 * if node is found for current character and character is not last character in search text.
 *    recursive call with found node as dictionary and incremented char position.
 * if character is last character in search text 
 * 	  get reference node, 
 *    build first name / full name and last name exact match result,
 *    build full name and last name prefix match result by traversing whole dictionary subtree rooted at node.
 *    prefix match result sorted by full contact are appended to exact match result
 * </p>
 *   
 *  dictionary entries for same contact
 * @author dnyanesh
 *
 */
public class ContactDictionaryServiceImpl implements ContactDictionaryService {
	
	/**
	 * Contact dictionary tree data structure. Used different variables for different group of
	 * characters to minimize memory usage. 
	 * @author dnyanesh
	 */
	
	private class ContactDictionaryNode {
		
		// keep reference of contact dictionary nodes for characters from a to z.
		ContactDictionaryNode[] childrenAZ;
	
		// keep reference of contact dictionary nodes for characters from 0 to 9.
		ContactDictionaryNode[] children09;
		// keep reference of contact dictionary node for character '.'
		ContactDictionaryNode childDot;
		// keep reference of contact dictionary node for character ' '
		ContactDictionaryNode childSpace;
				
		//Map<Character, ContactDictionaryNode> children;
		
		// keep occurrence frequency of same contact (full name) in dictionary. 
		// frequency value is set in dictionary node for last character of contact(full name)
		int frequency;
		
		// keep list of first names which are mapped to last name.
		// first name list is set in dictionary node for last character of last name.
		List<NameFrequency> firstNameList;
		
		/**
		 * Method to get object of <code>ContactDictionaryNode</code> referred by input character.
		 * @param c character for which mapped object of <code>ContactDictionaryNode </code> is requested.
		 * @return object of <code>ContactDictionaryNode</code> if object is present.
		 *		   null if object is not set yet.
		 */			
		ContactDictionaryNode get(char c) {
			if (c >= 'a' && c <= 'z' && childrenAZ != null) {
				return childrenAZ[c-'a'];
			}
			
			if (c >= '0' && c <= '9' && children09 != null) {
					return children09[c-'0'];
			}
			
			if (c == '.') {
				return childDot;
			}
			
			if (c == ' ') {
				return childSpace;
			}
	
			return null;
		}
		
		/**
		 * Method to get or create object of <code>ContactDictionaryNode</code> referred by input character.
		 * if object is missing, it create and set node reference to input character and return it.  
		 * @param c character for which mapped object of <code>ContactDictionaryNode </code> is requested.
		 * @return object of <code>ContactDictionaryNode</code> referred by input character.
		 */
		ContactDictionaryNode getOrCreate(char c) {
			if (c >= 'a' && c <= 'z') {
				if (childrenAZ == null) {
					childrenAZ = new ContactDictionaryNode['z' - 'a' + 1];
				}
				if (childrenAZ[c-'a'] == null) {
					childrenAZ[c-'a'] = new ContactDictionaryNode();
				} 
				return childrenAZ[c-'a'];
			}
			
			if (c >= '0' && c <= '9') {
				if (children09 == null) {
					children09 = new ContactDictionaryNode['9' - '0' + 1];
				}
				if (children09[c-'0'] == null) 	{
					children09[c-'0'] = new ContactDictionaryNode();
				} 
				return children09[c-'0'];
			}
			
			if (c == '.') {
				if (childDot == null) {
					childDot = new ContactDictionaryNode();
				}
				return childDot;
			}
		
			if (c == ' ') {
				if (childSpace == null) {
					childSpace = new ContactDictionaryNode();
				}
				return childSpace;
			}
			
			return null;
		}
		
		/**
		 * Provide list of valid keys which are used to traverse children tree in dictionary.
		 * @return valid keys list
		 */
		public List<Character> validKeyCharacters() {
			List<Character> characterList = new ArrayList<Character>();
			
			characterList.add(' ');
			characterList.add('.');
			
			for (char c = 'a' ; c <= 'z'; c++) {
				characterList.add(c);
			}
			
			for (char c = '0' ; c <= '9'; c++) {
				characterList.add(c);
			}
			
			return characterList;
		}
	}
	
	// root of contact dictionary tree
	private ContactDictionaryNode contactDictionary;
	
	public ContactDictionaryServiceImpl() {
		contactDictionary = 
				new ContactDictionaryNode();
	}
	/**
	 * Add API implementation. 
	 */
	public void add(Contact contact) throws InvalidContactException {
		String fullContact = contact.fullContact();
	
		String lastName = contact.getLastName(); 
		String firstName = contact.getFirstName();
		
		// add contact in dictionary by full name to search by first or full name
		add(fullContact, 0, contactDictionary, null);
		
		if (lastName != null) {
			// add contact in dictionary by last name to search by last name
			add(lastName, 0, contactDictionary, firstName);
		}
	}
	
	/**
	 * Method to add contact in dictionary. each contact is added in dictionary two times 
	 * one for full contact (first name + space + last name) and one for last name if last name is present. 
	 * full contact entry is used to search by by first name or full contact and last name entry is used to
	 * search by last name. 
	 * <br>
	 * 
	 * add operation doesn't perform any duplicate check.
	 * <br>
	 * 
	 * <br>
	 * case 1 : algorithm to add contact in dictionary for full contact (first name + space + last name)
	 * traverse dictionary tree for each character in full name. 
	 * if node is missing for a character, add new node in dictionary tree and link to character.  
	 * if character is last character of full name, increment frequency of node by 1 to denote end of contact.
	 * frequency denotes number of same contact in dictionary.
	 * 
	 * case 2 : algorithm to add contact in dictionary for last name
	 * traverse dictionary tree for each character in last name. 
	 * if node is missing for a character, add new node in dictionary tree and link to character
	 * if character is last character of last name, create first name list mapped to last name in sorting order.
	 * first name list keep track of first names mapped to last name.  
	 * 			
	 * 
	 * @param name  full contact (first name + space + last name) or last name of contact
	 * @param i index of current character in name.
	 * @param contactDictionary contact dictionary tree
	 * @param firstName contact's first name, expected null for full contact request and not be null for last name request 
	 * @throws InvalidContactException if not supported character is found in contact
	 */

	private void add(String name, int i,
			ContactDictionaryNode contactDictionary,
			String firstName) throws InvalidContactException {
		
		if (i < name.length()) {
			char c = name.charAt(i);
			ContactDictionaryNode node = contactDictionary.getOrCreate(c);
			
			if (node == null) {
				throw new InvalidContactException("Invalid contact. character " + c + " is contact is not supported");
			}
			if (i == name.length() - 1) {
				
				if (firstName == null) {
					// case to add full contact in dictionary tree
					node.frequency = 1 + node.frequency; 
				} else {
					updateFirstNameList(node, firstName);
				}
			} else {
				// recursive call to add with next character
				add(name, i+1, node, firstName);
			}
		}
	}
	
	/**
	 * Method to update input node's first name list with input first name.
	 * while updating list, it make sure list is in sorted order before and after operation.
	 * @param node which first name list has to be updated .
	 * @param firstName with which first name list has to be updated.
	 */
	private void updateFirstNameList(ContactDictionaryNode node,
			String firstName) {
		
		List<NameFrequency> firstNameList = node.firstNameList;
		// if firstNameList is null, initialize first name list and
		// add input first name(NameFrequency) in firstNameList.
		if (firstNameList == null) {
			
			firstNameList = new LinkedList<NameFrequency>();
			node.firstNameList = firstNameList;
			
			// add input first name in firstNameList
			NameFrequency firstNameFrequeny = new NameFrequency(firstName);
			firstNameList.add(firstNameFrequeny);
			return;
			
		}
		
		int index = 0, size = firstNameList.size() ;
	
		while (index < size) {
			NameFrequency nameFrequency = firstNameList.get(index);
			// if input first name is equal to first name of list, increase frequency count by 1.
			if (nameFrequency.getName().equals(firstName)) {
				nameFrequency.setFrequency(nameFrequency.getFrequency() + 1);
				return;
			} 
				
			// if first name of list is greater than input first name
			// add first name in list before current first name of list 
			// to maintain lexicographic ordering of list
			if (nameFrequency.getName().compareTo(firstName) > 0) {
				nameFrequency = new NameFrequency(firstName);
				firstNameList.add(index, nameFrequency);
				return;
			}
				
			index++;
		}
		// add as a last element
		if (index == size) {
			NameFrequency firstNameFrequeny = new NameFrequency(firstName);
			firstNameList.add(firstNameFrequeny);
		}
	}
	
	/**
	 * Search API implementation. 
	 */
	public List<Contact> search(String searchText) {
		List<Contact> resultList = new ArrayList<Contact>();
		// return empty list for blank or empty searchText
		if (StringUtils.isBlank(searchText)) {
			return resultList;
		}
		// trim all space characters
		searchText = searchText.trim();
		// search text must be in lower case as contact dictionary keep all contacts in lower case.
		searchText = searchText.toLowerCase();
		
		resultList = search(searchText.toLowerCase(), 0, contactDictionary);
		
		return resultList;
	}

	/**
	 * Search method to perform contact search by full name as well as last name in contact dictionary. 
	 * The algorithm used to implement search feature is
	 * <br>
	 * traverse dictionary tree for each character in searchText. 
	 * if node is not found for current character, 
	 *    return empty list.
	 * if node is found for current character and character is not last character in search text.
	 *    recursive call with found node as dictionary and incremented char position.
	 * if character is last character in search text 
	 * 	  get reference node, 
	 *    build first name / full name and last name exact match result,
	 *    build full name and last name prefix match result by traversing children subtree of node. 
	 *  
	 * <br>
	 * <br>
	 * 
	 * The result are returned in following order.
	 * <ul>
	 * 	<li> full name / first name exact match</li>
	 * 	<li> last name exact match</li>
	 * 	<li> full name prefix match</li>
	 * 	<li> last name prefix match</li>
	 *</ul> 
	 * 
	 * @param searchText for which contact search has to be performed,
	 * @param charPosition current char position in search text.
	 * @param dictionary contact dictionary.
	 * @return list of contacts matched for search text.
	 */
	private List<Contact> search(String searchText, int charPosition,
				ContactDictionaryNode dictionary) {
		
		if (charPosition < searchText.length()) {
			Character c = searchText.charAt(charPosition);
			
			ContactDictionaryNode node = dictionary.get(c);
			if (node == null) {
				// if there is no node entry for character c in dictionary
				return new ArrayList<Contact>();
			}
			// if node is found for character c and current char position 
			// of search text is less than search text length - 1
			if (charPosition < (searchText.length() - 1)) {
				return search(searchText, charPosition + 1, node);
			} else {
				List<Contact> result = new ArrayList<Contact>();
				
				//  full name exact matched contacts list
				List<Contact> fullNameExactMatchContactResult = searchResult(searchText, node.frequency);
				if (fullNameExactMatchContactResult != null) {
					result.addAll(fullNameExactMatchContactResult);
				}
				
				// first name exact matched contacts list
				ContactDictionaryNode spaceCharChildNode = node.get(Contact.SPACE_CHARACTER);
				if (spaceCharChildNode != null) {
					List<Contact> temp = getPrefixMatchContacts(spaceCharChildNode, searchText, searchText + Contact.SPACE_CHARACTER);
					if (temp != null) {
						result.addAll(temp);
					}
				}

				// last name exact match contacts list
				List<Contact> lastNameExactMatchResult = searchResult(searchText, searchText,  node.firstNameList, false);
				if (lastNameExactMatchResult != null) {
					result.addAll(lastNameExactMatchResult);
				}
				
				// list of contact for which first name start with last name.  
				List<Contact> firstNameStartWithLastNameList = new ArrayList<Contact>();
				List<NameFrequency> firstNameList = node.firstNameList;
			
				if (firstNameList != null) {
					for (NameFrequency nameFrequency : firstNameList) {
						if (nameFrequency.getName().equals(searchText)) {
							// continue if first name is equal to last name as first name is already part of result list 
							continue;
						}
						try {
							Contact contact = Contact.newInstance(nameFrequency.getName(), searchText);
							// Check if first name starts with last name, 
							if (StringUtils.startsWith(nameFrequency.getName(), searchText)) {
								// keep contact list for which first name start with full text matched last name. 
								// used this list to remove all duplicate from prefix match contacts result so that
								// such contacts get higher rank in result
								firstNameStartWithLastNameList.add(contact);
							}
						} catch (InvalidContactException e) {
							System.out.println("Error occurred while creating contant from " 
									+ " first name : " + nameFrequency.getName() + " and last name : " + searchText);
						}
					}
				}
				
				List<Contact> prefixMatchContactList = new ArrayList<Contact>();
				
				for (Character ch : node.validKeyCharacters()) {
					// already traverse for first name exact match.
					if (ch == Contact.SPACE_CHARACTER) {
						continue;
					}
					ContactDictionaryNode childNode = node.get(ch);
					if (childNode != null) {
						List<Contact> temp = getPrefixMatchContacts(childNode, searchText, searchText + ch);
						if (temp != null) {
							prefixMatchContactList.addAll(temp);
						}
					}
				}
				prefixMatchContactList.removeAll(firstNameStartWithLastNameList);
				
				result.addAll(prefixMatchContactList);
								
				return result;
			}
		}
	
		return null;
	}

	/**
	 * Method to traverse whole tree rooted at node and return contacts for each valid path.  
	 * @param node root of subtree 
	 * @param searchText input search text
	 * @param pathText pathtext text formed from each character from root to till. 
	 * @return list of contacts.
	 */
	private List<Contact> getPrefixMatchContacts(ContactDictionaryNode node,
			String searchText, String pathText)  {
		
		if (node == null) {
			return null;
		} else {
			List<Contact> prefixMatchContactList = new ArrayList<Contact>();
		
			// full name prefix matched contacts list
			List<Contact> fullNamePrefixMatchContactResult = searchResult(pathText, node.frequency);
			if (fullNamePrefixMatchContactResult != null) {
				prefixMatchContactList.addAll(fullNamePrefixMatchContactResult);
			}
	
			// first name exact matched contacts list
			ContactDictionaryNode spaceCharChildNode = node.get(Contact.SPACE_CHARACTER);
			if (spaceCharChildNode != null) {
				List<Contact> temp = getPrefixMatchContacts(
						spaceCharChildNode, searchText, pathText + Contact.SPACE_CHARACTER);
				if (temp != null) {
					prefixMatchContactList.addAll(temp);
				}
			}

			// last name prefix matched contacts list
			List<Contact> lastNamePrefixMatchResult = searchResult(searchText, pathText, node.firstNameList, true);
			if (lastNamePrefixMatchResult != null) {
				prefixMatchContactList.addAll(lastNamePrefixMatchResult);
			}
		
			// recursive call to traverse children tree for each character
			for (Character ch : node.validKeyCharacters()) {
				// already traverse for first name prefix match.
				if (ch == Contact.SPACE_CHARACTER) {
					continue;
				}
				ContactDictionaryNode childNode = node.get(ch);
				if (childNode != null) {
					List<Contact> temp = getPrefixMatchContacts(childNode, searchText, pathText + ch);
					if (temp != null) {
						prefixMatchContactList.addAll(temp);
					}
				}
			}
			
			return prefixMatchContactList;
		}
	}

	
	/**
	 *  Method to get search result for exact or prefix full name match.
	 * @param fullContact string formatted full contact.
	 * @param frequency occurrence frequency of full contact in dictionary.
	 * @return list of contacts 
	 */
	private List<Contact> searchResult(String fullContact,
			int frequency) {
		List<Contact> result = null ; 
		
		if (frequency == 0) {
			return result;
		}
		 
		try {
			Contact contact  = Contact.newInstance(fullContact);
			result = new ArrayList<Contact>(); 
			for (int i = 1; i <= frequency; i++) {
				result.add(contact);
			}
		} catch (InvalidContactException e) {
			System.out.println("Error occurred while creating contant instance from " 
					+ fullContact);
		}
		return result;
		
	}
	
	/**
	 * Method to get search result for exact or prefix last name match.
	 * @param searchText input search text.
	 * @param lastName contact's last name.
	 * @param firstNameList list of first name mapped to input last name.
	 * @param startWithFilterFlag used to enable/disable start with filter. 
	 * @return list of contacts 
	 */
	
	private List<Contact> searchResult(String searchText, String lastName, List<NameFrequency> firstNameList, 
			boolean startWithFilterFlag) {
		
		List<Contact> result = null ; 
		
		if (firstNameList == null || firstNameList.isEmpty()) {
			return result;
		}
	
		result = new ArrayList<Contact>(); 
		for (NameFrequency nameFrequency : firstNameList) {
			if (startWithFilterFlag && StringUtils.startsWith(nameFrequency.getName(), searchText)) {
				// for last name prefix match, ignore all first name which starts/ equals with search text / equals 
				// to avoid duplicate entries in result as such contacts will be added in result 
				// for full name prefix match.
				continue;
			}
			
			if (! startWithFilterFlag && nameFrequency.getName().equals(searchText)) {
				// for disabled startWithFilterFlag & last name perfect match,
				// ignore all first name which are equal to search text 
				// to avoid duplicate entries in result as such contacts will be added in result 
				// for first name exact match.
				continue;
			}
			
			try {
				
				Contact contact = Contact.newInstance(nameFrequency.getName(), lastName);
				for (int i= 1 ; i <= nameFrequency.getFrequency(); i++) {
					result.add(contact);
				}
			}  catch (InvalidContactException e) {
				System.out.println("Error occurred while creating contant from " 
						+ " first name : " + nameFrequency.getName() + " and last name : " + lastName);
			}
		}
		return result;
	}

	public static void main(String args[]) throws InvalidContactException {

		ContactDictionaryService contactDictionary = new ContactDictionaryServiceImpl();
		Contact contact1 = Contact.newInstance("dnyanesh");//
		Contact contact2 = Contact.newInstance("dnyanesh Puc"); //
		Contact contact3 = Contact.newInstance("pucsd dnyanesh"); //
		Contact contact4 = Contact.newInstance("dnyaneshpucsd dnyanesh"); //
		Contact contact5 = Contact.newInstance("dnyaneshpucsd pucsddnyanesh");//
		Contact contact6 = Contact.newInstance("dnyanesh Test"); //
		Contact contact7 = Contact.newInstance("dnyaneshpucsd dnyaneshtest");
		Contact contact8 = Contact.newInstance("dnyaneshpucsd dnyaneshpucsd");//
		
		Contact contact9 = Contact.newInstance(".", ".");//
			
		
		contactDictionary.add(contact1);
		contactDictionary.add(contact2);
		contactDictionary.add(contact3);
		contactDictionary.add(contact4);
		contactDictionary.add(contact5);
		contactDictionary.add(contact6);
		contactDictionary.add(contact7);
		contactDictionary.add(contact8);
		contactDictionary.add(contact9);
						
		List<Contact> searchResult = contactDictionary.search(".");
		System.out.println(searchResult);
	}
}

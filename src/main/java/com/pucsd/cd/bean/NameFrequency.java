package com.pucsd.cd.bean;
/**
 * Class to keep name and its occurrence frequency count.
 * @author dnyanesh
 *
 */
public class NameFrequency {
	private String name;
	private int frequency;

	public NameFrequency(String name) {
		this.name = name;
		this.frequency = 1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public String toString() {
		return name + " : " + frequency;
	}
}

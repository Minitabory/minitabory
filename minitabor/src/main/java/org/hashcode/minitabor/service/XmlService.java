package org.hashcode.minitabor.service;

public class XmlService {
	public static String sendRedirectXml(String link) {
		return "<?xml version=\"1.0\"?><response><link>" + link + "</link></response>";
	}
}

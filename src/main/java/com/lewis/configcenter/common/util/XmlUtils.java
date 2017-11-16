package com.lewis.configcenter.common.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class XmlUtils {
	
	public static Map<String, String> xml2map(String xmlString) throws Exception {
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		Document doc = new SAXBuilder().build(new StringReader(xmlString));
		Element root = doc.getRootElement();
		for (Element element : root.getChildren()) {
			map.put(element.getName(), element.getText());
		}
		return map;
	}
	
	public static String map2xml(String rootName, Map<String, String> map, boolean needPrefix) throws IOException {
		if (map == null || map.isEmpty())
			return "";
		
		Element root = new Element(rootName);
		Document doc = new Document(root);
		
		for (String key : map.keySet()) {
			Element element = new Element(key);
			element.addContent(map.get(key));
			root.addContent(element);
		}
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		XMLOutputter out = new XMLOutputter();
		
		if (needPrefix) {
			out.output(doc, os);
		} else {
			out.output(doc.getContent(), os);
		}
		
		return os.toString();
	}

}

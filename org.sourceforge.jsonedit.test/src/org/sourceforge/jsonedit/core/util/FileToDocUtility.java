package org.sourceforge.jsonedit.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

public class FileToDocUtility {
	
	public static IDocument getDocument(String location) {
		
		try {
			String string = readFileAsString(location);
			return new Document(string);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private static String readFileAsString(String filePath)
	        throws java.io.IOException{
	            StringBuffer fileData = new StringBuffer(1000);
	            BufferedReader reader = new BufferedReader(
	                    new FileReader(filePath));
	            char[] buf = new char[1024];
	            int numRead=0;
	            while((numRead=reader.read(buf)) != -1){
	                String readData = String.valueOf(buf, 0, numRead);
	                fileData.append(readData);
	                buf = new char[1024];
	            }
	            reader.close();
	            return fileData.toString();
	        }
	
}

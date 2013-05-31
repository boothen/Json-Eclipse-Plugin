package com.boothen.jsonedit.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

public class FileToDocUtility {

	public static IDocument getDocument(String location) {

		try {
			InputStream is = FileToDocUtility.class.getResourceAsStream("/" + location);

			return new Document(readStreamAsString(is));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static File getFile(String file) {

		try {
			URL resource = FileToDocUtility.class.getResource("/" + file);
			URI uri = null;
			if (resource.toString().startsWith("bundleresource")) {
				uri =FileLocator.toFileURL(resource).toURI();
			} else {
				uri =resource.toURI();
			}
			return new File(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String readStreamAsString(InputStream is)
			throws java.io.IOException{
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(is));
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

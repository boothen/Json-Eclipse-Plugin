package com.boothen.jsonedit.text;



/**
 * JsonTextFormatter is used to format the text and returns a formatted string.
 *
 * @author Matt Garner
 *
 */
public class JsonTextFormatter {

	private String indent = "   ";
	private int indentLength = 3;

	private String carriageReturn;
	private int returnLength;

	private String unformattedText;

	public JsonTextFormatter(String text, boolean spaces, int numSpaces, String carriageReturn) {
		this.unformattedText = text;
		this.carriageReturn = carriageReturn;
		this.returnLength = carriageReturn.length();
		initPreferences(spaces, numSpaces);
	}

	private void initPreferences(boolean spaces, int numSpaces) {
		indentLength = (spaces) ? numSpaces : 1;
		if (spaces) {
			StringBuffer strBuf = new StringBuffer();
			for (int i = 0; i < numSpaces; i++) {
				strBuf.append(" ");
			}
			indent = strBuf.toString();
		} else {
			indent = "\t";
		}

	}

	/**
	 * Formats the text returning a formatted string.
	 *
	 * @return formatted text.
	 */
	public String formatText() {

		StringBuilder doc = new StringBuilder(unformattedText);

		try {
			int indentCount = 0;
			boolean key = false;

			int i = 0;
			while (i < doc.length()) {

				char ch = doc.charAt(i);
				if (ch == '"') {
					if(doc.charAt(i-1) != '\\') {
						key = !key;
					}
				}

				if (key) {
					i++;
					continue;
				}

				switch(ch) {
				case '{' : {

					doc.replace(i, i + 1, "{" + carriageReturn + indent(++indentCount));
					i+= returnLength + (indentCount * indentLength);

					break;
				}
				case '}' : {
					doc.replace(i, i + 1, carriageReturn + indent(--indentCount) + "}");
					i+= returnLength + (indentCount * indentLength);

					if (doc.charAt(i+1) != ',' && notBracket(doc, i+1)) {
						doc.replace(i+1, i+1, carriageReturn + indent(indentCount));
						i+= returnLength + (indentCount * indentLength);
					}
					break;
				}
				case ',' : {
					doc.replace(i, i + 1, "," + carriageReturn + indent(indentCount));
					i+= returnLength + (indentCount * indentLength);
					break;
				}
				case '[' : {
					doc.replace(i, i + 1, "[" + carriageReturn + indent(++indentCount));
					i+= returnLength + (indentCount * indentLength);
					break;
				}
				case ']' : {
					doc.replace(i, i + 1, carriageReturn + indent(--indentCount) + "]");
					i+= returnLength + (indentCount * indentLength);

					if (doc.charAt(i + 1) != ',' && notBracket(doc, i + 1)) {
						doc.replace(i+1, i+1, carriageReturn + indent(indentCount));
						i+= returnLength + (indentCount * indentLength);
					}
					break;
				}
				case ' ': {
					doc.replace(i, i+1, "");
					--i;
					break;
				}
				case '\n': {
					doc.replace(i, i+1, "");
					--i;
					break;
				}
				case '\r': {
					doc.replace(i, i+1, "");
					--i;
					break;
				}
				case '\t': {
					doc.replace(i, i+1, "");
					--i;
					break;
				}
				case '\\': {
					if (doc.charAt(i+1) == '"') {
						i++;
					}
					break;
				}
				}
				i++;
			}
		} catch (IndexOutOfBoundsException e) {

		}

		return doc.toString();

	}

	/**
	 * Determine if next character is not a bracket.
	 *
	 * @param doc
	 * @param pos
	 * @return
	 */
	private boolean notBracket(StringBuilder doc, int pos) {
		int i = pos;
		while (i < doc.length()) {
			if (doc.charAt(i) != ' ' && doc.charAt(i) != '\n' && doc.charAt(i) != '\r') {
				if (doc.charAt(i) == '}' || doc.charAt(i) == ']') {
					return false;
				} else {
					return true;
				}

			} else {
				doc.replace(i, i+1, "");
				--i;
			}
			i++;
		}

		return false;
	}

	/**
	 * Indents the text.
	 *
	 * @param count
	 * @return
	 */
	private String indent(int count) {

		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < count; i++) {
			ret.append(indent);
		}
		return ret.toString();
	}
}

package com.mhdt.analyse;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import com.mhdt.io.FileIO;

public class Template {

	String source;
	String content;

	public Template(URI uri) {
		this.content = this.source = FileIO.getContent(new File(uri));
	}

	public Template(InputStream inputStream) {
		this.content = this.source = FileIO.getContent(inputStream);
	}

	public Template(File file) {
		this.content = this.source = FileIO.getContent(file);
	}

	public Template(String content) {
		this.content = content;
	}

	public void setParamer(String key, String value) {
		content = content.replace("${" + key + "}", value);
	}

	public String getText() {
		return content;
	}

	public String getTextAndRest() {
		String temp = content;
		rest();
		return temp;
	}

	public void rest() {
		content = source;
	}

	public String getSource() {
		return source;
	}

}

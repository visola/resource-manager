package br.com.depasser.web.resource;

public class FileUtils {

	public static String getExtension(String path) {
		int lastIndexOf = path.lastIndexOf(".");

		if (lastIndexOf == -1) {
			return null;
		}

		return path.substring(lastIndexOf + 1, path.length());
	}

}

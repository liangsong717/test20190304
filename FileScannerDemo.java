package com.fineway;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileScannerDemo {
	
	/**
	 * 注意编码格式 取决于所在服务器编码
	 * @param filePath
	 * @throws IOException
	 */
	public static String getJsonFromFile(String filePath) throws IOException {

		File file = new File(filePath);
		if (file.isFile() && file.canRead()) {
			System.out.println(file.getName());
		} else {
			System.out.println("no file!!!!!!");
		}
		Scanner scanner = null;
		StringBuilder buffer = new StringBuilder();
		try {
			scanner = new Scanner(file, "gb18030");
			//scanner = new Scanner(file, "UTF-8");
			while (scanner.hasNextLine()) {
				// System.out.println("hasvalue");
				buffer.append(scanner.nextLine().trim());
			}

		} catch (Exception e) {
			System.out.println("1111111:" + scanner.hasNextLine());
			e.printStackTrace();

		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		System.out.println("returnresult=" + buffer.toString().trim());
		return (buffer.toString().trim());
	}

}

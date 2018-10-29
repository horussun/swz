package com.test.file;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileCreate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String srcFile = "I_97CCE324DA498C0AC68EC71E2F04F231";
		String destFile = "C://dest//I_97CCE324DA498C0AC68EC71E2F04F231";
		int count = 1000;
		FileCreate fc = new FileCreate();
		fc.copyFile(srcFile,destFile,count);
	}
	
	public void copyFile(String srcFile,String destFile, int count) {

		try {
			int bytesum = 0;
			int byteread = 0;

			// InputStream inStream = new FileInputStream(srcFile);
			InputStream inStream = null;
			FileOutputStream fs = null;
			for(int i=0 ;i<count;i++) {
				inStream = FileCreate.class.getClassLoader().getResourceAsStream(srcFile);
				fs = new FileOutputStream(destFile+i);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
			}
			inStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

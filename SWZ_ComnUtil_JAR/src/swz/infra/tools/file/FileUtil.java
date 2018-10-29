/**
 * 
 */
package swz.infra.tools.file;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import swz.infra.tools.date.DateUtil;

/**
 * @author Administrator
 *  
 */
public class FileUtil {
	static int iCharNum = 0;
	private static String defaultContentEncoding = Charset.defaultCharset().name();   
	private static String sourceClass = FileUtil.class.getName();
    
	/*
	 * @Function:write the given string(str) to file(strOutFile) in the given
	 * charset type; 
	 * @Para: str:the content to be write file; 
	 * strOutFile:the destination file name. 
	 * psCharsetType:the code type which used for write file; 
	 * @Reuturn: void.
	 */
	public static void writeOutput(String str, String strOutFile,
			String psCharsetType) {
		try {
			FileOutputStream fos = new FileOutputStream(strOutFile);
			Writer out = new OutputStreamWriter(fos, psCharsetType);
			out.write(str);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @Function:write the given string(str) to file(strOutFile) in the given
	 * charset type; 
	 * @Para: str:the content to be write file; 
	 * strOutFile:the destination file name. 
	 * psCharsetType:the code type which used for write file; 
	 * @Reuturn: void.
	 */
	public static void writeOutput(String content, String fileDir, String fileName,	String psCharsetType) {
			File filePath = new File(fileDir);
			if(!filePath.isDirectory()){
				filePath.mkdir();
			}
			writeOutput(content, fileDir+"\\"+fileName, "UTF-8");
	}
	
	/*
	 * @Function:by the given charset type,read the content of the given file to
	 * string; @Para: strInFile:provide the file's path info. psCharsetType:the
	 * code type which used for read the file; @Reuturn: the content string in
	 * the given charset.
	 */
	public static String readInput(String strInFile, String psCharsetType) {
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(strInFile);
			InputStreamReader isr = new InputStreamReader(fis, psCharsetType);
			Reader in = new BufferedReader(isr);
			int ch;
			while ((ch = in.read()) > -1) {
				iCharNum += 1;
				buffer.append((char) ch);
			}
			in.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void createDir(String dir, boolean ignoreIfExitst)
			throws IOException {
		File file = new File(dir);

		if (ignoreIfExitst && file.exists()) {
			return;
		}

		if (file.mkdirs() == false) {
			throw new IOException("���ܶ�ȡ��Ŀ¼ = " + dir);
		}
	}

	public static void createDirs(String dir, boolean ignoreIfExitst)
			throws IOException {
		File file = new File(dir);

		if (ignoreIfExitst && file.exists()) {
			return;
		}

		if (file.mkdirs() == false) {
			throw new IOException("���ܶ�ȡ��Ŀ¼ = " + dir);
		}
	}

	public static void deleteFile(String filename) throws IOException {
		File file = new File(filename);
		if (file.isDirectory()) {
			throw new IOException(
					"IOException -> BadInputException: ����һ��Ϸ����ļￄ1¤7.");
		}
		if (file.exists() == false) {
			throw new IOException(
					"IOException -> BadInputException: �ļ�������.");
		}
		if (file.delete() == false) {
			throw new IOException("����ɾ���ļ�. filename = " + filename);
		}
	}

	public static void deleteDir(File dir) throws IOException {
		if (dir.isFile()) {
			throw new IOException(
					"IOException -> BadInputException: ����һ��Ŀ¼.");
		}
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isFile()) {
					file.delete();
				} else {
					deleteDir(file);
				}
			}
		} //if
		dir.delete();
	}

	public static long getDirLength(File dir) throws IOException {
		if (dir.isFile()) {
			throw new IOException("BadInputException: ����һ��Ŀ¼.");
		}
		long size = 0;
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				long length = 0;
				if (file.isFile()) {
					length = file.length();
				} else {
					length = getDirLength(file);
				}
				size += length;
			} //for
		} //if
		return size;
	}

	public static long getDirLength_onDisk(File dir) throws IOException {
		if (dir.isFile()) {
			throw new IOException("BadInputException:  ����һ��Ŀ¼.");
		}
		long size = 0;
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				long length = 0;
				if (file.isFile()) {
					length = file.length();
				} else {
					length = getDirLength_onDisk(file);
				}
				double mod = Math.ceil(((double) length) / 512);
				if (mod == 0) {
					mod = 1;
				}
				length = ((long) mod) * 512;
				size += length;
			}
		} //if
		return size;
	}

	/*
	 * function:
	 */
	public static byte[] getBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
				1024);
		byte[] block = new byte[512];
		while (true) {
			int readLength = inputStream.read(block);
			if (readLength == -1) {
				break; // end of file
			}
			byteArrayOutputStream.write(block, 0, readLength);
		}
		byte[] retValue = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		return retValue;
	}

	/**
	 * functin: based on fileName,get the content in string format
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String getStringFromFile(String fileName,String charset) {
		File file =	new File(fileName);			
		FileInputStream fis;
		InputStreamReader isr ;
		StringBuffer sb = null;
		try{
			fis = new FileInputStream(file);
			if(StringUtils.isNotBlank(charset)){
				isr = new InputStreamReader(fis,charset);	
			}
			else{
				isr = new InputStreamReader(fis, defaultContentEncoding);
			}
			
			BufferedReader reader = new BufferedReader(isr);

			sb = new StringBuffer();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			} 
			reader.close();
			isr.close();
			fis.close();
		}
		catch(Exception e){
		    e.printStackTrace();	
		}
		return sb.toString();
	}
	
	/*
	 * Function:read the give file and return as a byte[] Limitation:the file's
	 * volume must < 64M,because the max size of byte[] is 64M; para:sFileName
	 * is the full filename(including diretory. Return:byte[] with the specified
	 * file content
	 */
	public static byte[] getBytesFromFile(String sFileName) {
		//File file = new
		// File("D:\\tmp\\WSD11306-USEN-01_business_monitor_ds_1224.pdf");
		File file = new File(sFileName);
		FileInputStream fis;
		final int MAX_BYTE = 60000000;//max size of byte[]
		int readedNum = 0;
		int fileLen;
		byte[] barrResult=null;
		try {
			fis = new FileInputStream(file);
			fileLen = fis.available();
			if (fileLen > MAX_BYTE) {
				System.out
						.println("This file's size exceed 64M,which is exceed the transforming limitation! ");
			} else {
				barrResult = new byte[fileLen];
				readedNum = fis.read(barrResult, 0, fileLen);
				while (readedNum < fileLen) {
					readedNum = readedNum
							+ fis.read(barrResult, readedNum, fileLen
									- readedNum);
				}
				//System.out.println(inOutb.length);
				fis.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return barrResult;
	}

	/*
	 * function:
	 */
	public static String getFileName(String fullFilePath) {
		if (fullFilePath == null) {
			return "";
		}
		int index1 = fullFilePath.lastIndexOf('/');
		int index2 = fullFilePath.lastIndexOf('\\');

		//index is the maximum value of index1 and index2
		int index = (index1 > index2) ? index1 : index2;
		if (index == -1) {
			// not found the path separator
			return fullFilePath;
		}
		String fileName = fullFilePath.substring(index + 1);
		return fileName;
	}

	/*
	 * @para: psDirectory:specified direcotry @para: psSeparator:limiter of the
	 * result @para: pnSwitch: 0: don't include files in the subdirecotry. 1:
	 * include files in the subdirectory @reutrn:
	 * filename1+psSeparator+filename2+psSeparator+filename3
	 */
	public static String getFilesNameInDirectory(String psDirectory,
			String psSeparator, int pnSwitch) {
		File fDirectory = null;
		File[] alFiles = null;
		File fWork = null;
		String sResult = "";

		if (pnSwitch == 0) { //don't include the files of subdirectory
			fDirectory = new File(psDirectory);
			if (fDirectory.isDirectory()) {
				alFiles = fDirectory.listFiles();

				for (int i = 0; i < alFiles.length; i++) {
					fWork = alFiles[i];
					if (fWork.isFile())
						sResult = sResult + fWork.getAbsolutePath()
								+ psSeparator;
				}
			} else {//psDirectory is not a directory
				return "-1";
			}
		}
		return sResult;
	}
	
	/**
	 * get file's ext name
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName){
		String result=null;
		int postion=fileName.lastIndexOf(".");
		if(postion!=-1 && postion!=0){
			result = fileName.substring(postion+1);
		}
		//System.out.println(sourceClass+".getFileExt:" + result );
		return result;
	}

	/**
	 * function: traverse files in spesified dir,  
	 * @param dir
	 * @param recurse: indication for whether recursively traverse sub-directory
	 * @param includedFileType: the file types which will be included in the traverse.
	 * @param afterDate: only show info about files which lastmodified date is after the parameter of afterDate
	 */
	public static void traverseDir(String dir, boolean recurse,HashMap includedFileType,HashMap notIncludedFileType,Date afterDate) {
		String sResult=null;
		try {
			Date lastModified = null;
			File d = new File(dir);
			if (!d.isDirectory()) {
				return;
			}
			File[] files = d.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (recurse && files[i].isDirectory()) {
					traverseDir(files[i].getAbsolutePath(), true, includedFileType,notIncludedFileType,afterDate);
				} else {
					String fileName = files[i].getName();
					String sFileExt = FileUtil.getFileExt(fileName);
					if(sFileExt!=null){
							if(isExtInclueded(includedFileType,notIncludedFileType,sFileExt)){
								lastModified = new Date(files[i].lastModified());
								if(lastModified.after(afterDate)){
									sResult = files[i].getAbsolutePath()+"   "+files[i].getName();
									if(!sFileExt.equalsIgnoreCase("jar")){
										sResult = sResult + "   "+getFileLineNum(files[i]);
									}
									else{
										sResult = sResult + "   "+"N/A";
									}
									sResult = sResult +"   "+DateUtil.format(lastModified);
								}
							}
						if(sResult!=null){
							System.out.println(sResult);
							sResult = null;
						}
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * check the given sExt is contained in hmExt(hashmap). if hmExt have item of *.*,then all sExt is contained.
	 */
	private static boolean isExtInclueded(HashMap hmIncludeExt,HashMap hmNonIncludeExt,String sExt){
		boolean result = false;
		if(hmIncludeExt.get(sExt)!=null){
			result=true;
		}
		else if(hmIncludeExt.get("*.*")!=null){
			result=true;
		}
		
		if(hmNonIncludeExt.get(sExt)!=null){
			result = false;
		}
		return result;
	}
	/**
	 * 
	 * @return: The line number of the files;
	 * @throws IOException 
	 */
	public static int getFileLineNum(File file) throws IOException{
		int result = 0;
		BufferedReader in = null;
		
		in = new BufferedReader(new FileReader(file));
		
		String line = "";
		while ((line = in.readLine()) != null) {
			if(!line.equalsIgnoreCase("")){
			   result++;
			}
		}
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		writeOutput("aaa", "f:\\martin2", "martin.txt",	"UTF-8");
		writeOutput("aaabbb", "f:\\martin2", "martin.txt",	"UTF-8");
	}

	
}

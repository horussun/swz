package swz.infra.tools.string;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.ibm.as400.access.AS400PackedDecimal;
import com.ibm.as400.access.AS400Text;

/**
 * @author
 */
public class CharFormatter {

	/**
	 * Constant for GB18030 encoding. For more important on GB18030, see
	 * http://developers
	 * .sun.com/dev/gadc/technicalpublications/articles/gb18030.html
	 */
	private static final String ENCODING_GB18030 = "GB18030";
	private static final String ENCODING_UTF8 = "UTF-8";
	/**
	 * Constant for EBCDIC codepage. IBM uses Cp1388 for EBCDIC DBCS equivalent
	 * of GB18030 and Cp1381 for ASCII DBCS equivalent. However, in Java, all
	 * variables are in Unicode.
	 */
	private static final int CCSID_EBCDIC = 1388;

	/**
	 * These constants are encoded in Unicode. If you cannot read the chinese
	 * value, try re-opening this file with a Unicode capable editor. If that
	 * doesn't work, this file may have been damaged.
	 */
	protected final static String cNiHao = "你好";

	protected final static String cChnEngComposite = "ABC你好123青岛ൺ";

	/**
	 * Converts Unicode into GB18030.
	 * 
	 * @param str
	 *            Unicode encoded string.
	 * @return Byte array of GB18030 valued string.
	 * @throws Exception
	 */
	public static byte[] convertUnicodeToGB18030(String str) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(str
					.getBytes(ENCODING_GB18030));
			byte ba[] = new byte[bais.available()];
			int i = 0;
			int rc = bais.read();
			while (rc != -1) {
				ba[i++] = (byte) rc;
				rc = bais.read();
			}
			return ba;

		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	/**
	 * Converts Unicode into Codepage 1388 (IBM EBCDIC GB18030 equivalent)
	 * 
	 * @param strBuffer
	 *            Unicode encoded string.
	 * @return Byte array of Cp1388 valued string.
	 * @throws Exception
	 */
	public static byte[] convertUnicodeToCp1388(byte[] strBuffer) {
		if ((strBuffer == null) || (strBuffer.length == 0)) {
			return null;
		}
		return convertUnicodeStrToCp1388(new String(strBuffer));
	}

	/**
	 * Converts Unicode into Codepage 1388 (IBM EBCDIC GB18030 equivalent)
	 * 
	 * @param str
	 *            Unicode encoded string.
	 * @return Byte array of Cp1388 valued string.
	 * @throws Exception
	 */
	public static byte[] convertUnicodeStrToCp1388(String str) {
		try {
			if ((str == null) || (str.length() == 0)) {
				return null;
			}

			// Get length of EBCDIC bytes array from Unicode string
			int ebcdicLen = getCp1388LengthFromUnicodeStr(str).intValue();
			AS400Text oAS400Text = new AS400Text(ebcdicLen, CCSID_EBCDIC);
			byte[] bufferEBCDIC = oAS400Text.toBytes(str);

			return bufferEBCDIC;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	/**
	 * Converts Unicode into Codepage 1388, if str.length > length, converts
	 * valid substring into Codepage 1388
	 * 
	 * @param str
	 *            Unicode encoded string.
	 * @return Byte array of Cp1388 valued string.
	 * @throws Exception
	 */
	public static byte[] convertUnicodeStrToLengthCp1388(String str, Long length) {
		try {
			if ((str == null) || (str.length() == 0)) {
				return null;
			}

			char[] charArray = str.toCharArray();
			int len = 0;
			int ebcdicLen = 0;
			int index = 0;
			for (; index < charArray.length; index++) {
				// char大于255字符占两个字节，并在大于255的连串两边各加一个符号
				// EBCDIC中文字符串前后分别增加(SO)0x0E和(SI)0x0F
				len += charArray[index] > 255 ? 2 + (index != 0
						&& charArray[index - 1] > 255 ? 0 : 2) : 1;
				if (len > length.intValue()) {
					break;
				}
				ebcdicLen = len;
			}

			if (index < str.length()) {
				str = str.substring(0, index);
			}

			AS400Text oAS400Text = new AS400Text(ebcdicLen, CCSID_EBCDIC);
			byte[] bufferEBCDIC = oAS400Text.toBytes(str);

			return bufferEBCDIC;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	/**
	 * Convert Unicode decimal to EBCDIC packed decimal
	 * 
	 * @param decimalStr
	 * @param length
	 * @param decimalPos
	 * @return
	 * @throws Exception
	 */
	public static byte[] convertDecimalToPackedCp1388(String decimalStr,
			Long length, Long decimalPos) {
		try {
			if (decimalStr == null || decimalStr.length() == 0
					|| length <= decimalPos) {
				return null;
			}

			AS400PackedDecimal packedDec = new AS400PackedDecimal(length
					.intValue(), decimalPos.intValue());
			byte[] packedBytes = packedDec.toBytes(new BigDecimal(decimalStr));

			return packedBytes;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}


	/**
	 * Convert EBCDIC packed decimal to Unicode decimal
	 * 
	 * @param packedBytes
	 * @param length
	 * @param decimalPos
	 * @return
	 * @throws Exception
	 */
	public static String convertPackedCp1388ToDecimal(byte[] packedBytes,
			Long length, Long decimalPos) {
		try {
			if (packedBytes.length == 0 || length <= decimalPos) {
				return null;
			}

			AS400PackedDecimal packedDec = new AS400PackedDecimal(length
					.intValue(), decimalPos.intValue());
			BigDecimal bigDecimal  = (BigDecimal)packedDec.toObject(packedBytes);

			// decimalFormat like #0.##
			StringBuffer sb = new StringBuffer().append("#0");
			if (decimalPos > 0) {
				sb.append(".");
				for (int i = 0; i < decimalPos; i++) {
					sb.append("#");
				}
			}
			// 保证精度
			DecimalFormat nf = new DecimalFormat(sb.toString());
			nf.setMinimumFractionDigits(decimalPos.intValue());
			nf.setMaximumFractionDigits(decimalPos.intValue());
			String doubleStr = nf.format(bigDecimal);

			if ((doubleStr == null) || (doubleStr.length() == 0)) {
				return null;
			}

			return doubleStr;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	/**
	 * Get valid substring from Unicode string with limit EBCDIC length
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String getValidStringWithCp1388Length(String str, Long length) {
		if (str == null || length < 1) {
			return "";
		}

		char[] charArray = str.toCharArray();
		int len = 0;
		int index = 0;
		for (; index < charArray.length; index++) {
			// char大于255字符占两个字节，并在大于255的连串两边各加一个符号
			// EBCDIC中文字符串前后分别增加(SO)0x0E和(SI)0x0F
			len += charArray[index] > 255 ? 2 + (index != 0
					&& charArray[index - 1] > 255 ? 0 : 2) : 1;
			if (len > length) {
				break;
			}
		}
		if (index >= str.length()) {
			return str;
		}

		return str.substring(0, index);
	}

	/**
	 * Get the length of Cp1388 encoding from Unicode string
	 * 
	 * @param sStr
	 * @param length
	 * @return
	 */
	public static Long getCp1388LengthFromUnicodeStr(String str) {
		if (str == null || str.length() == 0) {
			return new Long(0);
		}

		char[] charArray = str.toCharArray();
		int len = 0;
		int index = 0;
		for (; index < charArray.length; index++) {
			// char大于255字符占两个字节，并在大于255的连串两边各加一个符号
			// EBCDIC中文字符串前后分别增加(SO)0x0E和(SI)0x0F
			len += charArray[index] > 255 ? 2 + (index != 0
					&& charArray[index - 1] > 255 ? 0 : 2) : 1;
		}

		return new Long(len);
	}

	private static byte[] trim(byte[] buffer, int bufferLength) {
		byte[] actBuffer = new byte[bufferLength];
		System.arraycopy(buffer, 0, actBuffer, 0, bufferLength);
		return actBuffer;
	}

	/**
	 * Converts Codepage 1388 into Unicode.
	 * 
	 * @param bytes
	 *            Cp1388 encoded string.
	 * @return Byte array of Unicode valued string.
	 * @throws Exception
	 */
	public static String convertCp1388ToUnicode(byte bytes[]) {
		try {
			if (bytes == null || bytes.length == 0) {
				return "";
			}
			AS400Text oAS400Text = new AS400Text(bytes.length, CCSID_EBCDIC);
			return (String) oAS400Text.toObject(bytes);
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	/**
	 * 测试: 为测试中文，从 index 开始，读取 length 个字节 (因为核心对连续的中文串开始0x0e和结束0x0f加了特殊字符, 例如
	 * '1张爱萍' 应该占用9个字节.)
	 * 
	 * @param ebcdicFullBytes
	 * @param index
	 * @param length
	 * @return
	 */
	public static String getCp1388FieldToUnicode(byte[] ebcdicFullBytes,
			Long index, Long length) {
		if (ebcdicFullBytes == null || ebcdicFullBytes.length < index
				|| ebcdicFullBytes.length < (index + length)) {
			return null;
		}
		byte[] fieldbyte = new byte[length.intValue()];
		System.arraycopy(ebcdicFullBytes, index.intValue(), fieldbyte, 0,
				length.intValue());
		String fieldUnicodeStr = convertCp1388ToUnicode(fieldbyte);
		// String hexStr = CharFormatter.toHex(fieldbyte);
		// System.out.println("HEX ======" + hexStr);

		return fieldUnicodeStr;
	}

	/**
	 * Merge two byte arrays to one byte array.
	 * 
	 * @return Byte array of two byte arrays.
	 * @throws Exception
	 */
	public static byte[] mergeTwoBytesArray(byte byteArray1[],
			byte byteArray2[]) {
		try {
			int lenArray1 = byteArray1.length;
			int lenArray2 = byteArray2.length;
			byte[] fullArray = new byte[lenArray1 + lenArray2];
			System.arraycopy(byteArray1, 0, fullArray, 0, lenArray1);
			System.arraycopy(byteArray2, 0, fullArray, lenArray1, lenArray2);
			return fullArray;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	/**
	 * Converts char array to byte array.
	 * 
	 * @param chr
	 *            Char array.
	 * @return Byte array.
	 */
	public static byte[] getBytes(char chr[]) {
		byte ba[] = new byte[chr.length];
		for (int i = 0; i < chr.length; i++) {
			ba[i] = (byte) chr[i];
		}
		return ba;
	}

	/**
	 * Print bytes in hex.
	 * 
	 * @param bytes
	 */
	public static void printHex(byte bytes[]) {
		System.out.println(new String(bytes));
		int flag = 0;
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(getHexString(bytes[i]));
			flag++;
			if (flag == 2) {
				System.out.print(" ");
				flag = 0;
			}
		}
		System.out.println("\n");
	}

	/**
	 * Print bytes in hex.
	 * 
	 * @param bytes
	 */
	public static String getPrintHex(byte bytes[]) {
		StringBuffer buffer = new StringBuffer();

		int flag = 0;
		for (int i = 0; i < bytes.length; i++) {
			buffer.append(getHexString(bytes[i]));
			flag++;
			if (flag == 2) {
				buffer.append(" ");
				flag = 0;
			}
		}
		return buffer.toString().toUpperCase();
	}

	public static String getHexString(byte in) {
		StringBuffer sb = new StringBuffer(6);
		String hexStr = Integer.toHexString(in & 0xFF);
		sb.append(((1 == hexStr.length()) ? "0" : "") + hexStr);
		return sb.toString();
	}

	public static String toHex(byte[] in) {
		StringBuffer sb = new StringBuffer(6 * in.length);
		if (in != null) {
			for (int i = 0; i < in.length; ++i) {
				String hexStr = Integer.toHexString(in[i] & 0xFF);
				sb.append("0x" + ((1 == hexStr.length()) ? "0" : "") + hexStr
						+ ((i == in.length - 1) ? "." : ", "));
			}
			return sb.toString();
		}

		return null;
	}

	/**
	 * Output the message byte stream to a file.
	 * 
	 * @param msg
	 * @param tofile
	 */
	public static void toFile(byte[] msg, String tofile) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(tofile));
			if (msg.length > 0) {
				out.write(msg);
			}
		} catch (Exception e) {
			//
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (Exception e) {
					//
				}
			}
		}
	}

	/**
	 * Reads the contents of a file into a byte array.
	 * 
	 * @param filename
	 * @return
	 */
	public static byte[] readFileToByteArray(String filename) {
		InputStream input = null;
		try {
			File file = new File(filename);
			input = openInputStream(file);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			long count = copyLarge(input, output);
			if (count > Integer.MAX_VALUE) {
				return null;
			}

			return output.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception ioe) {
					// ignore
				}
			}
		}
	}

	/**
	 * Opens a FileInputStream for the specified file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file
						+ "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file
					+ "' does not exist");
		}
		return new FileInputStream(file);
	}

	/**
	 * Copy bytes from a large InputStream to an OutputStream.
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static long copyLarge(InputStream input, OutputStream output)
			throws IOException {
		int DEFAULT_BUFFER_SIZE = 1024 * 4;
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * Converts a string representing hexadecimal values into an array of bytes
	 * of those same values.
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByteArray(String hex) {

		int len = hex.length();
		// Odd number of characters return null.
		if ((len & 0x01) != 0) {
			return null;
		}

		len = len / 2;
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();

		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	public static String hexStringToBCD(String hex) {
		return new String(hexStringToByteArray(hex));
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * Converts an array of bytes into a string representing the hexadecimal
	 * values of each byte in order.
	 * 
	 * @param bArray
	 * @return
	 */
	public static String byteArrayToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2) {
				sb.append(0);
			}
			sb.append(sTemp.toUpperCase());
		}

		return sb.toString();
	}

}

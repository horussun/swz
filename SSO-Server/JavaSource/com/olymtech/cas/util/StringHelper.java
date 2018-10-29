package com.olymtech.cas.util;

import java.util.*;


/**
 * Insert the type's description here.
 * Creation date: (9/29/01 3:58:06 PM)
 * @author: Jinhua Xi
 */
public class StringHelper
{
	public static final String SPLIT = "$$$";
	public static final String LINE_BREAK = "\r\n";
	public static final String TAB = "\t";

	/**
	 * StringHelper constructor comment.
	 */
	public StringHelper() {
		super();
	}
	/**
	 * get true or false for the boolean input
	 */
	public static String getString(boolean b)
	{
		if ( b )
			return "true";
		else
			return "false";
	}
	/**
	 * get the specified tabs
	 */
	public static String getTabs(int count)
	{
		String str = "";

		for ( int i=0; i<count; i++)
		{
			str += TAB;
		}

		return str;

	}



	/**
	 * get a quoted string
	 */

	public static String quote(String strSource)
	{
		if ( strSource == null ) strSource = "";
		return "\"" + strSource + "\"";
	}
	/**
	 * make sure it's a valid string, only check null for now
	 */
	public static String validate(String strSource)
	{
		if ( strSource == null )
			return "";
		else
			return strSource;
	}

	/**
	 * make the source string a valid name string by applying the name conventions
	 * 1. empty space and special characters are replaced with "_"
	 * 2. to lower case
	 */
	public static String makeName(String strSource)
	{
		if ( strSource == null )
			return null;

		return strSource.trim().replace(' ', '_').toLowerCase();
	}


   /**
	 * Is the given string a null string?
	 * <p>
	 * A null string is defined as one that has a empty reference (i.e. = null)
	 * or has zero length.
	 *
	 * @param   str     the test string.
	 * @return  true if the the given string is a null string.
	 */
	public static boolean isEmpty(String str)
	{
		return str == null || str.length() == 0;
	}

	/**
	 * Is the given string a blank string?
	 * <p>
	 * A blank string is defined as one that has a empty reference (i.e. = null)
	 * or has zero length after the leading and trailing space characters are
	 * trimmed.
	 *
	 * @param   str     the test string.
	 * @return  true if the the given string is a blank string.
	 */
	public static boolean isBlank(String str)
	{
		return str == null || str.trim().length() == 0;
	}

	/**
	 * Removes the leading and trailing space characters (' ') from the given
	 * string.
	 *
	 * @param   str     the source string.
	 * @return  a string with no leading and trailing space characters.
	 */
	public static String trim(String str)
	{
		return str == null ? null : str.trim();
	}

	/**
	 * Removes white space characters ('\t', '\n', ' ') from the given string.
	 *
	 * @param   str     the source string.
	 * @return  a string with white space characters removed.
	 */
	public static String removeWhiteSpaceChars(String str)
	{
		int len = (str == null) ? 0 : str.length();
		for (int i = 0; i < len; i++)
		{
			switch (str.charAt(i))
			{
				case '\t':
				case '\n':
				case ' ':
					break;

				default:
					return str;
			}
		}
		return "";
	}

	

	/**
	 * Removes the given leading and trailing characters from the given string.
	 *
	 * @param   str     the source string.
	 * @param   chars   the characters to removed.
	 * @return  a string with stripped off the given leading and trailing
	 *          characters.
	 */
	public static String removeChars(String str, String chars)
	{
		if (isEmpty(str))
		{
			return str;
		}

		int sizeOfChars = chars.length();

		while (str.startsWith(chars))
		{
			str = str.substring(sizeOfChars);
		}
		while (str.endsWith(chars))
		{
			str = str.substring(0, str.length() - sizeOfChars);
		}

		return str;
	}

	/**
	 * Splits the given strings into an array of tokens based on the given
	 * separators.
	 *
	 * @param   str     the source string.
	 * @param   sep     the string separators.
	 * @param   count   the number of tokens to return.
	 * @return  an array of string tokens.
	 */
	public static String[] split(String str, String sep, int count)
	{
		if (str == null)
		{
			if (count < 0)
			{
				throw new IllegalArgumentException();
			}
			return new String[count];
		}

		StringTokenizer tok =
			new StringTokenizer(str, sep, count == -1 ? false : true);

		if (count == -1)
		{
			count = tok.countTokens();
		}

		String[] result = new String[count];
		int i = 0;
		while (tok.hasMoreTokens())
		{
			String t = tok.nextToken();
			if (i < count)
			{
				if ((t.length() == 1) && (sep.indexOf(t) != -1))
				{
					continue;
				}
				result[i++] = t;
			}
			else
			{
				result[count-1] += t;
			}
		}
		return result;
	}

	/**
	 * Splits the given strings into an array of tokens based on the given
	 * separators.
	 *
	 * @param   str     the source string.
	 * @param   sep     the string separators.
	 * @return  an array of string tokens.
	 */
	public static String[] split(String str, String sep)
	{
		return split(str, sep, -1);
	}
	
	public static String[] split(String str)
	{
		return split(str, SPLIT, -1);
	}

	/**
	 * Replaces a substring within a string with another string.
	 *
	 * @param   str     the source string.
	 * @param   src     the substring to replace.
	 * @param   tgt     the substring to use for the replacement.
	 * @return  the result string.
	 */
	public static String replace(String str, String src, String tgt)
	{
		if (isEmpty(str) || isEmpty(src))
		{
			return str;
		}

		int startIndex = 0;
		int srcIndex = 0;
		int len = src.length();

		StringBuffer buffer = new StringBuffer();

		while ( (srcIndex = str.indexOf(src, startIndex) ) >=0 )
		{
			 buffer.append( str.substring(startIndex, srcIndex) ).append(tgt);

			// skip the src characters
			startIndex = srcIndex + len;
		}

		buffer.append( str.substring(startIndex, str.length()) );

		return buffer.toString();
	}

	/**
	 * Converts all characters in a string to the given character.
	 *
	 * @param   str     the source string.
	 * @param   ch      the target character.
	 * @return  the converted string.
	 */
	public static String convert(String str, char ch)
	{
		if (isEmpty(str))
		{
			return str;
		}

		int len = str.length();
		StringBuffer buf = new StringBuffer(len + 1);
		for (int i = 0; i < len; i++)
		{
			buf.append(ch);
		}
		return buf.toString();
	}

	/**
	 * Replaces the given set of characters in the given string with a target
	 * character.
	 *
	 * @param   str         the source string.
	 * @param   srcChars    the set of characters to replace.
	 * @param   tgtChar     the target character.
	 * @return  the converted string.
	 */
	public static String replace(String str, String srcChars, char tgtChar)
	{
		for (int i = 0; i < srcChars.length(); i++)
		{
			str = str.replace(srcChars.charAt(i), tgtChar);
		}
		return str;
	}

	/**
	 * Gets the integer value of the given string.
	 *
	 * @param   str         the source string.
	 * @param   defValue    the default integer value.
	 * @param   the integer value of the given string.
	 */
	public static int getIntValue(String str, int defValue)
	{
		if (StringHelper.isBlank(str))
		{
			return defValue;
		}

		try
		{
			return Integer.parseInt(str);
		}
		catch (NumberFormatException e)
		{
			return defValue;
		}
	}

	/**
	 * convert a string from one encoding to another. For example if the current encoding of the string is "iso-8859-1",
	 * then convertEncoding("iso-8859_1", "utf-8") will return a new string with "utf-8" encoding
	 *
	 * @param from String the encoding of the string, such as "iso-8859_1"
	 * @param to String the encoding for the new string, such as "utf-8"
	 * @return String the new string with the new encoding
	 */
	public static String covertEncoding(String source, String from, String to)
	{
		try
		{
			if ( source == null || source.equals("") || from.equals(to) ) return source;

			return new String(source.getBytes(from), to);
		}
		catch (Throwable e)
		{
			
		}

		return source;

	}


	/**
	 * Returns an array of bytes representing the system character encoding of the given
	 * string.
	 *
	 * @param   str     the source string.
	 * @return  a byte array containing the system character encoding of the given string.
	 */
	

	/**
	 * tokenize the string and return a map of attribute name and value pairs
	 * The string should be attribute name values seperated by spaces. The attribute value is quoted with single quotes or double quotes.
	 * For example, [id="My Id" name="my name"]
	 *
	 * @param source String the string containing attributes
	 * @param delim char either ["] or [']
	 * @return ListHashMap a map of attribute name to value
	 */
	

	/**
	 * encode the string so that is can be put between double-quotes or single quotes. This is used to using java to generate a string for javascript.
	 * @param source
	 * @return
	 */
	public static String encodeString(String source)
	{
		source = replace(source, "\r", "\\r");
		source = replace(source, "\n", "\\n");
		source = replace(source, "\"", "\\\"");
		source = replace(source, "\'", "\\\'");

		return source;
	}

}

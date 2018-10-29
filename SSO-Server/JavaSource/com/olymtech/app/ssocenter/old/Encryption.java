package com.olymtech.app.ssocenter.old;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Encryption
{

    private Encryption(String s)
    {
        b = "ECB";
        c = "PKCS5Padding";
        e = null;
        d = s;
        e = a();
    }

    public static synchronized Encryption getInstance(String s)
    {
        Encryption encryption = (Encryption)a.get(s);
        if(encryption == null)
        {
            encryption = new Encryption(s);
            a.put(s, encryption);
        }
        return encryption;
    }

    public SecretKey importSecretKey(String s)
    {
        File file = new File(s);
        FileInputStream fileinputstream = null;
        try
        {
            byte abyte0[] = new byte[(int)file.length()];
            fileinputstream = new FileInputStream(file);
            fileinputstream.read(abyte0);
            e = new SecretKeySpec(abyte0, d.toString());
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            filenotfoundexception.printStackTrace();
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }finally{
        	try {
				fileinputstream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return null;
    }

    public void exportSecretKey(String s, String s1, String s2)
    {
        File file = new File(s);
        File file1 = new File(s1);
        FileOutputStream fileoutputstream = null;
        FileOutputStream fileoutputstream1 = null;
        try
        {
            fileoutputstream = new FileOutputStream(file);
            fileoutputstream.write(e.getEncoded());
            fileoutputstream1 = new FileOutputStream(file1);
            fileoutputstream1.write(s2.getBytes());
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            filenotfoundexception.printStackTrace();
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }finally{
        	try {
				fileoutputstream.close();
				fileoutputstream1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }

    public byte[] encrypt(byte abyte0[])
    {
        String s = d.toString() + "/" + b + "/" + c;
        byte abyte1[] = null;
        try
        {
            Cipher cipher = Cipher.getInstance(s);
            cipher.init(1, e);
            abyte1 = cipher.doFinal(abyte0);
        }
        catch(NoSuchAlgorithmException nosuchalgorithmexception)
        {
            nosuchalgorithmexception.printStackTrace();
        }
        catch(InvalidKeyException invalidkeyexception)
        {
            invalidkeyexception.printStackTrace();
        }
        catch(IllegalStateException illegalstateexception)
        {
            illegalstateexception.printStackTrace();
        }
        catch(IllegalBlockSizeException illegalblocksizeexception)
        {
            illegalblocksizeexception.printStackTrace();
        }
        catch(BadPaddingException badpaddingexception)
        {
            badpaddingexception.printStackTrace();
        }
        catch(NoSuchPaddingException nosuchpaddingexception)
        {
            nosuchpaddingexception.printStackTrace();
        }
        return abyte1;
    }

    public byte[] decrypt(byte abyte0[])
    {
        String s = d.toString() + "/" + b + "/" + c;
        byte abyte1[] = null;
        try
        {
            Cipher cipher = Cipher.getInstance(s);
            cipher.init(2, e);
            abyte1 = cipher.doFinal(abyte0);
        }
        catch(NoSuchAlgorithmException nosuchalgorithmexception)
        {
            nosuchalgorithmexception.printStackTrace();
        }
        catch(InvalidKeyException invalidkeyexception)
        {
            invalidkeyexception.printStackTrace();
        }
        catch(IllegalStateException illegalstateexception)
        {
            illegalstateexception.printStackTrace();
        }
        catch(IllegalBlockSizeException illegalblocksizeexception)
        {
            illegalblocksizeexception.printStackTrace();
        }
        catch(BadPaddingException badpaddingexception)
        {
            badpaddingexception.printStackTrace();
        }
        catch(NoSuchPaddingException nosuchpaddingexception)
        {
            nosuchpaddingexception.printStackTrace();
        }
        return abyte1;
    }

    public SecretKey a()
    {
        SecretKey secretkey = null;
        try
        {
            KeyGenerator keygenerator = KeyGenerator.getInstance(d.toString());
            secretkey = keygenerator.generateKey();
        }
        catch(NoSuchAlgorithmException nosuchalgorithmexception)
        {
            nosuchalgorithmexception.printStackTrace();
        }
        return secretkey;
    }

    public String getStrEncryptMode()
    {
        return b;
    }

    public void setStrEncryptMode(String s)
    {
        b = s;
    }

    public static final String DES = "DES";
    private static Map a = new HashMap();
    private String b;
    private String c;
    private String d;
    private SecretKey e;

}

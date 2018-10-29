package com.test.zip;

public class TestZip {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZipCompressorByAnt zc = new  ZipCompressorByAnt("C:\\test.zip");  
        zc.compressExe("C:\\dest");  

	}

}

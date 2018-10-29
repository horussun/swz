package swz.sample.poi;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "Custom Field&106&115|Custom Field&219&228";
		//String[] array = DataDict.getArrayFromString(str,"\\|");
		String[] array = str.split("\\|");
		for (String ar:array) {
			System.out.println(ar);
		}
		
		
	}

}

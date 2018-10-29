function inputLimiter(val){
	var str = val.value;
	var limit = val.maxLength;
	var byte = 0;
	var newStr ='';
	if(limit!=214783647 && limit!=-1){
		for (var i = 0; i < str.length; i++) {
			var c = str.charCodeAt(i);
			if(c<=128){
				//1 byte character
				byte = byte+1;
			}else if(c<=2048){
				//2 bytes character
				byte = byte+2;
			}else if(c<=65533){
				//3 bytes character
				byte = byte+3;
			}else{
				//4 bytes character
				byte = byte+4;
			}
			if(byte<=limit){
				newStr = newStr + str.charAt(i);
			}
		}
		if(byte>limit){
			val.value = newStr;
			alertInputLengthError(newStr);
		}
	}
}
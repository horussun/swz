function Validator(formName) {
	this.log = '';
	this.count = 0;
	this.customMsg = '';
	this.formName = formName;
}

Validator.prototype.add = function(message) {
	if ( this.customMsg != '' ) {
		message = this.customMsg;
	}
	this.log += '* ' + message + '\\n';
	this.count++;
}

<!--
				   // This function examines the accumulated validation information
				   // and returns "true" if there were no problems and false otherwise.
				   // If there were any problems the problems will be reported to the
				   // user.
-->				   
Validator.prototype.end = function() {
	if (this.log != '') {
		alert('The following field' + (this.count!=1 ? 's were' : ' was') + ' filled out improperly:\\n\\n' + this.log);
		return false;
	}
	return true;
}

<!--
				   // This method checks to see if the specified value is blank.
				   // (static method)
-->				   
Validator.isBlank = function(value) {
	if ( !value || value == '' ) return true;

	for ( var i =0; i < value.length; i++ )
		if ( value.charAt(i) != ' ' ) return false;
	return true;
}

Validator.trim = function(str)
{
  return str.replace(/^\s+|\s+$/g, '');
}

Validator.isBlankOrZero = function(value) {
	return value==''||value==0;
}


Validator.areAnyNonBlank = function() {
	var len = arguments.length;

	for ( var i=0; i<len; i++ ) {
		if ( ! Validator.isBlank( arguments[i] ) ) {
			return true;
		}
	}
	return false;
}


<!--
				// This method ensures that the specified value is not blank.
-->
Validator.prototype.validateReq = function(prettyName, value) {
	if (Validator.isBlank(value) || value == null ) { // Added this null in.. not sure what it will break..
		this.add( 'The ' + prettyName + ' needs to be filled in.' );
		return false;
	}
	return true;
}


Validator.prototype.validate = function(prettyName, name) {
	var i=2;
	var value;
	var fld = document." ); out.print( this.formName ); out.print( "[ name ];
 	
 	if( fld ) {
		if ( fld == null || fld.disabled )
			return;
		value = getFieldValue( fld );
	} else
		value = this.compareDate( name );

	while(i<arguments.length) {
		var arg = arguments[i++];
		var v;

		if (arg=='req') {
			v=this.validateReq(prettyName,value);
		} else if (arg=='integer'||arg=='int') {
			v=this.validateInteger(prettyName,value);
		} else if (arg=='text') {
			v=this.validateText(prettyName,value);
		} else if (arg=='alphaNum') {
			v=this.validateAlphaNum(prettyName,value);
		} else if (arg=='carrierRef') {
			v=this.validateCarrierRef(prettyName,value);
		} else if (arg=='date') {
			v=this.validateDate(prettyName,value);
		} else if (arg=='number') {
			v=this.validateNumber(prettyName,value);
		} else if (arg=='nonZero') {
			v=this.validateNonZero(prettyName,value);
		} else if (arg.substring(0,3)=='len') {
			v=this.validateLength(prettyName,value,arg.substring(3));
		} else if (arg.substring(0,6)=='cmpNum') {
			v=this.validateCmpNum(prettyName,value,arg.substring(6));
		} else if (arg.substring(0,6)=='cmpStr') {
			v=this.validateCmpStr(prettyName,value,arg.substring(6));
		} else if (arg.substring(0,6)=='cmpCal') {
			v=this.validateCmpCal(prettyName,value,arg.substring(6));
		} else if (arg=='email') {
			v=this.validateEmail(prettyName,value);
		} else if (arg=='zip') {
			v=this.validateZipcode(prettyName,value);
		} else if (arg=='zipPartial') {
			v=this.validateZipcodePartial(prettyName,value);
		} else if (arg=='nistevoArea') {
			v=this.validateNistevoArea(prettyName,value);
		} else if (arg=='nonNistevoArea') {
			v=this.validateNonNistevoArea(prettyName,value);
		} else if (arg.substring(0,4)=='mes=') {
			this.customMsg = arg.substring(4);
			continue;
		} else {
			alert(\"Validator.validate():  Invalid option '\" + arg + \"' found.\");
			return false;
		}

		if ( !v )
			return false;

		this.customMsg = '';
	}
	return true;
}

<!--
					 // This method ensures that the specified value is not zero.
-->
Validator.prototype.validateNonZero = function(prettyName, value) {
	if ( !this.validateNumber(prettyName, value) )
		return false;
	if ( Validator.isBlank(value) )
		return true;
	if ( value == 0 ) {
		this.add( 'The ' + prettyName + ' cannot be zero.' );
		return false;
	}
	return true;
}


Validator.prototype.resolve = function(val) {
	var rslt = val.match( /^\\$(.*),(.*)$/ );
	if ( rslt == null ) {
		return [val,val];
	} else { 
		var ref = eval( 'document.' + this.formName + \"['\" + this.formName + '_' + rslt[1] + \"']\" );
		if( !ref || ref.options ) 
			return [ this.compareDate( this.formName + '_' + rslt[1] ), rslt[2] ];
		return [ ref.value, rslt[2] ];
	}
}

<!--
					 // This method validates the size of a number field.  It takes four parameters:
					 // The displayable field name, the value, the operator to use, and the value
					 // to compare against.
-->					 
Validator.prototype.validateCmpNum = function(prettyName, value, opers) {
	if ( Validator.isBlank(value) )
		return true;
	if ( !this.validateNumber(prettyName, value) )
		return false;
	value -= 0;
	if ( opers.charAt(0)=='<' ) {
		if ( opers.charAt(1)=='=') {
			var rslt = this.resolve(opers.substring(2));
			if ( value>rslt[0] ) {
				this.add('The ' + prettyName + ' must be less than or equal to ' + rslt[1] + '.' );
				return false;
			}
		} else {
			var rslt = this.resolve(opers.substring(1));
			if ( value>=rslt[0] ) {
				this.add('The ' + prettyName + ' must be less than ' + rslt[1] + '.' );
				return false;
			}
		}
	} else if ( opers.charAt(0)=='>' ) {
		if ( opers.charAt(1)=='=' ) {
			var rslt = this.resolve(opers.substring(2));
			if ( value<rslt[0] ) {
				this.add('The ' + prettyName + ' must be greater than or equal to ' + rslt[1] + '.' );
				return false;
			}
		} else {
			var rslt = this.resolve(opers.substring(1));
			if ( value<=rslt[0] ) {
				this.add('The ' + prettyName + ' must be greater than ' + rslt[1] + '.' );
				return false;
			}
		}
	} else if ( opers.substring(0,2)=='==' ) {
		var rslt = this.resolve(opers.substring(2));
		if ( value != rslt[0] ) {
			this.add('The ' + prettyName + ' must be equal to ' + rslt[1] + '.' );
			return false;
		}
	} else {
		alert( \"Validator.validateCmpNum():  Unknown operation '\" + opers + \"' encountered.\" );
		return false;
	}
	return true;
}

<!--
					 // This method validates the size of a number field.  It takes four parameters:
					 // The displayable field name, the value, the operator to use, and the value
					 // to compare against.
-->					 
Validator.prototype.validateCmpStr = function(prettyName, value, opers) {
	if ( Validator.isBlank(value) )
		return true;
	value = '' + value;
	if ( opers.charAt(0)=='<' ) {
		if ( opers.charAt(1)=='=') {
			var rslt = this.resolve(opers.substring(2));
			if ( value>rslt[0] ) {
				this.add('The ' + prettyName + ' must be less than or equal to ' + rslt[1] + '.' );
				return false;
			}
		} else {
			var rslt = this.resolve(opers.substring(1));
			if ( value>=rslt[0] ) {
				this.add('The ' + prettyName + ' must be less than ' + rslt[1] + '.' );
				return false;
			}
		}
	} else if ( opers.charAt(0)=='>' ) {
		if ( opers.charAt(1)=='=' ) {
			var rslt = this.resolve(opers.substring(2));
			if ( value<rslt[0] ) {
				this.add('The ' + prettyName + ' must be greater than or equal to ' + rslt[1] + '.' );
				return false;
			}
		} else {
			var rslt = this.resolve(opers.substring(1));
			if ( value<=rslt[0] ) {
				this.add('The ' + prettyName + ' must be greater than ' + rslt[1] + '.' );
				return false;
			}
		}
	} else if ( opers.substring(0,2)=='==' ) {
		var rslt = this.resolve(opers.substring(2));
		if ( value != rslt[0] ) {
			this.add('The ' + prettyName + ' must be equal to ' + rslt[1] + '.' );
			return false;
		}
	} else {
		alert( \"Validator.validateCmpStr():  Unknown operation '\" + opers + \"' encountered.\" );
		return false;
	}
	return true;
}

<!--
					 // This method validates a date.  It takes three parameters:
					 // The displayable field name, the control name,  & the operator to use combined with the control
					 // to compare against.
-->
Validator.prototype.validateCmpCal = function(prettyName, value, opers) {
	if ( Validator.isBlank(value) )
		return true;
	value -= 0;
	if (value==0) return true;
	if ( opers.charAt(0)=='<' ) {
		if ( opers.charAt(1)=='=') {
			var rslt = this.resolve(opers.substring(2));
			if ( value>rslt[0] ) {
				this.add('The ' + prettyName + ' Date/Time must be less than or equal to the ' + rslt[1] + ' Date/Time.' );
				return false;
			}
		} else {
			var rslt = this.resolve(opers.substring(1));
			if ( value>=rslt[0] ) {
				this.add('The ' + prettyName + ' Date/Time must be less than the ' + rslt[1] + ' Date/Time.' );
				return false;
			}
		}
	} else if ( opers.charAt(0)=='>' ) {
		if ( opers.charAt(1)=='=') {
			var rslt = this.resolve(opers.substring(2));
			if ( value<rslt[0] ) {
				this.add('The ' + prettyName + ' Date/Time must be greater than or equal to the ' + rslt[1] + ' Date/Time.' );
				return false;
			}
		} else {
			var rslt = this.resolve(opers.substring(1));
			if ( value<=rslt[0] ) {
				this.add('The ' + prettyName + ' Date/Time must be greater than the ' + rslt[1] + ' Date/Time.' );
				return false;
			}
		}
	} else if ( opers.charAt(0)=='=' && opers.charAt(1)=='=') {
			var rslt = this.resolve(opers.substring(2));
			if ( value!=rslt[0] ) {
				this.add('The ' + prettyName + ' Date/Time must be equal to the ' + rslt[1] + ' Date/Time.' );
				return false;
			}
	} else {
			alert( \"Validator.validateCmpCal():  Unknown operation '\" + opers + \"' encountered.\" );
     return false;
	}
	return true;
}

<!--
					 // function to convert calendar values to a string for byte comparison
-->
Validator.prototype.compareDate = function( controlName ) {
 	var yyyy,mm,dd,hh,DD,ampm,zz;
	var doc = document.forms[this.formName];
 	var convertedDate = '';
 	yyyy = Validator.getDatePartValue( doc[controlName + ':Year'] );     
 	mm =   Validator.getDatePartValue( doc[controlName + ':Month'] );     
 	dd =   Validator.getDatePartValue( doc[controlName + ':Day'] );       
 	hh =   Validator.getDatePartValue( doc[controlName + ':Hour'] );      
 	DD =   Validator.getDatePartValue( doc[controlName + ':Min'] );       
 	ampm = Validator.getDatePartValue( doc[controlName + ':AmPm'] );      
 	zz =   Validator.getDatePartValue( doc[controlName + ':TimeZone'] );  

 	if( yyyy != null )
 		convertedDate += yyyy; 
 	if( mm != null )
 		convertedDate += mm; 
 	if( dd != null )
 		convertedDate += dd;
 	if( hh != null && DD != null )
 		convertedDate += Validator.getTimeValue( hh, DD, ampm, zz);  
	  return convertedDate;
} 

<!--
					 // function to convert hours & minutes to military Time format
-->
Validator.getTimeValue = function(hours, minutes, ampm, zone) { 
 	var hr_value = Number(hours); 
 	if( ampm != null ) { 
 		if( ampm.toString() == '1') { 
 			if( hours < '12' ) 
					hr_value = hr_value + 12; 
			} else { 
				if( hours == '12' ) 
					hr_value = 0; 
			} 
		} 
		hours = hr_value.toString(); 
		if( zone != null ) {
		} 
		if( hours.length == 1 ) 
			hours = '0' + hours; 
		if( minutes.length == 1 ) 
			minutes = '0' + minutes; 
		return hours + minutes ;
} 

<!--
					 // function to return calander select control values		
-->
Validator.getDatePartValue = function( fld ) {

		if ( fld != null && fld[ 'options' ] != null ) {
			var ind = fld.selectedIndex;
			var len = 0;
			
			if ( ind != -1 ) {
     			len = fld.options[ind].value.length;
				
				if( len < 2 && fld.name.indexOf('AmPm') < 0 ) 
					return '0' + fld.options[ind].value;
     			else 
					return fld.options[ind].value;
   			} 
 		}		 
		return null;
}

<!--
					 // This method ensures that the specified value contains a valid number.
					 // Note that number here does not imply integer -- see Validator.validateInteger().
-->					 
Validator.prototype.validateNumber = function(prettyName, value) {

	if ( Validator.isBlank( value ) )
		return true;

	var reg = new RegExp( '^-?(([0-9]+\\.?[0-9]*)|([0-9]*\\.?[0-9]+))$' );


	if ( !reg.test( value ) ) {
		this.add( 'The ' + prettyName + ' does not contain a valid number.' );
		return false;
	}
	return true;
}

<!--
					 // This method verifies that the specified value is an integer.  Note that
					 // a blank value and a zero value is considered to be valid.
-->					 
Validator.prototype.validateInteger = function(prettyName, value) {
	if ( Validator.isBlank( value ) )
		return true;

	var reg = new RegExp( '^-?[0-9]+$' );

	var trimValue = Validator.trim(value);
	if ( !reg.test( trimValue ) ) {
		this.add( 'The ' + prettyName + ' is not a valid integer.' );
		return false;
	}
	return true;
}

<!--
					 // This method ensures that the specified value contains a valid date.
-->
Validator.prototype.validateDate = function(prettyName, value) {
	if ( Validator.isBlank(value) )
		return true;
	if (isNaN(Date.parse(strValue))) {
		this.add( 'The ' + prettyName + ' does not contain a valid date.' );
		return false;
	}
	return true;
}

<!--
					 // This method validates that the specified field contains characters that
					 // are valid for a text box.
-->					 
Validator.prototype.validateText = function(prettyName, value ) {
	if ( Validator.isBlank(value) )
		return true;

	var i = value.search( /[\\\\\"\\<\\%\\#\\|\\>]/ );
	if ( i != -1 ) {
		this.add( 'The ' + prettyName + ' contains an illegal character: ' + value.charAt( i ) );
		return false;
	}
	return true;
}

<!--
					 // This method validates that the specified field contains characters that
					 // are valid for a text box.
-->					 
Validator.prototype.validateAlphaNum = function(prettyName, value ) {
	if ( Validator.isBlank(value) )
		return true;
  	if ( !/^[a-zA-Z0-9]*$/.test(value) ) {
		this.add( 'The ' + prettyName + ' must contain only letters and numbers.' );
		return false;
	}
	return true;
}

<!--
					 // This method validates that the specified field contains characters that
					 // are valid for a text box.
-->					 
Validator.prototype.validateCarrierRef= function(prettyName, value ) {
	if ( Validator.isBlank(value) )
		return true;
  	if ( !/^[a-zA-Z0-9\\/-]*$/.test(value) ) {
		this.add( 'The ' + prettyName + ' must contain only letters, numbers, / or -.' );
		return false;
	}
	return true;
}

<!--
					 // Nistevo Area Names (that is, areas created by Nistevo) must contain at least one dot (".")
-->
Validator.prototype.validateNistevoArea = function(prettyName, value ) {
	if ( Validator.isBlank(value) )
		return true;
	var i = value.indexOf( '.' );
	if ( i == -1 ) {
		this.add( 'The ' + prettyName + ' must contain at least one period.' );
		return false;
	}
	return true;
}

<!--
					 // Non-Nistevo Area Names (that is, areas not created by Nistevo) must not contain a dot (".")
-->
Validator.prototype.validateNonNistevoArea = function(prettyName, value ) {
	if ( Validator.isBlank(value) )
		return true;
	var i = value.indexOf( '.' );
	if ( i != -1 ) {
		this.add( 'The ' + prettyName + ' must not contain any periods.' );
		return false;
	}
	return true;
}

<!--
					 // This method ensures that the specified field is either blank or
					 // contains a valid email address.
-->					 
Validator.prototype.validateEmail = function(prettyName, value) {
	if ( !Validator.isValidEmailAddress( value ) ) {
		this.add( 'The ' + prettyName + ' does not contain a valid email address.' );
		return false;
	}
	return true;
}

<!--
					 // This method ensures that the specified field is either blank or
					 // contains a valid zip code.
-->					 
Validator.prototype.validateZipcode = function(prettyName, value) {
	if ( value == '' )
		return true;
	if ( value.length > 0 ) 
		return true;
	return false;
}

<!--
					 // This method ensures that the specified field is either blank or contains
					 // a valid zip code or partial zip code
-->					 
Validator.prototype.validateZipcodePartial = function(prettyName, value) {
	if ( value == '' )
		return true;
 if ( value.length == 2 || value.length == 3 || value.length == 5 || value.length == 6 )
		return true;

 if ( value.length == 7 && ( value.search( /^\\D\\w{2}\\s\\w{3}/ ) == 0 ) )
		return true;
	this.add( 'The ' + prettyName + ' does not contain a valid postal code or partial postal code.' );" +
	return false;" +
}

<!--
					 // This function determines if the specified email address contains
					 // a valid email address.  Note that an empty string IS considered a
					 // valid email address!  (static method)
-->					 
Validator.isValidEmailAddress = function(s) {

	if ( s == '' )
		return true;
<!--
					// Must have a '@'.
			  		// Must have something before '@'
			  		// Cannot have a ',' or '!' or '#' or space.
			  		// Must have a domain name after the '@'
-->			  		
  var filter=/^\\w+([\\.'-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$/
  return (filter.test(s));
}

<!--
					 // This method validates the length of a string field.  It takes four parameters:
					 // the displayable field name, the value, the operator to use, and the value
					 // to compare against.
-->					 
Validator.prototype.validateLength = function(prettyName, value, operLen) {
	if ( Validator.isBlank(value) )
		return true;
	var l = value.length;
	if ( operLen.charAt(0)=='<' ) {
		if ( operLen.charAt(1)=='=') {
			var len=operLen.substring(2);
			if ( l>len ) {
				this.add('The ' + prettyName + ' must be shorter than ' + len + ' characters.' );
				return false;
			}
		} else {
			var len=operLen.substring(1);
			if ( l>=len ) {
				this.add('The ' + prettyName + ' cannot be longer than ' + len + ' characters.' );
				return false;
			}
		}
	} else if ( operLen.charAt(0)=='>' ) {
		if ( operLen.charAt(1)=='=' ) {
			var len=operLen.substring(2);
			if ( l<len ) {
				this.add('The ' + prettyName + ' must be at least ' + len + ' characters long.' );
				return false;
			}
		} else {
			var len=operLen.substring(1);
			if ( l<=len ) {
				this.add('The ' + prettyName + ' must be longer than ' + len + ' characters.' );
				return false;
			}
		}
	} else if ( operLen.substring(0,2)=='==' ) {
		var len=operLen.substring(2);
		if ( l != len ) {
			this.add('The ' + prettyName + ' must be exactly ' + len + ' characters long.' );
			return false;
		}
	} else {
		alert( \"Validator.validateLength():  Unknown operation '\" + operLen + \"' encountered.\" );
		return false;
	}
	return true;
}

<!--
					 // This method ensures that the specified radio button field has at least one
					 // of its options selected.
-->					 
Validator.prototype.validateSelectedRadio = function(prettyName, name, intNumButtons) {
	if (intNumButtons > 1) {
		for (var i=0; i<intNumButtons; i++) {
			if (eval(name+'['+i+'].checked')) {
				return true;
			}
		}
	} else if (eval(name+'.checked')) {
		return true;
	}
	this.add( 'At least one option from ' + prettyName + ' must be selected.' );
	return false;
}


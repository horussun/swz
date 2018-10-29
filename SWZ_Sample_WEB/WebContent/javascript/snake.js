/*
This is an unfinished attempt to refactor the cursor snake into a pure JavaScript implementation.
I was converting the getSnameMessage to JavaScript but it may be better to embed the message in 
the page with an id an look it up via DOM. Or perhaps have it make a simle AJAX call back to the 
server via a RESTful API for the message i.e. http://www.nistevo.com/snake/message
*/

var x,y
var step=10
var flag=0
var message=''

message=message.split('')

var xpos=new Array()
for (i=0;i<=message.length-1;i++) {
	xpos[i]=-50
}

var ypos=new Array()
for (i=0;i<=message.length-1;i++) {
	ypos[i]=-50
}

function handlerMM(e){
	x = (document.layers) ? e.pageX : document.body.scrollLeft+event.clientX
	y = (document.layers) ? e.pageY : document.body.scrollTop+event.clientY
	flag=1
}

function makesnake() {
	if (flag==1 && document.all) {
    	for (i=message.length-1; i>=1; i--) {
   			xpos[i]=xpos[i-1]+step
			ypos[i]=ypos[i-1]
    	}
		xpos[0]=x+step
		ypos[0]=y
	
		for (i=0; i<message.length-1; i++) {
    		var thisspan = eval('span'+(i)+'.style')
    		thisspan.posLeft=xpos[i]
			thisspan.posTop=ypos[i]
    	}
	}
	
	else if (flag==1 && document.layers) {
    	for (i=message.length-1; i>=1; i--) {
   			xpos[i]=xpos[i-1]+step
			ypos[i]=ypos[i-1]
    	}
		xpos[0]=x+step
		ypos[0]=y
	
		for (i=0; i<message.length-1; i++) {
    		var thisspan = eval('document.span'+i)
    		thisspan.left=xpos[i]
			thisspan.top=ypos[i]
    	}
	}
		var timer=setTimeout('makesnake()',10)
}

for (i=0;i<=message.length-1;i++) {
    document.write(\"<span id='span\"+i+\"' class='spanstyle'>\")
	document.write(message[i])
    document.write('</span>')
}

if (document.layers){
	document.captureEvents(Event.MOUSEMOVE);
}
document.onmousemove = handlerMM;\n


function getSnakeMessage(String snakeMsg) {
		var testing = true
		var today = new Date()

		var JANUARY 	= 0
		var FEBRUARY 	= 1
		var MARCH 		= 2
		var APRIL 		= 3
		var MAY 		= 4
		var JUNE 		= 5
		var JULY 		= 6
		var AUGUST 		= 7
		var SEPTEMBER 	= 8
		var OCTOBER 	= 9
		var NOVEMBER 	= 10
		var DECEMBER 	= 11
		
		var SUNDAY 		= 0
		var MONDAY 		= 1
		var TUESDAY 	= 2
		var WEDNESDAY 	= 3
		var THURSDAY 	= 4
		var FRIDAY 		= 5
		var SATURDAY 	= 6
		
		var weekday=new Array(7)
		weekday[SUNDAY]		= "Sunday"
		weekday[MONDAY]		= "Monday"
		weekday[TUESDAY]	= "Tuesday"
		weekday[WEDNESDAY]	= "Wednesday"
		weekday[THURSDAY]	= "Thursday"
		weekday[FRIDAY]		= "Friday"
		weekday[SATURDAY]	= "Saturday"

		var month = new Array(12)
		month[JANUARY] 	= "January"
		month[FEBRUARY] = "February"
		month[MARCH] 	= "March"
		month[APRIL] 	= "April"
		month[MAY] 		= "May"
		month[JUNE] 	= "June"
		month[JULY] 	= "July"
		month[AUGUST] 	= "August"
		month[SEPTEMBER] = "September"
		month[OCTOBER] 	= "October"
		month[NOVEMBER] = "November"
		month[DECEMBER] = "December"
		
		
		var month = today.getMonth()
		var day = today.getDate()
		
		if ( month == JANUARY && day == 1 ) {
			snakeMsg = "H a p p y   N e w   Y e a r !"
		} else if ( month == DECEMBER && day == 25 ) {
			snakeMsg = "M e r r y   C h r i s t m a s !"
		} else if ( month == JULY && day == 4 ) {
			snakeMsg = "H a p p y   J u l y   4 t h !"
		} else if ( month == FEBRUARY && day == 14 ) {
			snakeMsg = "H a p p y   V a l e n t i n e \\' s   D a y !"
		} else if ( month == OCTOBER && day == 31 ) {
			snakeMsg = "H a p p y   H a l l o w e e n !"
		} else if ( ( month == MARCH || month == APRIL ) && today. == SUNDAY ) {
			var year = currentCal.get( Calendar.YEAR )
			var prime = ( year + 1 ) % 19
			var dominical
			var i
			var j
			var eMonth = 0
			var eDay = 0
			var sundayLetter = new Array()
			var goldenNumber = new Array( 14,3,0,11,0,19,8,0,16,5,0,13,2,0,10,0,18,7,0,15,4,0,12,1,0,9,17,6,0,0,0,0,0,0,0 )
			
			if ( prime == 0 ) {
				prime = 19
			}

			dominical = ( year + ( year / 4 ) + 6 ) % 7

			for ( i = 0; i < 35; i++ ) {
				sundayLetter[i] = 6 - ( ( i + 2) % 7 )
		    }

			loop: 
				
			for ( i = 0; i < 35; i++ ) {
				if ( prime == goldenNumber[i] ) {
					for ( j = i + 1; j < 35; j++ ) {
						if ( sundayLetter[j] == dominical ) {
							if ( j > 9 ) {
								eMonth = 4
								eDay=j - 9
							} else {
								eMonth = 3
								eDay=j + 22
							}
		            
							break loop
						}
					}
				}
		    }

			if ( month == ( eMonth - 1 ) && day == eDay ) {
				snakeMsg = "H a p p y   E a s t e r !"
			}
		} else if ( month == NOVEMBER && today.getDay() == THURSDAY ) {
			int week = weekOfMonth( today.getDate() )
			
			if ( week == 5 || ( week == 4 && ( day + 7 ) > 30 ) ) {
				snakeMsg = "H a p p y   T h a n k s g i v i n g !"
			}
		} else if (testing){
			snakeMsg = "T H I S  I S  A  H A P P Y  T E S T I N G  D A Y  M E S S A G E ! ! ! ! ! ! ! ! !"
		}
		
		return snakeMsg
	}
	
	function weekOfMonth( dayOfMonth ){
		return 	Math.ceil( dayOfMonth / 7 )
	}

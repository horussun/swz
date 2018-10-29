package swz.infra.tools.log;
import org.apache.log4j.Logger;

public class TestLog4j {
//	static Logger log = Log.getLogger(testLog4j.class.getName());
	static Logger log = Log.getLogger(TestLog4j.class.getName());
	public static void main(String args[]) {
         
		log.debug("Here is some DEBUG");
		log.info("Here is some INFO");
		log.warn("Here is some WARN");
		log.error("Here is some ERROR");
		log.fatal("Here is some FATAL");
	}
}

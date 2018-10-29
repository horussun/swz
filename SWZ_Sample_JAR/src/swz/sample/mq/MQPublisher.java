package swz.sample.mq;

import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQTopic;
import com.ibm.mq.jms.MQTopicConnection;
import com.ibm.mq.jms.MQTopicConnectionFactory;
import com.ibm.mq.jms.MQTopicPublisher;
import com.ibm.mq.jms.MQTopicSession;

public class MQPublisher {
	public static void main(String[] args) {
		try {
			MQTopicConnectionFactory tcf = new MQTopicConnectionFactory();

			tcf.setQueueManager("QM1");
			tcf.setBrokerQueueManager("QM1");
			tcf.setHostName("9.186.102.180");
			tcf.setPort(1414);
			tcf.setChannel("SYSTEM.DEF.SVRCONN");

			tcf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
			tcf.setBrokerVersion(JMSC.MQJMS_BROKER_V1);
			tcf.setBrokerPubQueue("SYSTEM.BROKER.DEFAULT.STREAM");
			tcf.setCCSID(1208);

			MQTopicConnection tConn = (MQTopicConnection) tcf.createTopicConnection();
			MQTopicSession topicSession = (MQTopicSession) tConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			MQTopic topic = (MQTopic) topicSession.createTopic("SampleTopic");

			MQTopicSession pubSession = (MQTopicSession) tConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			MQTopicPublisher publisher = (MQTopicPublisher) pubSession.createPublisher(topic);

			TextMessage message = pubSession.createTextMessage();
			message.setText("This is a message ...");
			publisher.publish(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

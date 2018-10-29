package swz.sample.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import com.ibm.mq.*;
import com.ibm.mq.jms.*;

public class MQBrokerSub implements MessageListener {

	private MQTopicConnection connection;
	private MQTopicSession session;
	private MQTopicSubscriber subscriber;

	private void disconnect() throws JMSException {
		subscriber.close();
		session.close();
		connection.stop();
		connection.close();
		System.out.println("Subscriber stopped.");
	}

	private void connect() throws JMSException {

		MQTopicConnectionFactory tcf = new MQTopicConnectionFactory();

		tcf.setQueueManager("QM1");
		tcf.setBrokerQueueManager("QM1");
		tcf.setHostName("9.186.102.180");
		tcf.setPort(1414);
		tcf.setChannel("SYSTEM.DEF.SVRCONN");
		tcf.setCCSID(1208);

		tcf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
		tcf.setBrokerVersion(JMSC.MQJMS_BROKER_V1);

		System.out.println("1");
		connection = (MQTopicConnection) tcf.createTopicConnection();
		System.out.println("2");
		session = (MQTopicSession) connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		System.out.println("3");
		Topic topic = (Topic) session.createTopic("SampleTopic"); // scribble/coords
		System.out.println("4");
		subscriber = (MQTopicSubscriber) session.createSubscriber(topic);
		System.out.println("5");
		subscriber.setMessageListener(this);
		System.out.println("6");
		connection.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MQBrokerSub mqBS = new MQBrokerSub();
		try {
			mqBS.connect();
			System.in.read();

			mqBS.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			TextMessage tMessage = (TextMessage) message;
			String text;
			text = tMessage.getText();
			System.out.println("Received message <" + new String(text.getBytes("ISO-8859-1"), "GBK") // ISO
																										// 8859-1
																										// GB18030
					+ "> with ID <" + message.getJMSMessageID() + ">");
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
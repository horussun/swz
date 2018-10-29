package swz.infra.tools.mq;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

import swz.infra.tools.string.DERes;

/**
 * MQ操作的工具类
 *
 */
public class MQUtil {

	/**
	 * MQ测试
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MQUtil test = new MQUtil();
		try {
			test.init();
			test.sendMsgToSIQ("12345");
			test.close();
		} catch (Exception e) {
		}
	}

	public MQQueueManager qMgr = null;
	public MQQueue recvQueue = null;
	public MQQueue sendQueue = null;
	public String qManagerName = null;
	public String recvQueueName = null;
	public String sendQueueName = null;

	/**
	 * 初始化MQ参数
	 * @throws Exception
	 */
	public void init() throws Exception {
		// configure connection parameters
		MQEnvironment.hostname = DERes.s("hostname");// MQ Server name or IP
		MQEnvironment.port = new Integer(DERes.s("port")).intValue();// listenr port
		MQEnvironment.channel = DERes.s("channel");// Server-Connection Channel
		MQEnvironment.CCSID = new Integer(DERes.s("CCSID")).intValue();
		// MQEnvironment.userID = "mqm";
		// MQEnvironment.password = "123456";
		qManagerName = DERes.s("qManager");
		//recvQueueName = DERes.s("recvQueueName");
		sendQueueName = DERes.s("sendQueueName");
		// Create a connection to the QueueManager
		//System.out.println("Connecting to queue manager: " + qManagerName);
		qMgr = new MQQueueManager(qManagerName);
		// Send Queue
		// Set up the options on the queue we wish to open
		// 可读可写
		// int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;
		// 仅可写
		int sendOpenOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;
		// Now specify the queue that we wish to open and the open options
		//System.out.println("Accessing queue: " + sendQueueName);
		sendQueue = qMgr.accessQueue(sendQueueName, sendOpenOptions);

		// Recv Queue
		//int recvOpenOptions = MQC.MQOO_INPUT_SHARED | MQC.MQOO_FAIL_IF_QUIESCING;
		// Open the queue（打开队列）
		//recvQueue = qMgr.accessQueue(recvQueueName, recvOpenOptions, null,null, null);
		//System.out.println("Accessing queue: " + recvQueueName);
	}

	/**
	 * 发送信息到MQ
	 * @param msg
	 * @throws Exception
	 */
	public void sendMsgToSIQ(String msg) throws Exception {
		// Define a simple WebSphere MQ Message ...
		MQMessage message = new MQMessage();
		message.format = MQC.MQFMT_STRING;
		message.characterSet = 1208;
		String tempCorId = "SA|ORDR";
		message.correlationId = tempCorId.getBytes();
		message.writeString(msg);
		// ... and write some text in UTF8 format
		//message.writeUTF(msg);
		// Specify the default put message options
		MQPutMessageOptions pmo = new MQPutMessageOptions();
		// Put the message to the queue
		// System.out.println("Sending a message...");
		sendQueue.put(message, pmo);
		// commit();
		qMgr.commit();
	}

	/**
	 * 从MQ中取得消息
	 * @return
	 * @throws Exception
	 */
	public String getMsg() throws Exception {

		// Define a simple WebSphere MQ Message ...
		MQMessage message = new MQMessage();
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = gmo.options + MQC.MQGMO_SYNCPOINT;// Get messages under
														// sync point control
		gmo.options = gmo.options + MQC.MQGMO_WAIT; // Wait if no messages on
													// the Queue
		gmo.options = gmo.options + MQC.MQGMO_FAIL_IF_QUIESCING;// Fail if Qeue
																// Manager
																// Quiescing
		gmo.waitInterval = 60000; // Sets the time limit for the wait.
		
		recvQueue.get(message, gmo);
		// String msg = new String(message.read(),"utf-8");
		// System.out.println(new String((byte[])message.readObject(),"utf-8"));
		String shipmentXML = message.readUTF();
		// System.out.println(shipmentXML);
		String md = shipmentXML;
		// commit();
		qMgr.commit();
		return md;
	}

	/**
	 * 关闭MQ
	 * @throws Exception
	 */
	public void close() throws Exception {
		//System.out.println("Closing the queue");
		if (recvQueue != null) {
			recvQueue.close();
		}
		if (sendQueue != null) {
			sendQueue.close();
		}
		// Disconnect from the QueueManager
		//System.out.println("Disconnecting from the Queue Manager");
		if (qMgr != null) {
			qMgr.disconnect();
		}
		//System.out.println("Done!");
	}

}

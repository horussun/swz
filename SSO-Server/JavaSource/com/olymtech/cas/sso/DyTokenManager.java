package com.olymtech.cas.sso;

import org.apache.log4j.Logger;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;


public class DyTokenManager{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DyTokenManager.class);

	private static DyTokenManager dyTokenManager = null;
	public static DyTokenManager getInstance(){
		if(dyTokenManager==null)dyTokenManager = new DyTokenManager();
		return dyTokenManager;
	}
	private static final String autherPage="http://dc.800jit.com/ETMS/services/DCSrvService";	
	/**
	 * 验证动态密码
	 * @param dytokenSn
	 * @param dynamicpwd
	 * @return
	 */
    public String checkDynamicPwdByToken(String engineId,String dytokenSn,String dynamicpwd){
		if (logger.isDebugEnabled()) {
			logger
					.debug("checkDynamicPwdByToken(String, String, String) - start");
		}

    	String retcode="";
    	try {
    		EndpointReference targetEPR = new EndpointReference(autherPage);
    		OMFactory factory = OMAbstractFactory.getOMFactory();
			OMNamespace omNs = factory.createOMNamespace("","");
			OMElement sendmsg = factory.createOMElement("checkDynamicPwdByToken", omNs);
			OMElement engineid = factory.createOMElement("engineid", omNs);
			engineid.addChild(factory.createOMText(engineid, engineId));
			sendmsg.addChild(engineid);
			OMElement tokensn = factory.createOMElement("tokensn", omNs);
			tokensn.addChild(factory.createOMText(tokensn, dytokenSn));
			sendmsg.addChild(tokensn);
			OMElement dynamicpass = factory.createOMElement("dynamicpass", omNs);
			dynamicpass.addChild(factory.createOMText(dynamicpass, dynamicpwd));
			sendmsg.addChild(dynamicpass);
			ServiceClient serviceClient = new ServiceClient();
			Options options = new Options();
			options.setTo(targetEPR);
			serviceClient.setOptions(options);
//			System.out.println(sendmsg);
			OMElement result = serviceClient.sendReceive(sendmsg);//发送完毕，返回接收信息
//			System.out.println(result);
			retcode = result.getFirstChildWithName(new QName("retcode")).getText();
//			System.out.println("retcode="+result.getFirstChildWithName(new QName("retcode")).getText());
//			System.out.println("retdesc="+result.getFirstChildWithName(new QName("retdesc")).getText());
    	} catch (Exception ex) {
			logger.error("checkDynamicPwdByToken(String, String, String)", ex);
    		
    	}

		if (logger.isDebugEnabled()) {
			logger
					.debug("checkDynamicPwdByToken(String, String, String) - end");
		}
    	return retcode;
    }
    
}
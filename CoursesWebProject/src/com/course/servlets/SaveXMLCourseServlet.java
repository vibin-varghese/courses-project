package com.course.servlets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.xml.messaging.JAXMServlet;
import javax.xml.messaging.ReqRespListener;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import com.course.info.CourseInfo;
import com.course.info.CourseInfoImpl;

/**
 * Servlet implementation class saveXMLCourseServlet
 */
@WebServlet("/SaveXMLCourseServlet")
public class SaveXMLCourseServlet extends JAXMServlet implements ReqRespListener {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see JAXMServlet#JAXMServlet()
     */
    public SaveXMLCourseServlet() {
        super();
    }

    static MessageFactory messageFactory = null;

    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        try {
            messageFactory = MessageFactory.newInstance();
        } catch (Exception ex) {}
    }
    
    public SOAPMessage onMessage(SOAPMessage msg){
    	SOAPMessage message = null;
        try {
            SOAPPart soappart = msg.getSOAPPart();
            SOAPEnvelope incomingEnvelope = soappart.getEnvelope();
            SOAPBody body = incomingEnvelope.getBody();

			Iterator iterator = body.getChildElements();
			
			Map<String, String> course = null;
			while(iterator.hasNext()){
				SOAPElement element = (SOAPElement) iterator.next();
	    	    Iterator child = element.getChildElements();
	    	    
	    	    course = new HashMap<>();
	    	    while (child.hasNext()) {	    	        	  
		    	    SOAPElement element1 = (SOAPElement) child.next();
		    	    course.put(element1.getNodeName(), element1.getValue());
	    	    }
			}
			
			CourseInfo courseInfo = new CourseInfoImpl();
			String returnMessage = courseInfo.saveXMLCourseData(course);
			
            message = messageFactory.createMessage();
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();

            envelope.getBody().addChildElement(envelope
                .createName("Response")).addTextNode(returnMessage);

            return message;
        } catch(Exception e) {
        	e.printStackTrace();	
        }
        return message;
    }
}

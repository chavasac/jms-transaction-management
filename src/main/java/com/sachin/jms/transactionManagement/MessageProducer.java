package com.sachin.jms.transactionManagement;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageProducer {

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext initialContext = new InitialContext();
		Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		/*
		 * SessionMode is set while creating JMSContext. Example of using
		 * SESSION_TRANSACTED mode on JMS producer side
		 */
		JMSContext jmsContext = connectionFactory.createContext(JMSContext.SESSION_TRANSACTED);

		JMSProducer producer = jmsContext.createProducer();
		//Sending multiple messages before jmsContext calls commit() on it
		producer.send(requestQueue, "Message 1");
		producer.send(requestQueue, "Message 2");
		producer.send(requestQueue, "Message 3");
		producer.send(requestQueue, "Message 4");
		producer.send(requestQueue, "Message 5");
		
		//Calling commit after sending all messages. All above 5 messages are being sent in 1 JMS transaction
		jmsContext.commit();
		

	}

}

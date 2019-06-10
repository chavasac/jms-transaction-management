package com.sachin.jms.transactionManagement;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageConsumer {

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext initialContext = new InitialContext();
		Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		/*
		 * SessionMode is set while creating JMSContext. Example of using
		 * SESSION_TRANSACTED mode on JMS producer side
		 */
		JMSContext jmsContext = connectionFactory.createContext(JMSContext.SESSION_TRANSACTED);
		

		JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
		
		System.out.println(((TextMessage) consumer.receive()).getText());
		System.out.println(((TextMessage) consumer.receive()).getText());
		System.out.println(((TextMessage) consumer.receive()).getText());
		System.out.println(((TextMessage) consumer.receive()).getText());
		System.out.println(((TextMessage) consumer.receive()).getText());
		
		//All above messages are receive in 1 transaction which is different from JMS producer.
		jmsContext.commit();

	}

}

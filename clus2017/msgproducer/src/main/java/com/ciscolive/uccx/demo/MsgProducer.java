package com.ciscolive.uccx.demo;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MsgProducer {
	
	 ActiveMQConnectionFactory connectionFactory =null;
	 Connection connection = null;
	 
	
	
	
	public  synchronized static MsgProducer getInstance() {
		return new MsgProducer();
	}
	
	public void init(String brokerURL) throws Exception {
		// Create a ConnectionFactory
        connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        // Create a Connection
        connection = connectionFactory.createConnection();
        connection.start();
	}
	
	public void stop() throws Exception
	{
		connection.close();
	}
	
	
	public void send(String topic, String msg) {
		Session session = null;
        try {
           
           

            // Create a Session
             session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createTopic(topic);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            TextMessage message = session.createTextMessage(msg);

            // Tell the producer to send the message
            producer.send(message);

          
            connection.close();
        }
        catch (Exception e) {
           
            e.printStackTrace();
        }
        finally {
        	if(session != null) {
        		  // Clean up
                try {
					session.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}	
        }
    }

}

package en.ehome.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author:Jun
 * @time:2019/4/3
 */
public class ActiveMQTest {
//    @Test
//    public void producerMQTest() throws JMSException {
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
//        Connection connection = connectionFactory.createConnection();
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue = session.createQueue("queue-test");
//        MessageProducer messageProducer = session.createProducer(queue);
//        TextMessage message = session.createTextMessage("hello activemq");
//        messageProducer.send(message);
//        messageProducer.close();
//        session.close();
//        connection.close();
//    }
//
//    @Test
//    public void consumerPQTest() throws Exception {
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
//        Connection connection = connectionFactory.createConnection();
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue = session.createQueue("queue-test");
//        MessageConsumer consumer = session.createConsumer(queue);
//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message){
//                TextMessage textMessage = (TextMessage) message;
//                try {
//                    System.out.println(textMessage.getText());
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        System.in.read();
//        consumer.close();
//        session.close();
//        connection.close();
//    }
////
//    @Test
//    public void topicProducerTest() throws Exception{
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://www.unone.club:61616");
//        Connection connection = connectionFactory.createConnection();
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Topic topic = session.createTopic("topic1");
//        MessageProducer producer = session.createProducer(topic);
//        TextMessage message = session.createTextMessage("hello topic1");
//        producer.send(message);
//        producer.close();
//        session.close();
//        connection.close();
//    }
//
//    @Test
//    public void topicConsumerTest() throws Exception{
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://www.unone.club:61616");
//        Connection connection = connectionFactory.createConnection();
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Topic topic = session.createTopic("topic1");
//        MessageConsumer consumer = session.createConsumer(topic);
//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                TextMessage textMessage = (TextMessage) message;
//                try {
//                    String s = textMessage.getText();
//                    System.out.println(s);
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        System.in.read();
//        consumer.close();
//        session.close();
//        connection.close();
//
//    }
}

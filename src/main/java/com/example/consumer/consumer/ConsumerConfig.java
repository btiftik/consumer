package com.example.consumer.consumer;


import com.example.consumer.data.Data;
import com.example.consumer.data.DataReader;
import com.example.consumer.data.DataWriter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Configuration
@EnableJms
public class ConsumerConfig {

    @Value("#{'${activemq.broker-url}'}")
    private String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setTrustAllPackages(true);
        activeMQConnectionFactory.setExclusiveConsumer(true);

        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory
                .setConnectionFactory(receiverActiveMQConnectionFactory());
        factory.setErrorHandler(t -> System.out.println("Error in listener! " + t));
        return factory;
    }

    @Bean
    @Scope("prototype")
    public Unmarshaller unmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        return jaxbContext.createUnmarshaller();
    }

    @Bean
    public Consumer receiver() {
        return new Consumer();
    }

    @Bean
    public DataReader dataReader() {
        return new DataReader();
    }

    @Bean
    public DataWriter dataWriter() {
        return new DataWriter();
    }

}
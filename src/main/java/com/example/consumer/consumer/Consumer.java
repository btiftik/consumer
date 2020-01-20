package com.example.consumer.consumer;

import com.example.consumer.data.Data;
import com.example.consumer.data.DataReader;
import com.example.consumer.data.DataWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.xml.bind.JAXBException;


@Component
public class Consumer {

    @Autowired
    private DataReader dataReader;

    @Autowired
    private DataWriter dataWriter;

    private static final String HEADER_ERROR_MESSAGE = "Improperly Formatted Header";

    @JmsListener(destination = "mailbox")
    public void receiveMessage(String input, @Headers MessageHeaders messageHeaders) throws JAXBException {
        Data data = dataReader.convertXMLStringToData(input);

        Assert.notNull(messageHeaders, HEADER_ERROR_MESSAGE);
        Assert.notNull(messageHeaders.getTimestamp(), HEADER_ERROR_MESSAGE);
        Assert.notNull(messageHeaders.getId(), HEADER_ERROR_MESSAGE);

        dataWriter.write(data/*, messageHeaders.getTimestamp(), messageHeaders.getId().toString()*/);


    }

}

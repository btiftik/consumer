package com.example.consumer.data;

import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class DataReader {

    @Autowired
    private Unmarshaller unmarshaller;


    public Data convertXMLStringToData(String input) throws JAXBException {
        Data data;
        data = (Data) unmarshaller.unmarshal(new StringReader(input));
        return data;
    }
}

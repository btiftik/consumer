package com.example.consumer.data;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataWriter {

    private Data privateData = null;

    public void write(Data data/*, long timeStamp, String serverId*/) {
        Data dataToBePrinted = addNewData(data);
        if (dataToBePrinted != null) {

            //System.out.println("Received " + convertDataToJsonFormat(dataToBePrinted) + " new date: " + dataToBePrinted.getTimeStamp() + " time: " + timeStamp);
            System.out.println(convertDataToJsonFormat(dataToBePrinted));
        }
    }

    private String convertDataToJsonFormat(Data data) {
        String jsonString = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonString = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            System.out.println("Mapper Error");
            e.printStackTrace();
        }

        return jsonString;
    }


    Data addNewData(Data data) {
        Data result;
        if (privateData == null) {
            privateData = data;
            result = null;
        } else if (privateData.getTimeStamp() == data.getTimeStamp()) {
            privateData.setAmount(privateData.getAmount() + data.getAmount());
            result = null;
        } else {
            result = privateData;
            privateData = data;
        }
        return result;
    }

}

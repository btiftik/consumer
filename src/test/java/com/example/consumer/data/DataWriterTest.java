package com.example.consumer.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DataWriterTest {

    @Autowired
    private DataWriter dataWriter;
    private Data data1;
    private Data data2;
    private Data data3;
    private Data data4;
    private Data data6;
    private Data data7;
    private Data returner;

    @Before
    public void init() {
        data1 = new Data(100, 300L);
        data2 = new Data(50.556, 300L);
        data3 = new Data(25.556, 300L);
        data4 = new Data(12.556, 301L);
        data6 = new Data(-25.555, 300L);
        data7 = new Data(-50.555, 300L);
        returner = new Data(-50.555, 9999L);

    }

    @Test
    public void shouldReturnNullValue() {
        Assert.assertNull(dataWriter.addNewData(data1));
        Assert.assertNull(dataWriter.addNewData(data2));
        Assert.assertNull(dataWriter.addNewData(data3));
    }

    @Test
    public void shouldReturnNonNullValue() {
        dataWriter.addNewData(data1);
        Assert.assertNotNull(dataWriter.addNewData(data4));
        Assert.assertNotNull(dataWriter.addNewData(data2));
    }


    @Test
    public void shouldGiveCorrectAmount() {
        dataWriter.addNewData(data3);
        dataWriter.addNewData(data6);
        double expected = 0.001;
        Assert.assertEquals(expected, dataWriter.addNewData(returner).getAmount(), 0.000000001);

        dataWriter.addNewData(data2);
        dataWriter.addNewData(data7);
        Assert.assertEquals(expected, dataWriter.addNewData(returner).getAmount(), 0.000000001);

        dataWriter.addNewData(data6);
        dataWriter.addNewData(data7);

        Assert.assertEquals(-76.11, dataWriter.addNewData(returner).getAmount(), 0.000000001);

    }
}

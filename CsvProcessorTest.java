package com.sensordata;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CsvProcessorTest {

    @Test
    public void testFilterRemovesHighTemp() throws Exception {
        CsvProcessor p = new CsvProcessor();
        List<SensorReading> readings =
                p.readCsv("src/test/resources/test_sensor_data.csv");
        List<SensorReading> filtered = p.filterHighTemp(readings);
        assertEquals(3, filtered.size());
    }

    @Test
    public void testAverageHumidity() throws Exception {
        CsvProcessor p = new CsvProcessor();
        List<SensorReading> readings =
                p.readCsv("src/test/resources/test_sensor_data.csv");
        Map<String, Double> avg = p.averageHumidityPerSensor(
                p.filterHighTemp(readings));
        assertEquals(60.0, avg.get("S1"), 0.01);
        assertEquals(55.0, avg.get("S2"), 0.01);
        assertNull(avg.get("S3"));
    }

    @Test
    public void testCountPerSensor() throws Exception {
        CsvProcessor p = new CsvProcessor();
        List<SensorReading> readings =
                p.readCsv("src/test/resources/test_sensor_data.csv");
        Map<String, Long> counts = p.countPerSensor(
                p.filterHighTemp(readings));
        assertEquals(1L, counts.get("S1"));
        assertEquals(2L, counts.get("S2"));
    }
}
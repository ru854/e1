package com.sensordata;

import java.util.List;
import java.util.Map;

public class SensorProcessor {
    public static void main(String[] args) throws Exception {
        CsvProcessor processor = new CsvProcessor();

        List<SensorReading> readings =
                processor.readCsv("src/main/resources/sensor_data.csv");
        System.out.println("Read " + readings.size() + " readings.");

        List<SensorReading> filtered = processor.filterHighTemp(readings);
        System.out.println("After filter: " + filtered.size() + " readings.");

        Map<String, Double> avgHumidity = processor.averageHumidityPerSensor(filtered);
        Map<String, Long> counts = processor.countPerSensor(filtered);

        processor.writeSummary(avgHumidity, counts, "summary_report.csv");
        System.out.println("summary_report.csv written with "
                + avgHumidity.size() + " sensors.");
    }
}
package com.sensordata;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class CsvProcessor {

    public List<SensorReading> readCsv(String path) throws Exception {
        List<SensorReading> readings = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                readings.add(new SensorReading(
                        line[0].trim(), line[1].trim(),
                        Double.parseDouble(line[2].trim()),
                        Double.parseDouble(line[3].trim()),
                        Double.parseDouble(line[4].trim())));
            }
        }
        return readings;
    }

    public List<SensorReading> filterHighTemp(List<SensorReading> readings) {
        return readings.stream()
                .filter(r -> r.getTemperature() <= 100)
                .collect(Collectors.toList());
    }

    public Map<String, Double> averageHumidityPerSensor(List<SensorReading> readings) {
        return readings.stream()
                .collect(Collectors.groupingBy(
                        SensorReading::getSensorId,
                        TreeMap::new,
                        Collectors.averagingDouble(SensorReading::getHumidity)));
    }

    public Map<String, Long> countPerSensor(List<SensorReading> readings) {
        return readings.stream()
                .collect(Collectors.groupingBy(
                        SensorReading::getSensorId,
                        TreeMap::new,
                        Collectors.counting()));
    }

    public void writeSummary(Map<String, Double> summary,
                             Map<String, Long> counts,
                             String path) throws Exception {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("sensor_id,avg_humidity,reading_count\n");
            for (Map.Entry<String, Double> e : summary.entrySet()) {
                writer.write(String.format("%s,%.2f,%d%n",
                        e.getKey(), e.getValue(),
                        counts.getOrDefault(e.getKey(), 0L)));
            }
        }
    }
}
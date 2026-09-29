package com.sensordata;

public class SensorReading {
    private final String timestamp;
    private final String sensorId;
    private final double temperature;
    private final double humidity;
    private final double pressure;

    public SensorReading(String timestamp, String sensorId,
                         double temperature, double humidity, double pressure) {
        this.timestamp = timestamp;
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getTimestamp() { return timestamp; }
    public String getSensorId() { return sensorId; }
    public double getTemperature() { return temperature; }
    public double getHumidity() { return humidity; }
    public double getPressure() { return pressure; }
}
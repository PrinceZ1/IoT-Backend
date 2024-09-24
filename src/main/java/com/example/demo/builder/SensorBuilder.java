package com.example.demo.builder;

import java.time.LocalDateTime;

public class SensorBuilder {
    private Integer temperature;
    private Integer humidity;
    private Integer light;
    private LocalDateTime timestamp;

    private SensorBuilder(Builder builder) {
        this.temperature = builder.temperature;
        this.humidity = builder.humidity;
        this.light = builder.light;
        this.timestamp = builder.timestamp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Integer getLight() {
        return light;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private Integer temperature;
        private Integer humidity;
        private Integer light;
        private LocalDateTime timestamp;

        public Builder setHumidity(Integer humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder setLight(Integer light) {
            this.light = light;
            return this;
        }

        public Builder setTemperature(Integer temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        public SensorBuilder build() {
            return new SensorBuilder(this);
        }
    }
}

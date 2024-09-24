package com.example.demo.builder;

import java.time.LocalDateTime;

public class LedBuilder {
    private String deviceName;
    private String active;
    private LocalDateTime timestamp;

    private LedBuilder(Builder builder) {
        this.deviceName = builder.deviceName;
        this.active = builder.active;
        this.timestamp = builder.timestamp;
    }
    public String getActive() {
        return active;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private String deviceName;
        private String active;
        private LocalDateTime timestamp;

        public Builder setActive(String active) {
            this.active = active;
            return this;
        }

        public Builder setDeviceName(String deviceName) {
            this.deviceName = deviceName;
            return this;
        }

        public Builder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        public LedBuilder build() {
            return new LedBuilder(this);
        }
    }
}

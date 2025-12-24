package org.lovekapibarasan.shutdown_android.config;

public class RepeatingTriggerConfig {
    public Integer startHour;
    public Integer startMinute;
    public Integer intervalMinutes;
    public Integer endHour;
    public Integer endMinute;
    public Integer durationMinutes;

    public RepeatingTriggerConfig(Integer startHour, Integer startMinute,
                                  Integer intervalMinutes, Integer endHour,
                                  Integer endMinute, Integer durationMinutes) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.intervalMinutes = intervalMinutes;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.durationMinutes = durationMinutes;
    }
}
package org.lovekapibarasan.shutdown_android.config;

public class RepeatingTriggerConfig {
    public Integer startHour;
    public Integer startMinute;
    public Long intervalMinutes;
    public Integer endHour;
    public Integer endMinute;
    public Long durationMinutes;

    public RepeatingTriggerConfig(Integer startHour, Integer startMinute,
                                  Long intervalMinutes, Integer endHour,
                                  Integer endMinute, Long durationMinutes) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.intervalMinutes = intervalMinutes;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.durationMinutes = durationMinutes;
    }
}
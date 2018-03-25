package pl.com.bottega.cms.application;

import java.time.LocalTime;

public class ShowDto {
    private Long id;
    private LocalTime time;

    public ShowDto(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}

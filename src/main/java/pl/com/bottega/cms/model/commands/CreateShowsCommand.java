package pl.com.bottega.cms.model.commands;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class CreateShowsCommand implements Command {
    private Long movieId;
    private Long cinemaId;
    private Set<LocalDateTime> dates;
    private ShowsCalendar calendar;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Set<LocalDateTime> getDates() {
        return dates;
    }

    public void setDates(Set<LocalDateTime> dates) {
        this.dates = dates;
    }

    public ShowsCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(ShowsCalendar calendar) {
        this.calendar = calendar;
    }

    public void validate(ValidationErrors errors) {
        validatePresence(errors,"movieId", movieId);
        validatePresence(errors,"cinemaId", cinemaId);
        validatePresence(errors,"dates", dates, "calendar", calendar);
        if (calendar != null) {
            validatePresence(errors, "weekDays", calendar.getWeekDays());
            validatePresence(errors, "hours", calendar.getHours());
            validatePresence(errors, "weekDays", calendar.getWeekDays());
            validateWeekdays(errors, "weekDays", calendar.getWeekDays());
            validatePresence(errors, "fromDate", calendar.getFromDate());
            validatePresence(errors, "untilDate", calendar.getUntilDate());
        }
        if (dates != null) {
            validatePresence(errors, "dates", dates);
        }
    }

    public LocalDate getFromDate() {
        return this.getCalendar().getFromDate().toLocalDate();
    }

    public LocalDate getUntilDate() {
        return this.getCalendar().getUntilDate().toLocalDate();
    }

    public boolean calendarContainsDate(LocalDate date) {
        String dayOfWeekFromDate = date.getDayOfWeek().name();
        return this.getCalendar().isWeekdaysContain(dayOfWeekFromDate);
    }

    public Set<LocalTime> getCalendarHours() {
        return this.getCalendar().getHours();
    }

    public LocalDateTime getFromDateTime() {
        return this.getCalendar().getFromDate();
    }

    public LocalDateTime getUntilDateTime() {
        return this.getCalendar().getUntilDate();
    }

    public boolean calendarContainsDateTime(LocalDateTime showDateTime) {
        if (!showDateTime.isBefore(getFromDateTime()) && !showDateTime.isAfter(getUntilDateTime())) {
            return true;
        }
        return false;
    }
}

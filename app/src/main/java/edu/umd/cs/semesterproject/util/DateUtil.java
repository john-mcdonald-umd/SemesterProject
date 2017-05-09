package edu.umd.cs.semesterproject.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.umd.cs.semesterproject.model.Time;
import edu.umd.cs.semesterproject.model.TimeRule;

public class DateUtil {

    // TODO: Add static date/time conversion code.
    public static Calendar setCalendar(int dayOfTheWeek, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, dayOfTheWeek);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar;
    }

    public static int getCalendarDay(TimeRule.Day day) {
        int calendarDay;

        switch (day) {
            case SUN:
                calendarDay = Calendar.SUNDAY;
                break;
            case MON:
                calendarDay = Calendar.MONDAY;
                break;
            case TUE:
                calendarDay = Calendar.TUESDAY;
                break;
            case WED:
                calendarDay = Calendar.WEDNESDAY;
                break;
            case THUR:
                calendarDay = Calendar.THURSDAY;
                break;
            case FRI:
                calendarDay = Calendar.FRIDAY;
                break;
            case SAT:
                calendarDay = Calendar.SATURDAY;
                break;
            default:
                calendarDay = Calendar.SUNDAY;
                break;
        }

        return calendarDay;
    }

    public static String timeToCsv(Time time) {
        return time.getHour() + "," + time.getMinute();
    }

    public static Time csvToTime(String csv) {
        String[] times = csv.split(",");

        return new Time(Integer.parseInt(times[0]), Integer.parseInt(times[1]));
    }

    public static String dayListToCsv(List<TimeRule.Day> days) {
        String csv = "";

        for (int i = 0; i < days.size() - 1; i++) {
            csv += days.get(i) + ",";
        }
        csv += days.get(days.size() - 1);

        return csv;
    }

    public static List<TimeRule.Day> csvToDayList(String csv) {
        String[] days = csv.split(",");
        List<TimeRule.Day> dayList = new ArrayList<>();

        for (int i = 0; i < days.length; i++) {
            dayList.add(TimeRule.Day.valueOf(days[i]));
        }

        return dayList;
    }
}

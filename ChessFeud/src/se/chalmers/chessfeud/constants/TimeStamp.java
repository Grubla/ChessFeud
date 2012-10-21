package se.chalmers.chessfeud.constants;

/**
 * This is a class for creating and managing timestamps. This class is useful
 * when calculating how long ago a timestamp were.
 * 
 * @author Henrik Alburg
 * 
 *         Copyright (c) Henrik Alburg 2012
 */
public class TimeStamp {
	private static int[] daysPerMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
			31, 30, 31 };
	private static int secondsPerMinute = 60;
	private static int secondsPerHour = secondsPerMinute * 60;
	private static int secondsPerDay = secondsPerHour * 24;
	private static int daysPerYear = 365;
	private static int daysPerLeapYear = 366;

	private long timeSeconds;

	/**
	 * Creates a timestamp from a string. The string should be on the form:
	 * "2012-10-20 13:37:18"
	 * 
	 * @param s
	 *            The string representing a date
	 */
	public TimeStamp(String s) {
		String[] split = s.split(" ");
		String[] ymd = split[0].split("-");
		String[] hms = split[1].split(":");

		this.timeSeconds = 0;

		int year = Integer.parseInt(ymd[0]);
		int month = Integer.parseInt(ymd[1]);
		int day = Integer.parseInt(ymd[2]);

		int hour = Integer.parseInt(hms[0]);
		int minute = Integer.parseInt(hms[1]);
		int seconds = Integer.parseInt(hms[2]);

		timeSeconds += getYearSeconds(year);
		timeSeconds += getMonthSeconds(month);
		timeSeconds += getDaySeconds(day);

		timeSeconds += getHourSeconds(hour);
		timeSeconds += getMinuteSeconds(minute + 1);
		timeSeconds += seconds;

	}

	/**
	 * Returns the amount of seconds since the start of 1970.
	 * 
	 * @return
	 */
	public long getSeconds() {
		return timeSeconds;
	}

	/**
	 * Returns the number of minutes since this timestamp took place.
	 * 
	 * @return The number of minutes since this date.
	 */
	public int getMinutesSinceStamp() {
		long currentSeconds = System.currentTimeMillis();
		return (int) ((currentSeconds - timeSeconds * 1000) / 60000);
	}

	/* Returns the number of seconds that have passed for all the months */
	private int getMonthSeconds(int month) {
		int seconds = 0;

		for (int i = 0; i < month - 1; i++) {
			seconds += daysPerMonth[i] * secondsPerDay;
		}
		return seconds;
	}

	/* Returns the number of seconds between 1970 and this year */
	private int getYearSeconds(int year) {
		int seconds = 0;
		for (int i = 1970; i < year; i++) {
			if (i % 4 == 0) {
				seconds += daysPerLeapYear * secondsPerDay;
			} else {
				seconds += daysPerYear * secondsPerDay;
			}
		}
		return seconds;
	}

	/*
	 * Returns the number of seconds between the first day in this month and
	 * this day
	 */
	private int getDaySeconds(int day) {
		int seconds = 0;
		for (int i = 0; i < day; i++) {
			seconds += secondsPerDay;
		}
		return seconds;
	}

	/* Returns the number of seconds between the days first hour and this hour */
	private int getHourSeconds(int hour) {
		int seconds = 0;
		for (int i = 1; i < hour - 1; i++) {
			seconds += secondsPerHour;
		}
		return seconds;
	}

	/*
	 * Returns the number of seconds between this minute and the first minute of
	 * the hour
	 */
	private int getMinuteSeconds(int minute) {
		return secondsPerMinute * (minute);
	}
}

package com.youedata.modular.support;

import org.joda.time.DateTime;

import java.util.Date;


/**
 * 时间加工厂
 * @author ibm
 *
 */
public class TimeProcessFactory {
	public static TimeRegion processTime(Date date,TimeGrain timeGrain){
		TimeRegion timeRegion = null;
		switch (timeGrain) {
		case Day:
			timeRegion = processDayTime(date);
			break;
		case Week:
			timeRegion = processWeekTime(date);
			break;
		case Month:
			timeRegion = processMonthTime(date);
			break;
		case Year:
			timeRegion = processYearTime(date);
			break;
		default:
			break;
		}
		
		return timeRegion;
	}
	
	private static TimeRegion processDayTime(Date date) {
		DateTime dateTime = new DateTime(date);
		//获取这一天的开始时间 和结束时间
		DateTime startTime = dateTime.withHourOfDay(0).withMinuteOfHour(0)
				.withSecondOfMinute(0).withMillisOfSecond(0);
		DateTime endTime = startTime.plusDays(1);
		return new TimeRegion(startTime,endTime);
	}

	private static TimeRegion processWeekTime(Date date) {
		DateTime dateTime = new DateTime(date);
		//获取这一周的开始时间 和结束时间
		DateTime startTime = dateTime.withDayOfWeek(1).withHourOfDay(0).withMinuteOfHour(0)
			.withSecondOfMinute(0).withMillisOfSecond(0);
		
		//joda默认是从礼拜一开始的
		DateTime endTime = startTime.plusWeeks(1);
		return new TimeRegion(startTime,endTime);
	}

	private static TimeRegion processMonthTime(Date date) {
		DateTime dateTime = new DateTime(date);
		//获取这一月的开始时间 和结束时间
		DateTime startTime = dateTime.withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0)
				.withSecondOfMinute(0).withMillisOfSecond(0);
		DateTime endTime = startTime.plusMonths(1);
		return new TimeRegion(startTime,endTime);
	}

	private static TimeRegion processYearTime(Date date) {
		DateTime dateTime = new DateTime(date);
		//获取这一年的开始时间 和结束时间
		DateTime startTime = dateTime.withMonthOfYear(1)
				.withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0)
				.withSecondOfMinute(0).withMillisOfSecond(0);
		DateTime endTime = startTime.plusYears(1);
		return new TimeRegion(startTime,endTime);
	}
	
	/**
	 * 
	 * @param startDate
	 * @return
	 */
	public static TimeRegion processTotalDate(Date startDate){
		return new TimeRegion(new DateTime(startDate), new DateTime());
	}

	/**
	 * 应该使用processTotalDate(date)
	 * @param date
	 * @return 
	 */
	@Deprecated 
	private static TimeRegion processTotalTime(Date date) {
		return new TimeRegion(new DateTime(2015,1,1,0,0,0), new DateTime());
	}
	
	public enum TimeGrain{
		/**
		 * Minute 分钟
		 */
		Minute(-1),
		/**
		 * Hour 分钟
		 */
		Hour(0),
		/**
		 * Hour 分钟
		 */
		Day(1),
		/**
		 * Hour 分钟
		 */
		Week(5),
		/**
		 * Hour 分钟
		 */
		Month(2),
		/**
		 * Hour 分钟
		 */
		Year(3),
		/**
		 * Hour 分钟
		 */
		Total(4),
		/**
		季度
		 */
		Quarter(6),
		/**
		 * Hour 分钟
		 */
		None(7),
		/**
		 * Hour 分钟
		 */
		LastMonthCompare(10),
		/**
		 * Hour 分钟
		 */
		LastYearCompare(11);
		
		private int code;
		private TimeGrain(int code){
			this.code = code;
		}
		
		public static TimeGrain getByCode(int code){
			TimeGrain timeGrain = null;
			
			TimeGrain[] values = values();
			for (TimeGrain tg : values) {
				if(tg.getCode() == code){
					timeGrain = tg;
					break;
				}
			}
			
			return timeGrain;
		}

		public int getCode() {
			return code;
		}
		
	}
	
	public static class TimeRegion {
		private DateTime startTime;
		private DateTime endTime;
		
		public TimeRegion() {
			super();
		}

		public TimeRegion(DateTime startTime, DateTime endTime) {
			super();
			this.startTime = startTime;
			this.endTime = endTime;
		}

		public DateTime getStartTime() {
			return startTime;
		}

		public void setStartTime(DateTime startTime) {
			this.startTime = startTime;
		}

		public DateTime getEndTime() {
			return endTime;
		}

		public void setEndTime(DateTime endTime) {
			this.endTime = endTime;
		}
		
	}
	
	public static void main(String[] args) {
	}

}

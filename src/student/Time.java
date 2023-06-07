package student;

public class Time {
	private int hour;
    private int minute;
    private int second;

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Time(String currentTime) {
        String[] time = currentTime.split(":");
        hour = Integer.parseInt(time[0]);
        minute = Integer.parseInt(time[1]);
        second = Integer.parseInt(time[2]);
    }

    public String getCurrentTime(){
        return hour + ":" + minute + ":" + second;
    }

    public void oneSecondPassed(){
        if(second == 0) {
        	if(minute == 0) {
        		if(hour == 0) {
        			return;
        		}
        		hour--;
        		minute = 60;
        	}
        	minute--;
        	second = 60;
        }
        second--;
    }
}

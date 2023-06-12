package student;

public class ExamTimer extends Thread {
    private int minutes;
    private boolean stopTimer;
    private ComputerizedExamController instance;
    private int totalSeconds;

    public ExamTimer(int minutes, ComputerizedExamController instance) {
        this.minutes = minutes;
        this.stopTimer = false;
        this.instance = instance;
    }

    @Override
    public void run() {
        totalSeconds = minutes * 60;

        while (totalSeconds >= 0 && !stopTimer) {
            int hours = totalSeconds / 3600;
            int minutesRemaining = (totalSeconds % 3600) / 60;
            int secondsRemaining = totalSeconds % 60;

            String timeString = String.format("%02d:%02d:%02d", hours, minutesRemaining, secondsRemaining);
            setTime(timeString);
            //System.out.println(timeString);

            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            totalSeconds--;
        }
        if(totalSeconds <= 0){
            timesUp();
        }

        System.out.println("Time's up!");
    }

    public void stopTimer(){
        stopTimer = true;
    }

    public void updateTimer(int minutes){
        totalSeconds = minutes * 60;
    }

    //method to set the time in the timer label
    private void setTime(String time){
        instance.setUpdateExamTimer(time);
    }
    //When the timer runs out submit the exam
    private void timesUp(){
        instance.endOfTimerSubmit();
    }
}

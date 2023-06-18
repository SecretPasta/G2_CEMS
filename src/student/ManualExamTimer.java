package student;

public class ManualExamTimer extends Thread {
    private int minutes;
    private boolean stopTimer;
    private ManualExamController instance;
    private int totalSeconds;
    private int elapsedSeconds;

    /**
     * Constructs a new ManualExamTimer with the specified duration in minutes and the associated controller instance.
     *
     * @param minutes  the duration of the timer in minutes
     * @param instance the ManualExamController instance associated with the timer
     */
    public ManualExamTimer(int minutes, ManualExamController instance) {
        this.minutes = minutes;
        this.stopTimer = false;
        this.instance = instance;
        this.elapsedSeconds = 0;
    }


    /**
     * Starts the timer and runs the timer logic.
     * This method is executed when the thread is started.
     * It updates the timer every second and checks if the timer has reached zero.
     * If the timer reaches zero, it calls the {@link #timesUp()} method.
     */
    @Override
    public void run() {
        totalSeconds = minutes * 60;

        while (totalSeconds >= 0 && !stopTimer) {
            int hours = totalSeconds / 3600;
            int minutesRemaining = (totalSeconds % 3600) / 60;
            int secondsRemaining = totalSeconds % 60;

            String timeString = String.format("%02d:%02d:%02d", hours, minutesRemaining, secondsRemaining);
            setTime(timeString);

            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            totalSeconds--;
            elapsedSeconds++;
        }
        if (totalSeconds <= 0) {
            timesUp();
        }

        //System.out.println("Time's up!");
    }


    /**
     * Stops the timer.
     */
    public void stopTimer() {
        stopTimer = true;
    }

    /**
     * Updates the timer with the specified duration in minutes.
     *
     * @param minutes The new duration of the timer in minutes.
     */
    public void updateTimer(int minutes) {
        totalSeconds = minutes * 60;
    }

    /**
     * Returns the elapsed time in minutes.
     *
     * @return The elapsed time in minutes.
     */
    public int getElapsedMinutes() {
        return (int) (elapsedSeconds / 60);
    }

    /**
     * Sets the time in the timer label of the associated ComputerizedExamController instance.
     *
     * @param time The time to be set in the timer label.
     */
    private void setTime(String time) {
        instance.setUpdateExamTimer(time);
    }

    /**
     * Handles the logic when the timer runs out.
     */
    private void timesUp() {
        instance.endOfTimerSubmit();
    }

}


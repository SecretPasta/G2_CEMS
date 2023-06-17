package student;

public class ExamTimer extends Thread {
    private int minutes;
    private boolean stopTimer;
    private ComputerizedExamController instance;
    private int totalSeconds;
    private int elapsedSeconds;

    /**
     * Constructs a new ExamTimer object with the specified duration in minutes and a reference to the ComputerizedExamController instance.
     * The ExamTimer is used to track the elapsed time during an exam and update the timer label accordingly.
     *
     * @param minutes   The duration of the exam in minutes.
     * @param instance  The ComputerizedExamController instance to update the timer label.
     */
    public ExamTimer(int minutes, ComputerizedExamController instance) {
        this.minutes = minutes;
        this.stopTimer = false;
        this.instance = instance;
        this.elapsedSeconds = 0;
    }

    /**
     * Runs the timer countdown.
     * The method calculates the total duration in seconds based on the provided minutes
     * and updates the timer label every second until the timer reaches zero or the stopTimer flag is set.
     * If the timer reaches zero, it calls the timesUp method to handle the event.
     * This method is intended to be executed in a separate thread.
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

        System.out.println("Time's up!");
    }


    /**
     * Stops the timer.
     * Sets the stopTimer flag to true.
     */
    public void stopTimer() {
        stopTimer = true;
    }

    /**
     * Updates the timer with the specified minutes.
     *
     * @param minutes The new duration in minutes.
     */
    public void updateTimer(int minutes) {
        totalSeconds = minutes * 60;
    }

    /**
     * Returns the elapsed minutes.
     *
     * @return The elapsed minutes.
     */
    public int getElapsedMinutes() {
        return (int) (elapsedSeconds / 60);
    }


    /**
     * Sets the time in the timer label.
     * Calls the setUpdateExamTimer method of the current instance of the ManualExamController.
     *
     * @param time The time to be set in the timer label.
     */
    private void setTime(String time) {
        instance.setUpdateExamTimer(time);
    }


    /**
     * Handles the situation when the exam timer runs out.
     * Calls the endOfTimerSubmit method of the current instance of the ManualExamController.
     */
    private void timesUp() {
        instance.endOfTimerSubmit();
    }

}


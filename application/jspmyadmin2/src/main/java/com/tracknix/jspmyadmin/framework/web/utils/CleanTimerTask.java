package com.tracknix.jspmyadmin.framework.web.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Yugandhar Gangu
 */
class CleanTimerTask extends TimerTask {

    private final static long _ONCE_PER_DAY = 1000 * 60 * 60 * 24;

    private final static int _ONE_AM = 1;
    private final static int _ZERO_MINUTES = 0;

    @Override
    public void run() {
        // clean
    }

    /**
     * @return Date
     */
    private static Date getTomorrowMorning2AM() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, _ONE_AM);
        calendar.set(Calendar.MINUTE, _ZERO_MINUTES);
        return calendar.getTime();
    }

    /**
     *
     */
    public static void startClean() {
        CleanTimerTask task = new CleanTimerTask();
        task.run();
        Timer timer = new Timer();
        timer.schedule(task, getTomorrowMorning2AM(), _ONCE_PER_DAY);
    }

}

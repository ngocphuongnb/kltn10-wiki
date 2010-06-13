/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myquartz;

import impl.IndexViwikiJob;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author VinhPham
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SchedulerException, ParseException {
        SchedulerFactory schedFac = new StdSchedulerFactory();
        Scheduler sched = schedFac.getScheduler();
        sched.start();
        JobDetail jobDetail = new JobDetail("myJob", null, IndexViwikiJob.class);
        //CronTrigger trigger = new CronTrigger("my trigger", null, "30 9 2 ? * SUN");//s m h dom m dow
        Trigger trigger = TriggerUtils.makeHourlyTrigger();
        trigger.setStartTime(new Date());
        trigger.setName("my trigger");
        sched.scheduleJob(jobDetail, trigger);
    }

}

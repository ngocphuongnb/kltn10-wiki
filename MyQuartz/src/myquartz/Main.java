/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myquartz;

import index.*;
import impl.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
        if (args.length > 0) {
            if (args[0].equals("help") || args[0].equals("?")) {
                System.out.println("?, help: Show help");
                System.out.println("wiki: index only data wiki");
                System.out.println("raovat: index only data raovat");
                System.out.println("music: index only data music");
                System.out.println("video: index only data video");
                System.out.println("news: index only data news");
                System.out.println("bookmark: index only data bookmark");
                System.out.println("all: index all data");
                System.out.println("saveimage: Save local image");
            }

            if (args[0].equals("wiki")) {
                SchedulerFactory schedFac = new StdSchedulerFactory();
                Scheduler sched = schedFac.getScheduler();
                sched.start();
                JobDetail jobDetail = new JobDetail("wikiJob", null, IndexViwikiJob.class);
                Date d = new Date();
                Calendar cl = Calendar.getInstance();
                cl.setTime(d);
                cl.add(Calendar.SECOND, 10);
                String fd = String.format("%d %d %d ? * *", cl.get(Calendar.SECOND), cl.get(Calendar.MINUTE),
                        cl.get(Calendar.HOUR_OF_DAY));
                CronTrigger trigger = new CronTrigger("wiki trigger", null, fd);//s m h dom m dow
                sched.scheduleJob(jobDetail, trigger);
            }

            if (args[0].equals("raovat")) {
                SchedulerFactory schedFac = new StdSchedulerFactory();
                Scheduler sched = schedFac.getScheduler();
                sched.start();
                JobDetail jobDetail = new JobDetail("raovatJob", null, IndexRaovatJob.class);
                Date d = new Date();
                Calendar cl = Calendar.getInstance();
                cl.setTime(d);
                cl.add(Calendar.SECOND, 10);
                String fd = String.format("%d %d %d ? * %s", cl.get(Calendar.SECOND), cl.get(Calendar.MINUTE),
                        cl.get(Calendar.HOUR_OF_DAY), cl.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US));
                CronTrigger trigger = new CronTrigger("raovat trigger", null, fd);//s m h dom m dow
                sched.scheduleJob(jobDetail, trigger);
            }

            if (args[0].equals("music")) {
                SchedulerFactory schedFac = new StdSchedulerFactory();
                Scheduler sched = schedFac.getScheduler();
                sched.start();
                JobDetail jobDetail = new JobDetail("musicJob", null, IndexMusicJob.class);
                Date d = new Date();
                Calendar cl = Calendar.getInstance();
                cl.setTime(d);
                cl.add(Calendar.SECOND, 10);
                String fd = String.format("%d %d %d ? * %s", cl.get(Calendar.SECOND), cl.get(Calendar.MINUTE),
                        cl.get(Calendar.HOUR_OF_DAY), cl.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US));
                CronTrigger trigger = new CronTrigger("music trigger", null, fd);//s m h dom m dow
                sched.scheduleJob(jobDetail, trigger);
            }

            if (args[0].equals("video")) {
                SchedulerFactory schedFac = new StdSchedulerFactory();
                Scheduler sched = schedFac.getScheduler();
                sched.start();
                JobDetail jobDetail = new JobDetail("videoJob", null, IndexVideoJob.class);
                Date d = new Date();
                Calendar cl = Calendar.getInstance();
                cl.setTime(d);
                cl.add(Calendar.SECOND, 10);
                String fd = String.format("%d %d %d ? * %s", cl.get(Calendar.SECOND), cl.get(Calendar.MINUTE),
                        cl.get(Calendar.HOUR_OF_DAY), cl.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US));
                CronTrigger trigger = new CronTrigger("video trigger", null, fd);//s m h dom m dow
                sched.scheduleJob(jobDetail, trigger);
            }

            if (args[0].equals("news")) {
                SchedulerFactory schedFac = new StdSchedulerFactory();
                Scheduler sched = schedFac.getScheduler();
                sched.start();
                JobDetail jobDetail = new JobDetail("newsJob", null, IndexNewsJob.class);
                Date d = new Date();
                Calendar cl = Calendar.getInstance();
                cl.setTime(d);
                cl.add(Calendar.SECOND, 10);
                String fd = String.format("%d %d %d ? * %s", cl.get(Calendar.SECOND), cl.get(Calendar.MINUTE),
                        cl.get(Calendar.HOUR_OF_DAY), cl.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US));
                CronTrigger trigger = new CronTrigger("news trigger", null, fd);//s m h dom m dow
                sched.scheduleJob(jobDetail, trigger);
            }


            if (args[0].equals("bookmark")) {
                SchedulerFactory schedFac = new StdSchedulerFactory();
                Scheduler sched = schedFac.getScheduler();
                sched.start();
                JobDetail jobDetail = new JobDetail("bookmarkJob", null, IndexBookmarkJob.class);
                Date d = new Date();
                Calendar cl = Calendar.getInstance();
                cl.setTime(d);
                cl.add(Calendar.SECOND, 10);
                String fd = String.format("%d %d %d ? * %s", cl.get(Calendar.SECOND), cl.get(Calendar.MINUTE),
                        cl.get(Calendar.HOUR_OF_DAY), cl.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US));
                CronTrigger trigger = new CronTrigger("bookmark trigger", null, fd);//s m h dom m dow
                sched.scheduleJob(jobDetail, trigger);
            }

            if (args[0].equals("all")) {
                SchedulerFactory schedFac = new StdSchedulerFactory();
                Scheduler sched = schedFac.getScheduler();
                sched.start();
                JobDetail jobDetail = new JobDetail("allJob", null, IndexAllJob.class);
                Date d = new Date();
                Calendar cl = Calendar.getInstance();
                cl.setTime(d);
                cl.add(Calendar.SECOND, 10);
                String fd = String.format("%d %d %d ? * %s", cl.get(Calendar.SECOND), cl.get(Calendar.MINUTE),
                        cl.get(Calendar.HOUR_OF_DAY), cl.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US));
                CronTrigger trigger = new CronTrigger("all trigger", null, fd);//s m h dom m dow
                sched.scheduleJob(jobDetail, trigger);
            }

            if (args[0].equals("image")) {
                SchedulerFactory schedFac = new StdSchedulerFactory();
                Scheduler sched = schedFac.getScheduler();
                sched.start();
                JobDetail jobDetail = new JobDetail("imageJob", null, IndexImageJob.class);
                Date d = new Date();
                Calendar cl = Calendar.getInstance();
                cl.setTime(d);
                cl.add(Calendar.SECOND, 10);
                String fd = String.format("%d %d %d ? * %s", cl.get(Calendar.SECOND), cl.get(Calendar.MINUTE),
                        cl.get(Calendar.HOUR_OF_DAY), cl.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US));
                CronTrigger trigger = new CronTrigger("image trigger", null, fd);//s m h dom m dow
                sched.scheduleJob(jobDetail, trigger);
            }
            if (args[0].equals("saveimage")) {
                SchedulerFactory schedFac = new StdSchedulerFactory();
                Scheduler sched = schedFac.getScheduler();
                sched.start();
                JobDetail jobDetail = new JobDetail("saveimageJob", null, SaveImageJob.class);
                Date d = new Date();
                Calendar cl = Calendar.getInstance();
                cl.setTime(d);
                cl.add(Calendar.SECOND, 10);
                String fd = String.format("%d %d %d ? * %s", cl.get(Calendar.SECOND), cl.get(Calendar.MINUTE),
                        cl.get(Calendar.HOUR_OF_DAY), cl.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US));
                CronTrigger trigger = new CronTrigger("saveimage trigger", null, fd);//s m h dom m dow
                sched.scheduleJob(jobDetail, trigger);
            }
        } else {
            System.out.println("error syntax");
            System.out.println("type help or ? to show help menu");
        }
    }
}

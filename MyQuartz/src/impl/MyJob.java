/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author VinhPham
 */
public class MyJob implements Job {

    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("Hello! VinhPham");
    }

}

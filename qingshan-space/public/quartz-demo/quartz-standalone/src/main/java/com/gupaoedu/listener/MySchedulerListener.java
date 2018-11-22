package com.gupaoedu.listener;

import org.quartz.*;

/**
 * @Author: qingshan
 * @Date: 2018/9/19 15:26
 * @Description: 咕泡学院，只为更好的你
 */
public class MySchedulerListener implements SchedulerListener {
    
    public void jobScheduled(Trigger trigger) {
        String jobName = trigger.getJobKey().getName();
        System.out.println(jobName + " has been scheduled");
    }

    public void jobUnscheduled(TriggerKey triggerKey) {
        System.out.println(triggerKey + " is being unscheduled");
    }

    public void triggerFinalized(Trigger trigger) {
        System.out.println("Trigger is finished for " + trigger.getJobKey().getName());
    }

    public void triggerPaused(TriggerKey triggerKey) {
        System.out.println(triggerKey + " is being paused");
    }
    
    public void triggersPaused(String triggerGroup) {
        System.out.println("trigger group "+triggerGroup + " is being paused");
    }
    
    public void triggerResumed(TriggerKey triggerKey) {
        System.out.println(triggerKey + " is being resumed");
    }

    public void triggersResumed(String triggerGroup) {
        System.out.println("trigger group "+triggerGroup + " is being resumed");
    }

    
    public void jobAdded(JobDetail jobDetail) {
        System.out.println(jobDetail.getKey()+" is added");
    }
    
    public void jobDeleted(JobKey jobKey) {
        System.out.println(jobKey+" is deleted");
    }
    
    public void jobPaused(JobKey jobKey) {
        System.out.println(jobKey+" is paused");
    }

    public void jobsPaused(String jobGroup) {
        System.out.println("job group "+jobGroup+" is paused");
    }

    public void jobResumed(JobKey jobKey) {
        System.out.println(jobKey+" is resumed");
    }

    public void jobsResumed(String jobGroup) {
        System.out.println("job group "+jobGroup+" is resumed");
    }

    public void schedulerError(String msg, SchedulerException cause) {
        System.out.println(msg + cause.getUnderlyingException().getStackTrace());
    }
    
    public void schedulerInStandbyMode() {
        System.out.println("scheduler is in standby mode");
    }

    public void schedulerStarted() {
        System.out.println("scheduler has been started");
    }

    
    public void schedulerStarting() {
        System.out.println("scheduler is being started");
    }

    public void schedulerShutdown() {
        System.out.println("scheduler has been shutdown");
    }

    public void schedulerShuttingdown() {
        System.out.println("scheduler is being shutdown");
    }

    public void schedulingDataCleared() {
        System.out.println("scheduler has cleared all data");
    }
}

package com.kennethrdzg.smalltalk.scheduler;

import java.util.logging.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler{
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    private Logger LOGGER = Logger.getLogger(BatchScheduler.class.getName());

    @Scheduled(cron = "0 0 0 * * *")
    public void backupUsers(){
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("startAt", System.currentTimeMillis())
            .toJobParameters();
        try{
            jobLauncher.run(job, jobParameters);
        } catch(JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e){
            LOGGER.severe("Backup Job Failed");
        }
        LOGGER.info("Backup Job Completed Succesfully");
        return;
    }
}

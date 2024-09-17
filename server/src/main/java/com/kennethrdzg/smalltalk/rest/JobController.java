package com.kennethrdzg.smalltalk.rest;

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
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/jobs")
public class JobController{
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    private Logger LOGGER = Logger.getLogger(JobController.class.getName());

    @Scheduled(cron = "0 0 0 * * *")
    @GetMapping
    public ResponseEntity<String> backupUsers(){
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("startAt", System.currentTimeMillis())
            .toJobParameters();
        try{
            jobLauncher.run(job, jobParameters);
        } catch(JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e){
            LOGGER.severe("Backup Job Failed");
            return ResponseEntity.badRequest().body("Backup Job Failed");
        }
        LOGGER.info("Backup Job Completed Succesfully");
        return ResponseEntity.ok("Backup Job was Succesful");
    }
}
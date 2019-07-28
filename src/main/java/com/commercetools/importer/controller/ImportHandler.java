package com.commercetools.importer.controller;

import com.commercetools.importer.service.KafkaWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.LogManager.getLogger;

@RestController
@RequestMapping(value = "/products")
public class ImportHandler {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobBuilder jobBuilder;

    @Autowired
    Step step;

    private static Logger log = getLogger(ImportHandler.class);

    @GetMapping(value = "/import")
    public ResponseEntity<String> importProducts() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobExecution jobExecution = getJobExecution();
        return ResponseEntity.ok().body("Products Import Started...");
    }

    private JobExecution getJobExecution() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(Instant.now().getEpochSecond()));
        JobParameters parameters = new JobParameters(maps);

        log.info("Starting the job...");

        return jobLauncher.run(jobBuilder.start(step).build(), parameters);
    }
}

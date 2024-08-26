package br.stapassoli.batch.student;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.batch.core.Job;


@RestController
@RequestMapping(value = "/students")
@RequiredArgsConstructor
public class StudentController {

    private final JobLauncher jobLauncher;
    private final Job studentsJob;

    @PostMapping
    public void importCsvToDBJob() {
        JobParameters jobParameter = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(studentsJob, jobParameter);
        } catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 |JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }

    }


}

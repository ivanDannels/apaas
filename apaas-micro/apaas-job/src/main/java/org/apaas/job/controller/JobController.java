package org.apaas.job.controller;

import org.apaas.core.web.controller.ReactiveBaseController;
import org.apaas.job.entity.JobEntity;
import org.apaas.job.service.JobService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController extends ReactiveBaseController<JobEntity, Long, JobService> {

    public JobController(JobService service) {
        super(service);
    }
}
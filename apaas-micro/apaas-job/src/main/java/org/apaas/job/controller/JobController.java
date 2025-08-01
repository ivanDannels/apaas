package org.apaas.job.controller;

import org.apaas.job.entity.JobEntity;
import org.apaas.job.service.JobService;
import org.apaas.job.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public Mono<AjaxResult<Flux<JobEntity>>> getAllJobs() {
        return Mono.just(AjaxResult.success(jobService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<AjaxResult<Mono<JobEntity>>> getJobById(@PathVariable Long id) {
        return Mono.just(AjaxResult.success(jobService.findById(id)));
    }

    @PostMapping
    public Mono<AjaxResult<Mono<JobEntity>>> createJob(@RequestBody JobEntity job) {
        return Mono.just(AjaxResult.success(jobService.save(job)));
    }

    @DeleteMapping("/{id}")
    public Mono<AjaxResult<Void>> deleteJob(@PathVariable Long id) {
        return jobService.deleteById(id)
                .then(Mono.just(AjaxResult.success()));
    }
}
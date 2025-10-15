package org.apaas.job.application.assembler;

import org.apaas.application.assembler.BaseAssembler;
import org.apaas.job.application.dto.JobDTO;
import org.apaas.job.domain.model.JobEntity;
import org.mapstruct.Mapper;

/**
 * @author ivan
 */
@Mapper
public interface JobAssembler extends BaseAssembler<JobEntity, JobDTO, Long> {

}
package org.apaas.auth.application.assembler;

import org.apaas.application.assembler.BaseAssembler;
import org.apaas.auth.application.dto.UserDTO;
import org.apaas.auth.domain.entity.User;
import org.mapstruct.Mapper;

/**
 * @author ivan
 */
@Mapper
public interface UserAssembler extends BaseAssembler<User, UserDTO, Long> {

}

package athl.logistics.athl_logistics.service.mappers.Impl;

import athl.logistics.athl_logistics.models.UserValidationCode;
import athl.logistics.athl_logistics.service.dto.UserValidationCodeDTO;
import athl.logistics.athl_logistics.service.mappers.UserValidationCodeMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidationCodeMapperImpl implements UserValidationCodeMapper {
    private final ModelMapper modelMapper;

    @Override
    public UserValidationCodeDTO fromEntity(UserValidationCode entity) {
        return modelMapper.map(entity, UserValidationCodeDTO.class);
    }

    @Override
    public UserValidationCode toEntity(UserValidationCodeDTO dto) {
        return modelMapper.map(dto, UserValidationCode.class);
    }

}

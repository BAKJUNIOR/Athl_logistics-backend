package athl.logistics.athl_logistics.service.mappers.Impl;

import athl.logistics.athl_logistics.models.User;
import athl.logistics.athl_logistics.service.dto.UserDTO;
import athl.logistics.athl_logistics.service.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final ModelMapper modelMapper;

    @Override
    public UserDTO fromEntity(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    @Override
    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}

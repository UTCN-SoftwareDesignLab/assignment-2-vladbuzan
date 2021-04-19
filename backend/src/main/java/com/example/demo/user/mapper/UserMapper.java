package com.example.demo.user.mapper;

import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.dto.UserMinimalDto;
import com.example.demo.user.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDto userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "role", expression = "java(user.getRole().getName().name())")
    })
    UserListDto userListDtoFromUser(User user);

    @AfterMapping
    default void populateRole(User user, @MappingTarget UserListDto userListDTO) {
        userListDTO.setRole(user.getRole().getName().name());
    }
}

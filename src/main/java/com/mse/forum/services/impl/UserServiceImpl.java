package com.mse.forum.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mse.forum.dto.UserDTO;
import com.mse.forum.mappers.UsersMapper;
import com.mse.forum.persistance.UsersRepository;
import com.mse.forum.persistance.entities.Roles;
import com.mse.forum.persistance.entities.UserEntity;
import com.mse.forum.services.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UsersRepository usersRepository;

	private UsersMapper mapper;

	@Override
	public boolean saveUser(UserDTO user) {
		UserEntity entity = mapper.toEntity(user);
		entity.setRole(Roles.USER);
		usersRepository.save(entity);
		return true;
	}

	@Override
	public UserDTO getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Optional<UserEntity> userEntityOptional = usersRepository.findByUsername(userDetails.getUsername());

		UserEntity userEntity = userEntityOptional.get();
		UserDTO dto = mapper.toDto(userEntity);
		return dto;
	}

	@Override
	public List<UserDTO> getUsers() {
		return usersRepository.findAll()
				.stream()
				.map(entity -> mapper.toDto(entity))
				.collect(Collectors.toList());
	}


	@Override
	public boolean updateUser(UserDTO user) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Optional<UserEntity> userEntityOptional = usersRepository.findByUsername(userDetails.getUsername());

		UserEntity userEntity = userEntityOptional.get();

        // user id and current authenticate user id are equal - can change profile data
		if (userEntity.getId() == user.getId()) {
            userEntity.setTheme(user.getTheme());
            userEntity.setEmail(user.getEmail());
            userEntity.setUsername(user.getUsername());
        }

		usersRepository.save(userEntity);
		return true;
	}

	@Override
	public boolean updateUserRole(UserDTO user) {
		Optional<UserEntity> userEntityOptional = usersRepository.findById(user.getId());
		UserEntity userEntity = userEntityOptional.get();
		userEntity.setRole(user.getRole());
		usersRepository.save(userEntity);
		return true;
	}

	@Override
    public boolean deleteUser(Long userId){
	    usersRepository.deleteById(userId);
	    return true;
    }

	@Override
	public List<UserDTO> searchUsersByName(String username) {
		return usersRepository.findAllByUsernameContaining(username)
				.stream()
				.map(entity -> mapper.toDto(entity))
				.collect(Collectors.toList());
	}
}
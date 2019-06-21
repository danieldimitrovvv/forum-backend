package com.mse.forum.services;

import com.mse.forum.dto.UserDTO;
import java.util.List;

public interface UserService {

	boolean saveUser(UserDTO user);

	UserDTO getUser();

	List<UserDTO> getUsers();

	boolean updateUser(UserDTO user);

    boolean updateUserRole(UserDTO user);

    boolean deleteUser(Long userId);

	List<UserDTO> searchUsersByName(String username);
}

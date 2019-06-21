package com.mse.forum.contollers;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.mse.forum.dto.UserDTO;
import com.mse.forum.services.UserService;

import lombok.AllArgsConstructor;

import javax.annotation.security.RolesAllowed;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

	private UserService usersService;

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void create(@RequestBody UserDTO user) {
		usersService.saveUser(user);
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(@RequestBody UserDTO user) {
		usersService.updateUser(user);
	}

	@ResponseBody
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	@RolesAllowed(value = { "ADMIN", "MODERATOR" })
	public void updateRole(@RequestBody UserDTO user) {
		usersService.updateUserRole(user);
	}

	@ResponseBody
	@RequestMapping(value = "/search-users/{username}", method = RequestMethod.GET)
	@RolesAllowed(value = { "ADMIN", "MODERATOR", "USER" })
	public List<UserDTO> searchUser(@PathVariable String username) {
		return usersService.searchUsersByName(username);
	}

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public UserDTO getUser() {
		return usersService.getUser();
	}

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(@PathVariable Long id) {
        usersService.deleteUser(id);
    }

	@RequestMapping(path = "/me", method = RequestMethod.GET)
	public String getMe() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails.getUsername());
		return "OK";
	}

	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<UserDTO> getUsers() {
		return usersService.getUsers();
	}
}

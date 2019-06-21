package com.mse.forum.dto;

import com.mse.forum.persistance.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

	private String username;

	private String password;

	private String email;
	
	private String theme;

	private Roles role;

}
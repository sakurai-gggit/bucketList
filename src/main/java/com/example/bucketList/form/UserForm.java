package com.example.bucketList.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import com.example.bucketList.validation.constraints.PasswordEquals;

import lombok.Data;

@Data
@PasswordEquals
public class UserForm {
	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@Size(max = 20)
	private String password;

	@NotEmpty
	@Size(max = 20)
	private String passwordConfirmation;

}
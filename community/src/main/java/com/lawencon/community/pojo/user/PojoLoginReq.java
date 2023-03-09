package com.lawencon.community.pojo.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class PojoLoginReq {
	
	@NotBlank(message = "Email Tidak Boleh Kosong")
	@Email (message = "Format Email Tidak Sesuai")
	private String email;
    private String passwordUser;

    @NotBlank (message = "Password Tidak Boleh Kosong")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getPasswordUser() {
		return passwordUser;
	}

	public void setPasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}

}

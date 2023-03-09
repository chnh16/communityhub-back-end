package com.lawencon.community.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.User;
import com.lawencon.community.pojo.user.PojoLoginReq;
import com.lawencon.community.pojo.user.PojoLoginRes;
import com.lawencon.community.service.JwtService;
import com.lawencon.community.service.UserService;


@RestController
@RequestMapping("users")
public class UserController {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public UserController (UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	
	@PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid final PojoLoginReq user){
        final Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPasswordUser());

        authenticationManager.authenticate(auth);
        final Optional<User> userOptional = userService.getByEmail(user.getEmail());

        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 1);

        final Map<String, Object> claims = new HashMap<>();
        claims.put("exp", cal.getTime());
        claims.put("id", userOptional.get().getId());

        final PojoLoginRes loginRes = new PojoLoginRes();
        
        loginRes.setToken(jwtService.generateJwt(claims));
        loginRes.setIdUser(Long.valueOf(userOptional.get().getId()) );
        loginRes.setRoleCode(userOptional.get().getRole().getRoleCode());
        loginRes.setFullName(userOptional.get().getProfile().getFullName());

        return new ResponseEntity<>(loginRes, HttpStatus.OK);
    }
	
	

}

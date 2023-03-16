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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.user.PojoLoginReq;
import com.lawencon.community.pojo.user.PojoLoginRes;
import com.lawencon.community.pojo.user.PojoProfileUpdateReq;
import com.lawencon.community.pojo.user.PojoUserGetUserProfileRes;
import com.lawencon.community.pojo.user.PojoUserRegisterReq;
import com.lawencon.community.pojo.user.PojoVerificationCodeUpdateReq;
import com.lawencon.community.pojo.user.PojoVerificationUpdateReq;
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
        loginRes.setIdUser(userOptional.get().getId()) ;
        loginRes.setRoleCode(userOptional.get().getRole().getRoleCode());
        loginRes.setFullName(userOptional.get().getProfile().getFullName());

        return new ResponseEntity<>(loginRes, HttpStatus.OK);
    }
	
	@PostMapping("regis")
	public ResponseEntity<PojoInsertRes> register(@Valid @RequestBody final PojoUserRegisterReq data){
		final PojoInsertRes res = userService.register(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@PostMapping("regis-admin")
	public ResponseEntity<PojoInsertRes> createAdmin(@Valid @RequestBody final PojoUserRegisterReq data){
		final PojoInsertRes res = userService.createAdmin(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> verification(@RequestBody PojoVerificationUpdateReq data) {
		final PojoUpdateRes res = userService.verification(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("code")
	public ResponseEntity<PojoUpdateRes> updateCodeVerification(@RequestBody PojoVerificationCodeUpdateReq data) {
		final PojoUpdateRes res = userService.updateCodeVerification(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("user-profile")
	public ResponseEntity<PojoUserGetUserProfileRes> getUserProfile(){
		final PojoUserGetUserProfileRes res = userService.getUserProfile();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("update-profile")
	public ResponseEntity<PojoUpdateRes> updateProfile(@RequestBody PojoProfileUpdateReq data) {
		final PojoUpdateRes res = userService.updateProfile(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	

}

package com.spring.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.ElectionEntity;
import com.spring.entity.UserCredentialsEntity;
import com.spring.json.Election;
import com.spring.json.LoginResponse;
import com.spring.json.UserCredentials;
import com.spring.service.AdminService;

@RestController
@RequestMapping("/evs")
public class AdminController {

	@Autowired
	private AdminService adminService;

	private String sessionId = null;

	@PostMapping("/election")
	public ElectionEntity addElectionDetails(@RequestBody Election election, ElectionEntity electionEntity) {
		BeanUtils.copyProperties(election, electionEntity);
		adminService.addElection(electionEntity);
		return electionEntity;
	}

	@GetMapping("/election")
	public List<ElectionEntity> getElectionDetails() {
		return adminService.getElectionDetails();
	}

	@PostMapping("/admin/login")
	public LoginResponse loginAdmin(@RequestBody UserCredentials userCredentials,
			UserCredentialsEntity userCredentialsEntity) {
		LoginResponse res = adminService.adminLogin(userCredentials);
		sessionId = res.getSessionId();
		return res;
	}

	@GetMapping("/admin/logout")
	public LoginResponse logoutAdmin() {
		return adminService.adminLogout(sessionId);
	}

}
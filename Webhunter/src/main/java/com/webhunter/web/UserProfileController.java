package com.webhunter.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webhunter.domain.UserProfile;

@RequestMapping("/userprofiles")
@Controller
@RooWebScaffold(path = "userprofiles", formBackingObject = UserProfile.class)
public class UserProfileController {}

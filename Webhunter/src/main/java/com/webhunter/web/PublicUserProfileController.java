package com.webhunter.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webhunter.domain.UserProfile;

@RequestMapping("/publicuserprofile/**")
@Controller
@RooWebScaffold(path = "publicuserprofile", formBackingObject = UserProfile.class)
public class PublicUserProfileController {
	
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid UserProfile userProfile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userProfile);
            return "publicuserprofile/create";
        }
        uiModel.asMap().clear();
        userProfileService.saveUserProfile(userProfile);
        return "redirect:/publicuserprofile/" + encodeUrlPathSegment(userProfile.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new UserProfile());
        return "publicuserprofile/create";
    }
  
	
}

package com.webhunter.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.webhunter.domain.UserProfile;
import com.webhunter.domain.UserRole;
import com.webhunter.service.UserProfileService;

@RequestMapping("/publicuserprofile/**")
@Controller
@RooWebJson(jsonObject = UserProfile.class)
@RooWebScaffold(path = "publicuserprofile", formBackingObject = UserProfile.class)
public class PublicUserProfileController {
    @Autowired
    UserProfileService userProfileService;
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody UserProfile userProfile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
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
  

    

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("userprofile", userProfileService.findUserProfile(id));
        uiModel.addAttribute("itemId", id);
        return "publicuserprofile/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("userprofiles", userProfileService.findUserProfileEntries(firstResult, sizeNo));
            float nrOfPages = (float) userProfileService.countAllUserProfiles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("userprofiles", userProfileService.findAllUserProfiles());
        }
        return "publicuserprofile/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UserProfile userProfile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userProfile);
            return "publicuserprofile/update";
        }
        uiModel.asMap().clear();
        userProfileService.updateUserProfile(userProfile);
        return "redirect:/publicuserprofile/" + encodeUrlPathSegment(userProfile.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, userProfileService.findUserProfile(id));
        return "publicuserprofile/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UserProfile userProfile = userProfileService.findUserProfile(id);
        userProfileService.deleteUserProfile(userProfile);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/publicuserprofile";
    }
    
    void populateEditForm(Model uiModel, UserProfile userProfile) {
        uiModel.addAttribute("userProfile", userProfile);
        uiModel.addAttribute("userroles", UserRole.findAllUserRoles());
    }
    
    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    

}

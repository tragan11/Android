// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.webhunter.domain;

import com.webhunter.domain.UserProfile;
import com.webhunter.domain.UserRole;
import java.util.Set;

privileged aspect UserProfile_Roo_JavaBean {
    
    public String UserProfile.getUserName() {
        return this.userName;
    }
    
    public void UserProfile.setUserName(String userName) {
        this.userName = userName;
    }
    
    public String UserProfile.getPassword() {
        return this.password;
    }
    
    public void UserProfile.setPassword(String password) {
        this.password = password;
    }
    
    public String UserProfile.getConfirmPassword() {
        return this.confirmPassword;
    }
    
    public void UserProfile.setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public Set<UserRole> UserProfile.getRoles() {
        return this.roles;
    }
    
    public void UserProfile.setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
    
}

// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.webhunter.domain;

import com.webhunter.domain.UserProfile;
import com.webhunter.domain.UserProfileDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect UserProfileDataOnDemand_Roo_DataOnDemand {
    
    declare @type: UserProfileDataOnDemand: @Component;
    
    private Random UserProfileDataOnDemand.rnd = new SecureRandom();
    
    private List<UserProfile> UserProfileDataOnDemand.data;
    
    public UserProfile UserProfileDataOnDemand.getNewTransientUserProfile(int index) {
        UserProfile obj = new UserProfile();
        setPassword(obj, index);
        setUserName(obj, index);
        return obj;
    }
    
    public void UserProfileDataOnDemand.setPassword(UserProfile obj, int index) {
        String password = "password_" + index;
        obj.setPassword(password);
    }
    
    public void UserProfileDataOnDemand.setUserName(UserProfile obj, int index) {
        String userName = "userName_" + index;
        obj.setUserName(userName);
    }
    
    public UserProfile UserProfileDataOnDemand.getSpecificUserProfile(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        UserProfile obj = data.get(index);
        Long id = obj.getId();
        return UserProfile.findUserProfile(id);
    }
    
    public UserProfile UserProfileDataOnDemand.getRandomUserProfile() {
        init();
        UserProfile obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return UserProfile.findUserProfile(id);
    }
    
    public boolean UserProfileDataOnDemand.modifyUserProfile(UserProfile obj) {
        return false;
    }
    
    public void UserProfileDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = UserProfile.findUserProfileEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'UserProfile' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<UserProfile>();
        for (int i = 0; i < 10; i++) {
            UserProfile obj = getNewTransientUserProfile(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}

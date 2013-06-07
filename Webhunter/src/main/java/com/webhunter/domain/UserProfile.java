package com.webhunter.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UserProfile {

    @NotNull
    @Size(min = 8)
    private String userName;

    @NotNull
    @Size(min = 8)
    private String password;

    @Transient
    @NotNull
    @Size(min = 8)
    private String confirmPassword;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private Set<UserRole> roles = new HashSet<UserRole>();
}

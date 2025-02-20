package com.mcoder.kclothing.cart.data;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mcoder.kclothing.cart.model.Role;
import com.mcoder.kclothing.cart.model.User;
import com.mcoder.kclothing.cart.repository.RoleRepository;
import com.mcoder.kclothing.cart.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent>{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        
        createDefaultRolesIfNotExist(defaultRoles);
        createDefaultAdminIfNotExist();
    }


      private void createDefaultAdminIfNotExist(){
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for(int i = 1; i <= 2; i++) {
            String defaultEmail = "admin"+i+"@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setUserName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user " + i + " created successfully");
        }
    }

    private void createDefaultRolesIfNotExist(Set<String> roles){
        roles.stream()
             .filter(role -> roleRepository.findByName(role).isEmpty())
             .map(Role :: new).forEach(roleRepository :: save);
    }
}

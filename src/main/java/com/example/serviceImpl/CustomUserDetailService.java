package com.example.serviceImpl;

import com.example.entity.User;
import com.example.enums.UserType;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = null;
        User existingData = userRepository.findByName(name);

        if (existingData != null) {
            user = existingData;
        }
        if (existingData == null) {
            System.out.println("User not Found");
        }
        if (user.getName() == null) {
            throw new UsernameNotFoundException("UserName not Found");
        }
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
//        UserDetails user1 = org.springframework.security.core.userdetails.User.withUsername(user.getName()).password(user.getPassword()).authorities("Admin").build();
//        return user1;
    }
}
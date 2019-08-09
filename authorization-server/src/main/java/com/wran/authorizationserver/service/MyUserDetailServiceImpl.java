package com.wran.authorizationserver.service;

import com.wran.authorizationserver.model.oauth.AuthUserDetails;
import com.wran.authorizationserver.model.oauth.User;
import com.wran.authorizationserver.model.dto.UserCreateDto;
import com.wran.authorizationserver.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
public class MyUserDetailServiceImpl implements UserDetailsService, MyUserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private RoleService roleService;
    @Bean
    protected PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userDetailRepository.findByUsername(username);

        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username or password is wrong"));

        UserDetails userDetails = new AuthUserDetails(optionalUser.get());
        new AccountStatusUserDetailsChecker().check(userDetails);
        return userDetails;
    }

    public User register(UserCreateDto userDto){
        if(userDetailRepository.existsByUsername(userDto.getUsername()))
            return null;

        User user = new User(userDto.getUsername(), passwordEncoder().encode(userDto.getPassword()), userDto.getEmail(),
                true, true, true, true,
                Arrays.asList(roleService.findByName("user")));
        return userDetailRepository.save(user);
    }

    public List<User> saveAll(List<User> users) {
        return userDetailRepository.saveAll(users);
    }
}

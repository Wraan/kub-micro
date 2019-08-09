package com.wran.authorizationserver.service;

import com.wran.authorizationserver.model.oauth.Role;
import com.wran.authorizationserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String name){
        return roleRepository.findByName("ROLE_" + name);
    }

    public List<Role> saveAll(List<Role> roles){
        return roleRepository.saveAll(roles);
    }
}

package com.patrickreplogle.backendtemplate.services;

import com.patrickreplogle.backendtemplate.models.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findRoleById(long id);

    Role save(Role role);

    Role findByName(String name);

    Role update(long id, Role role);

    void delete(long id);
}

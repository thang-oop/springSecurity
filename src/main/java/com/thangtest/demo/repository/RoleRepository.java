package com.thangtest.demo.repository;

import com.thangtest.demo.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Integer> {

    Role findByName(String name);
}

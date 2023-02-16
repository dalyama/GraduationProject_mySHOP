package com.myShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.Roles;

@Repository
public interface RolesRepo  extends JpaRepository<Roles, Integer>{

}

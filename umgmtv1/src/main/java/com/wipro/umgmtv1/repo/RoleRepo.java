package com.wipro.umgmtv1.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.umgmtv1.entity.RoleMenu;

@Repository
public interface RoleRepo extends JpaRepository<RoleMenu, Integer> {

	List<RoleMenu> findByRoleId(int roleId);
	boolean existsByRoleIdAndMenuNameAndShowMenu(int roleId, String menuName, String showMenu);


}

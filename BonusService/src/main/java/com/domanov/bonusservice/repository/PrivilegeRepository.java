package com.domanov.bonusservice.repository;

import com.domanov.bonusservice.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    @Query("SELECT t FROM Privilege t WHERE t.username = ?1")
    Privilege findByUsername(String usr);
}

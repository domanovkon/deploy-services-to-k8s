package com.domanov.bonusservice.repository;

import com.domanov.bonusservice.model.PrivilegeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrivilegeHistoryRepository extends JpaRepository<PrivilegeHistory, Integer> {

    @Query("SELECT t FROM PrivilegeHistory t WHERE t.privilege.id = ?1")
    List<PrivilegeHistory> findByPrivilegeId(Integer id);
}

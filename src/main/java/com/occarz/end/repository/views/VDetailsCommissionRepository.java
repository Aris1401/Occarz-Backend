package com.occarz.end.repository.views;

import com.occarz.end.views.VDetailsCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VDetailsCommissionRepository extends JpaRepository<VDetailsCommission, Integer> {
    @Query("SELECT a FROM VDetailsCommission a WHERE a.annee = ?1")
    public List<VDetailsCommission> findByAnnee(int annee);
}

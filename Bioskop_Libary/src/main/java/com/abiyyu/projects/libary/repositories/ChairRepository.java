package com.abiyyu.projects.libary.repositories;

import com.abiyyu.projects.libary.entity.Chair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChairRepository extends JpaRepository<Chair,Long> {

    @Query("SELECT c FROM Chair c INNER JOIN Order o ON c.order.id = o.id WHERE o.schedule.id = :idSchedule ORDER BY chairNumber")
    public List<Chair> getChairByScheduleId(@Param("idSchedule") Long id);

    @Query("SELECT c FROM Chair c WHERE c.order.id =?1")
    List<Chair> getChairByOrder(Long idOrder);
}

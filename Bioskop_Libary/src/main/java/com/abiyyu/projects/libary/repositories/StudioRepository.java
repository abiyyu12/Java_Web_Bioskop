package com.abiyyu.projects.libary.repositories;

import com.abiyyu.projects.libary.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio,Long> {
}
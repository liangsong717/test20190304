package com.fineway.scaffolding.dao;


import com.fineway.scaffolding.entity.Hello;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloDao extends PagingAndSortingRepository<Hello, Long> {
    Page<Hello> findByName(@Param("name") String name, Pageable pageable);
}

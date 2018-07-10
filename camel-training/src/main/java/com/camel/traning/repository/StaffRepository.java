package com.camel.traning.repository;

import com.camel.traning.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Long, Staff> {
}

package com.example.security.part;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutsourcedRepository extends JpaRepository<Outsourced, Long> {
}

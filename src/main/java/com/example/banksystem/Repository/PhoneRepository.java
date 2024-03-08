package com.example.banksystem.Repository;

import com.example.banksystem.Entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}

package com.example.banksystem.Repository;

import com.example.banksystem.Entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

}

package com.example.anken_manager.repository;

import com.example.anken_manager.entity.Anken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnkenRepository extends JpaRepository<Anken, Long> {
}
package com.example.dao;

import com.example.entity.Shorter;
import org.springframework.data.repository.CrudRepository;

public interface ShorterDao extends CrudRepository<Shorter, Long> {
    Shorter findByHash(String hash);
}

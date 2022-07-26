package com.wallinson.clients.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallinson.clients.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

}

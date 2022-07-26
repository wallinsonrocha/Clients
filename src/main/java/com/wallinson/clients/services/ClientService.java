package com.wallinson.clients.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wallinson.clients.entities.Client;
import com.wallinson.clients.exception.DatabaseException;
import com.wallinson.clients.exception.NotFoundException;
import com.wallinson.clients.repository.ClientRepository;


@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<Client> findAllPaged(PageRequest pageRequest){
		return repository.findAll(pageRequest);
	} 
	
	@Transactional(readOnly = true)
	public Client findById(Long id){
		Optional<Client> obj = repository.findById(id);
		return obj.orElseThrow(()-> new NotFoundException("Id not found " + id));	
	}
	
	@Transactional
	public Client insert(Client obj) {
		return repository.save(obj);
	}
	
	@Transactional
	public Client update(Long id, Client obj) {
		try {
			Client entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);					
		} catch (EntityNotFoundException e){
            throw new NotFoundException("Id not found " + id);
        }
	}
	
	@Transactional
	public void delete(Long id) {
		try{
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new NotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
	}
	
	private void updateData(Client entity, Client obj){
	    entity.setName(obj.getName());
	    entity.setCpf(obj.getCpf());
	    entity.setBirthDate(obj.getBirthDate());
	    entity.setIncome(obj.getIncome());
	    entity.setChildren(obj.getChildren());   
	}
}

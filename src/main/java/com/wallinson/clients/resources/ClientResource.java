package com.wallinson.clients.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wallinson.clients.entities.Client;
import com.wallinson.clients.services.ClientService;

@Controller
@RequestMapping(value = "/client")
public class ClientResource {
	
	@Autowired
	private ClientService service;

	@GetMapping("/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id)  {
	    Client obj = service.findById(id);
	    return ResponseEntity.ok().body(obj);
	} 
	
	@GetMapping
	public ResponseEntity<Page<Client>> findAll(
		    @RequestParam(value = "page", defaultValue = "0") Integer page,
		    @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
		    @RequestParam(value = "direction", defaultValue = "DESC") String direction,
		    @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
		){
		        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		        Page<Client> list = service.findAllPaged(pageRequest);
		        return ResponseEntity.ok().body(list);
		    }
	
	@PostMapping
	public ResponseEntity<Client> input(@RequestBody Client obj){
		obj = service.insert(obj);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	        .buildAndExpand(obj.getId()).toUri();
	    return ResponseEntity.created(uri).body(obj);		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client obj){
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
	    service.delete(id);
	    return ResponseEntity.noContent().build();
	}
	
}

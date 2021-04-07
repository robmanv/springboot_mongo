package com.robmanv.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robmanv.workshopmongo.domain.User;
import com.robmanv.workshopmongo.dto.UserDTO;
import com.robmanv.workshopmongo.repository.UserRepository;
import com.robmanv.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired         //Injetar automaticamente pelo Spring
	private UserRepository repo; 
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public User findById(String id) {
		Optional<User> user = repo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPosts());
	}
	
	public void delete (String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public User update(User obj) {
		try {
			User entity = findById(obj.getId());
			
			updateData(entity, obj);
			
			return repo.save(entity);
		} catch (ObjectNotFoundException e) {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
	
	}
	
	private void updateData(User entity, User obj) {
		// Atualizar os dados do entity com o que tem no obj

		entity.setId(obj.getId());
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		
	}
}

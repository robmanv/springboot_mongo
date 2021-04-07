package com.robmanv.workshopmongo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robmanv.workshopmongo.domain.Post;
import com.robmanv.workshopmongo.dto.AuthorDTO;
import com.robmanv.workshopmongo.dto.CommentDTO;
import com.robmanv.workshopmongo.dto.PostDTO;
import com.robmanv.workshopmongo.repository.PostRepository;
import com.robmanv.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired         //Injetar automaticamente pelo Spring
	private PostRepository repo; 
	
	public List<Post> findAll() {
		return repo.findAll();
	}
	
	public Post findById(String id) {
		Optional<Post> user = repo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public Post insert(Post obj) {
		return repo.insert(obj);
	}
	
	public Post fromDTO(PostDTO objDto) {
		return new Post(objDto.getId(), objDto.getDate(), objDto.getTitle(), objDto.getBody(), new AuthorDTO(), new ArrayList<CommentDTO>());
	}
	
	public void delete (String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public Post update(Post obj) {
		try {
			Post entity = findById(obj.getId());
			
			updateData(entity, obj);
			
			return repo.save(entity);
		} catch (ObjectNotFoundException e) {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
	
	}
	
	private void updateData(Post entity, Post obj) {
		// Atualizar os dados do entity com o que tem no obj

		entity.setId(obj.getId());
		entity.setDate(obj.getDate());
		entity.setTitle(obj.getTitle());
		entity.setBody(obj.getBody());
		entity.setAuthor(obj.getAuthor());
		
	}
	
	public List<Post> findByTitle(String text) {
		//return repo.findByTitleContainingIgnoreCase(text);
		return repo.procurarTitulo(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return repo.fullSearch(text, minDate, maxDate);
	}
	
}

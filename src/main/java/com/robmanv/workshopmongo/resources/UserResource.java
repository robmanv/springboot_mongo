package com.robmanv.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.robmanv.workshopmongo.domain.Post;
import com.robmanv.workshopmongo.domain.User;
import com.robmanv.workshopmongo.dto.UserDTO;
import com.robmanv.workshopmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired                 // IMPORTANTE: Use pra ligar as CAMADAS
	private UserService service;
	
	@RequestMapping(method = RequestMethod.GET)  // Ou @GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {                  // O ResponseEntity serve pra encapsular as requisições HTTP, poderia testar direto com list
//		User maria = new User("1", "Maria Silva", "maria@gmail.com");
//		User alex = new User("2", "Alex Green", "alex@gmail.com");

		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x.getId(), x.getName(), x.getEmail(), x.getPosts())).collect(Collectors.toList()); //Posso colocar o objeto list de User ou remapear como fiz

		//return list;
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User user = service.findById(id);
		
		return ResponseEntity.ok().body(new UserDTO(user));
	}

	@RequestMapping(value="/{id}/posts", method=RequestMethod.GET)
	public ResponseEntity<List<Post>> getUserPosts(@PathVariable String id) {
		User user = service.findById(id);
		UserDTO userDto = new UserDTO(user);
		
		return ResponseEntity.ok().body(userDto.getPosts());
	}

	@PostMapping // OU o de cima com .POST
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {
		User user = service.fromDTO(objDto);
		user = service.insert(user);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri(); // obter caminho do recurso que inserimos
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value="/{id}") // OU o de cima com .DELETE
	public ResponseEntity<Void> insert(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// Vai capturar do REST a chamada PUT
	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@PathVariable String id, @RequestBody UserDTO userDto) {
		User user = service.fromDTO(userDto);
		user.setId(id);
		user = service.update(user);
		return ResponseEntity.noContent().build();
	}

}

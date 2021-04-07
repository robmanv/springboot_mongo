package com.robmanv.workshopmongo.resources;

import java.net.URI;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.robmanv.workshopmongo.domain.Post;
import com.robmanv.workshopmongo.domain.User;
import com.robmanv.workshopmongo.dto.PostDTO;
import com.robmanv.workshopmongo.resources.util.URL;
import com.robmanv.workshopmongo.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {
	
	@Autowired                 // IMPORTANTE: Use pra ligar as CAMADAS
	private PostService service;
	
	@RequestMapping(method = RequestMethod.GET)  // Ou @GetMapping
	public ResponseEntity<List<PostDTO>> findAll() {                  // O ResponseEntity serve pra encapsular as requisições HTTP, poderia testar direto com list
//		User maria = new User("1", "Maria Silva", "maria@gmail.com");
//		User alex = new User("2", "Alex Green", "alex@gmail.com");

		List<Post> list = service.findAll();
		List<PostDTO> listDto = list.stream().map(x -> new PostDTO(x.getId(), x.getDate(), x.getTitle(), x.getBody(), x.getAuthor(), x.getComments())).collect(Collectors.toList()); //Posso colocar o objeto list de User ou remapear como fiz

		//return list;
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<PostDTO> findById(@PathVariable String id) {
		Post post = service.findById(id);
		
		return ResponseEntity.ok().body(new PostDTO(post));
	}

	@PostMapping // OU o de cima com .POST
	public ResponseEntity<Void> insert(@RequestBody PostDTO objDto) {
		Post post = service.fromDTO(objDto);
		post = service.insert(post);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri(); // obter caminho do recurso que inserimos
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value="/{id}") // OU o de cima com .DELETE
	public ResponseEntity<Void> insert(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// Vai capturar do REST a chamada PUT
	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@PathVariable String id, @RequestBody PostDTO postDto) {
		Post post = service.fromDTO(postDto);
		post.setId(id);
		post = service.update(post);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/titlesearch", method = RequestMethod.GET)  // Ou @GetMapping
	public ResponseEntity<List<PostDTO>> findByTitle(@RequestParam(value="titulo", defaultValue="") String text) {                  // O ResponseEntity serve pra encapsular as requisições HTTP, poderia testar direto com list

		List<Post> list = service.findByTitle(URL.decodeParam(text));
		List<PostDTO> listDto = list.stream().map(x -> new PostDTO(x.getId(), x.getDate(), x.getTitle(), x.getBody(), x.getAuthor(), x.getComments())).collect(Collectors.toList()); //Posso colocar o objeto list de User ou remapear como fiz

		//return list;
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/fullsearch", method = RequestMethod.GET)  // Ou @GetMapping
	public ResponseEntity<List<PostDTO>> fullSearch(
			@RequestParam(value="text", defaultValue="") String text,
			@RequestParam(value="minDate", defaultValue="") String minDate,
			@RequestParam(value="maxDate", defaultValue="") String maxDate) {                  // O ResponseEntity serve pra encapsular as requisições HTTP, poderia testar direto com list

		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date(0L));
		List<Post> list = service.fullSearch(URL.decodeParam(text), min, max);
		List<PostDTO> listDto = list.stream().map(x -> new PostDTO(x.getId(), x.getDate(), x.getTitle(), x.getBody(), x.getAuthor(), x.getComments())).collect(Collectors.toList()); //Posso colocar o objeto list de User ou remapear como fiz

		//return list;
		return ResponseEntity.ok().body(listDto);
	}
	


}

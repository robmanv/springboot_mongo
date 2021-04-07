package com.robmanv.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.robmanv.workshopmongo.domain.Post;
import com.robmanv.workshopmongo.domain.User;
import com.robmanv.workshopmongo.dto.AuthorDTO;
import com.robmanv.workshopmongo.dto.CommentDTO;
import com.robmanv.workshopmongo.dto.UserDTO;
import com.robmanv.workshopmongo.repository.PostRepository;
import com.robmanv.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com", new ArrayList<Post>());
		User alex = new User(null, "Alex Green", "alex@gmail.com",  new ArrayList<Post>());
		User bob = new User(null, "Bob Grey", "bob@gmail.com",  new ArrayList<Post>());
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		SimpleDateFormat sdsf = new SimpleDateFormat("yyyy-MM-dd");
		sdsf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		postRepository.deleteAll();

		Post p1  = new Post(null, sdsf.parse("2018-03-21"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria), new ArrayList<CommentDTO>());
		Post p2  = new Post(null, sdsf.parse("2018-03-23"), "Bom dia", "Acordei feliz hoje", new AuthorDTO(maria), new ArrayList<CommentDTO>());
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdsf.parse("2018-03-21"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", sdsf.parse("2018-03-22"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdsf.parse("2018-03-23"), new AuthorDTO(alex));
		
		p1.getComments().addAll(Arrays.asList(c1, c2));
		p2.getComments().add(c3);
		
		postRepository.saveAll(Arrays.asList(p1, p2));
		
		maria.getPosts().addAll(Arrays.asList(p1, p2));
		
		userRepository.save(maria);		
	}
	

}

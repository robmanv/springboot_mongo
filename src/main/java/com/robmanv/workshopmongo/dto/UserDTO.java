package com.robmanv.workshopmongo.dto;

import java.io.Serializable;
import java.util.List;

import com.robmanv.workshopmongo.domain.Post;
import com.robmanv.workshopmongo.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String email;
	private List<Post> posts;

	public UserDTO(User obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
		posts = obj.getPosts();
	}
	
}

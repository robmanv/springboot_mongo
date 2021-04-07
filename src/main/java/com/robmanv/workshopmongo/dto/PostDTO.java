package com.robmanv.workshopmongo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.robmanv.workshopmongo.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private Date date;
	private String title;
	private String body;
	private AuthorDTO author;
	private List<CommentDTO> comments;

	public PostDTO(Post obj) {
		id = obj.getId();
		date = obj.getDate();
		title = obj.getTitle();
		body = obj.getBody();
		author = obj.getAuthor();
		comments = obj.getComments();
	}
	
}

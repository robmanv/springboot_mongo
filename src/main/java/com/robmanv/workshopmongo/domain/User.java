package com.robmanv.workshopmongo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Document(collection="user")             // Informa que é uma coleção do MongoDB, se não informar o collection=user ele pega o nome da classe com letra minuscula
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	private String id;

	private String name;
	private String email;
	
	@DBRef(lazy=false)     // lazy pra não carregar automaticamente a lista de posts dos usuários, evita tráfego desnecessário na rede, só vão ser carregados quando fizer o getPosts
	private List<Post> posts = new ArrayList<>();  // Quando associo objetos devo iniciar as coleções
}

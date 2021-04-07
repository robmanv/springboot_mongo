package com.robmanv.workshopmongo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.robmanv.workshopmongo.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{
	
	// Vide documentação oficial do Spring sobre Query Methods!!
	
	// Atenção: O QUERY METHODS é sempre alimentado nessa INTERFACE (REPOSITORIES)
	
	List<Post> findByTitleContainingIgnoreCase(String text);
	
	// o ?0 pra pegar o 1o parametro do metodo, senão ?1 seria o 2o parametro e i pra ignorar CaseSensitive, veja documentação oficial com as opções abaixo, só COPY COLA
	@Query("{ 'title': { $regex: ?0, $options: 'i' } } ")
	List<Post> procurarTitulo(String text);

	@Query("{ $and: [ { date: {$gte: ?1} },  { date: {$lte: ?2} }, { $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } }, { 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Post> fullSearch(String text, Date minDate, Date maxDate);
	
}

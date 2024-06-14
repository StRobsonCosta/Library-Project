package br.com.gestao.livro.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import br.com.gestao.livro.model.Book;

@Repository
public interface BookRepository extends CassandraRepository<Book, UUID> {

}

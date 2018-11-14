package io.ymq.elasticsearch.repository;

import io.ymq.elasticsearch.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-11-02 14:47
 **/
public interface BookRepository extends ElasticsearchRepository<Book, String> {

    Page<Book> findByAuthor(String author, Pageable pageable);

    List<Book> findByTitle(String title);

    Book save(Book book);
}
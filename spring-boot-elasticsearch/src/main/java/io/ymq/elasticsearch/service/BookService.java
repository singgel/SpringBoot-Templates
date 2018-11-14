package io.ymq.elasticsearch.service;

import io.ymq.elasticsearch.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-11-02 14:48
 **/
public interface BookService {

    Book save(Book book);

    void delete(Book book);

    Book findOne(String id);

    Iterable<Book> findAll();

    Page<Book> findByAuthor(String author, PageRequest pageRequest);

    List<Book> findByTitle(String title);
}

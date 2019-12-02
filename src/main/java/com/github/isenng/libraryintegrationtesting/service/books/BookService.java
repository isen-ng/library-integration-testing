package com.github.isenng.libraryintegrationtesting.service.books;

import com.github.isenng.libraryintegrationtesting.dal.entities.Book;
import com.github.isenng.libraryintegrationtesting.dal.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    private BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book Insert(String title, String author, String text) {
        return repository.save(new Book(title, author, text));
    }

    public Optional<Book> Get(long id) {
        return repository.findById(id);
    }
}

package com.esen.bookstore.service;

import com.esen.bookstore.model.Book;
import com.esen.bookstore.model.BookStore;
import com.esen.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookStoreService bookStoreService;

    public void save(Book book) {
        bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot find book with id" + id);
        }

        var book = bookRepository.findById(id).get();
        bookStoreService.removeBookFromInventories(book);
        bookRepository.delete(book);
    }

    public Book update(Long id, String title, String author, String publisher, Double price) {
        if (Stream.of(title, author, publisher, price).allMatch(Objects::isNull)) {
            throw new IllegalArgumentException("At least one input is required");
        }

        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot find book with id" + id);
        }

        var book = bookRepository.findById(id).get();

        if (title != null) book.setTitle(title);
        if (author != null) book.setAuthor(author);
        if (publisher != null) book.setPublisher(publisher);
        if (price != null) book.setPrice(price);

        return bookRepository.save(book);
    }

    public Map<String, Double> findPrices(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot find book with id" + id);
        }

        var book = bookRepository.findById(id).get();

        return bookStoreService.findAll().stream()
                .collect(Collectors.toMap(BookStore::getLocation, bookStore -> bookStore.getPriceModifier() * book.getPrice()));
    }

    public List<Book> filterBook(String title, String publisher, String author) {
        return bookRepository.findAll().stream().filter(book ->
                (title == null || (book.getTitle().equals(title)) && (publisher == null || book.getTitle().equals(publisher)) && (author == null || book.getTitle().equals(author)))).toList();

    }
}

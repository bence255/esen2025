package com.esen.bookstore.service;

import com.esen.bookstore.model.Book;
import com.esen.bookstore.model.BookStore;
import com.esen.bookstore.repository.BookStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookStoreService {
    private final BookStoreRepository bookStoreRepository;

    public void removeBookFromInventories(Book book) {
        bookStoreRepository.findAll()
                .forEach(bookStore -> {
                    bookStore.getInventory().remove(book);
                    bookStoreRepository.save(bookStore);
                });

    }
    public List<BookStore> findAll(){
        return bookStoreRepository.findAll();
    }

    public void delete(Long id) {
        if (!bookStoreRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot find book with id" + id);
        }

        var bookStore = bookStoreRepository.findById(id).get();
        bookStoreRepository.delete(bookStore);
    }
}

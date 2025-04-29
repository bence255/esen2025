package com.esen.bookstore.service;

import com.esen.bookstore.model.Book;
import com.esen.bookstore.repository.BookStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

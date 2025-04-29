package com.esen.bookstore.shell;

import com.esen.bookstore.model.BookStore;
import com.esen.bookstore.service.BookStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("Bookstore related commands")
@RequiredArgsConstructor
public class BookStoreHandler {
    private final BookStoreService bookStoreService;

    @ShellMethod(value = "List all bookstores", key = "list bookstores")
    public String findAll(){
        return bookStoreService.findAll()
                .stream()
                .peek(bookStore -> bookStore.setInventory(null))
                .map(BookStore::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(value = "Deletes a bookstore by ID", key = "delete bookstore")
    public void deleteBookStore(Long id){
        bookStoreService.delete(id);
    }
}

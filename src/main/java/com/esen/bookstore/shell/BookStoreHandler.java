package com.esen.bookstore.shell;

import com.esen.bookstore.model.Book;
import com.esen.bookstore.model.BookStore;
import com.esen.bookstore.service.BookStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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

    @ShellMethod(value = "Creates a bookstore", key = "create bookstore")
    public void createBookStore(String location, Double priceModifier ,Double moneyInCashRegister){
        bookStoreService.save(BookStore.builder()
                .location(location)
                .priceModifier(priceModifier)
                .moneyInCashRegister(moneyInCashRegister)
                .build());
    }
    @ShellMethod(value = "Updates a bookstore", key = "update bookstore")
    public String updateBookStore(
            @ShellOption(defaultValue = ShellOption.NULL) Long id,
            @ShellOption(defaultValue = ShellOption.NULL) String location,
            @ShellOption(defaultValue = ShellOption.NULL) Double priceModifier,
            @ShellOption(defaultValue = ShellOption.NULL) Double moneyInCashRegister
    ){
        return bookStoreService.update(id, location, priceModifier, moneyInCashRegister).toString();
    }
}

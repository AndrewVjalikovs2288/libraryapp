package lv.autentica.library.controllers;

import lv.autentica.library.dto.BookDto;
import lv.autentica.library.entities.Author;
import lv.autentica.library.entities.Book;
import lv.autentica.library.services.AuthorService;
import lv.autentica.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @GetMapping(path = {"/", "/books"})
    public String getAllBooksView(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }

    @GetMapping("books/{id}")
    private String getBookView(Model model, @PathVariable("id") Long id) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "book_view";
    }

    @DeleteMapping("books/{id}")
    private String deleteBook(@PathVariable("id") Long id, RedirectAttributes attributes) {
        bookService.deleteBook(id);
        attributes.addFlashAttribute("msg", "Book deleted successfully");
        return "redirect:/books/";
    }

    @GetMapping("books/new")
    private String getNewBookForm(Model model, @ModelAttribute("book") BookDto book) {
        List<Author> allAuthors = authorService.getAllAuthors();
        model.addAttribute("allAuthors", allAuthors);
        model.addAttribute("book", book);
        return "secured/book_edit";
    }

    @GetMapping("books/{id}/edit")
    private String getBookEditForm(Model model, @PathVariable("id") Long id) {
        List<Author> allAuthors = authorService.getAllAuthors();
        model.addAttribute("allAuthors", allAuthors);

        Book book = bookService.getBook(id);
        model.addAttribute("book", bookService.getDtoFromEntity(book));
        return "secured/book_edit";
    }

    @PostMapping("books/save")
    private String saveBook(@ModelAttribute BookDto book) throws Exception {
        Book savedBook = bookService.saveBook(book);
        return "redirect:/books/" + savedBook.getId();
    }

    @GetMapping("/books/search")
    public String getBookSearchForm(Model model, @ModelAttribute("book") BookDto book) {
        model.addAttribute("book", book);
        List<Author> allAuthors = authorService.getAllAuthors();
        model.addAttribute("allAuthors", allAuthors);
        return "book_search";
    }

    @PostMapping("books/search")
    private String seacrhBooks(Model model, @ModelAttribute BookDto book) throws Exception {
        Collection<Book> foundBooks = bookService.searchBooks(book);
        model.addAttribute("books", foundBooks);
        return "index";
    }
}
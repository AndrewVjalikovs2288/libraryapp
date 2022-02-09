package lv.autentica.library.controllers;

import lv.autentica.library.entities.Author;
import lv.autentica.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public String getAllAuthorsView(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "secured/authors/author_list";
    }

    @GetMapping("/{id}")
    private String getAuthorView(Model model, @PathVariable("id") Long id) {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        return "secured/authors/author_view";
    }

    @DeleteMapping("/{id}")
    private String deleteAuthor(Model model, @PathVariable("id") Long id, RedirectAttributes attributes) {
        authorService.deleteAuthor(id);
        attributes.addFlashAttribute("msg", "Author deleted successfully");
        return "redirect:/authors/";
    }

    @GetMapping("/new")
    private String getNewAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "secured/authors/author_edit";
    }

    @GetMapping("/{id}/edit")
    private String getEditAuthorForm(Model model, @PathVariable("id") Long id) {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        return "secured/authors/author_edit";
    }

    @PostMapping("/save")
    private String saveAuthor(Model model, @ModelAttribute Author author) {
        authorService.saveAuthor(author);
        return "redirect:/authors/" + author.getId();
    }

}

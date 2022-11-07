package lv.autentica.library.formatter;

import lv.autentica.library.entities.Author;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class AuthorFormatter implements Formatter<Author> {
    @Override
    public Author parse(String id, Locale locale) throws ParseException {
        return id == null ? new Author() : new Author(Long.parseLong(id));
    }

    @Override
    public String print(Author author, Locale locale) {
        return author.getId().toString();
    }
}


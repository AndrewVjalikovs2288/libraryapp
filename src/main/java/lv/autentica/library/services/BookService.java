package lv.autentica.library.services;

import lv.autentica.library.dto.BookDto;
import lv.autentica.library.entities.Book;
import lv.autentica.library.enums.Languages;
import lv.autentica.library.repositories.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(BookDto bookDto) throws Exception {
        Book book = getEntityFromDto(bookDto);

        bookRepository.save(book);
        return book;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book getEntityFromDto(BookDto dto) throws Exception {
        Book entity = dto.getId() == null ? new Book() : bookRepository.getById(dto.getId());
        BeanUtils.copyProperties(dto, entity);

        entity.setLanguage(Languages.valueOf(dto.getLanguage()));

        if(dto.getPublishingDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date publishingDate = formatter.parse(dto.getPublishingDate());
            entity.setYear(publishingDate.getYear());
        }
        return entity;
    }

    public BookDto getDtoFromEntity(Book entity) {
        BookDto dto = new BookDto();
        BeanUtils.copyProperties(entity, dto);

        if(entity.getYear() != null) {
            Date date = new GregorianCalendar(entity.getYear().intValue(), 0, 1).getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dto.setPublishingDate(formatter.format(date));
        }

        dto.setLanguage(entity.getLanguage().name());
        return dto;
    }

    public Collection<Book> searchBooks(BookDto book) {
        List<Long> authors = CollectionUtils.isEmpty(book.getAuthors())
                ? null
                : book.getAuthors().stream().map(a -> a.getId()).collect(Collectors.toList());
        Collection<Book> results = bookRepository.searchBooks2(book.getTitle(),
                book.getISBN(),
                book.getSummary(),
                Languages.valueOf(book.getLanguage()),
                authors);
        return results;
    }
}
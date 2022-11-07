package lv.autentica.library.dto;

import lv.autentica.library.entities.Author;
import lv.autentica.library.entities.Book;
import lv.autentica.library.enums.Languages;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BookDto {

    private Long id;
    private String title;
    private String summary;
    private String ISBN;
    private String publishingDate;
    private String language;
    private MultipartFile cover;
    private int pageCount;
    private List<Author> authors;

    public BookDto() {
    }

    @Override
    public String toString() {
        return "BookDto{" +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", publishingDate=" + publishingDate +
                ", language='" + language + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id) &&
                Objects.equals(title, bookDto.title) &&
                Objects.equals(ISBN, bookDto.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, ISBN);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public MultipartFile getCover() {
        return cover;
    }

    public void setCover(MultipartFile cover) {
        this.cover = cover;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}

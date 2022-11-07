package lv.autentica.library.repositories;

import lv.autentica.library.entities.Book;
import lv.autentica.library.enums.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleAndYear(String title, int year);

    List<Book> findByTitleContainsOrISBNContainsOrSummaryContainsOrLanguageIsOrAuthorIdsIn(String title,
                                                                                           String ISBN,
                                                                                           String summary,
                                                                                           String language,
                                                                                           List<Long> authorIds);

    @Query(value = "select b.* " +
            " from books b " +
            " join book_authors ba on b.id = ba.book_id " +
            " join authors a on ba.author_id = a.id " +
            " where (:title is null or b.title like %:title%) " +
            " and (:ISBN is null or b.isbn like %:ISBN%) " +
            " and (:summary is null or b.summary like %:summary%) " +
            " and (:language is null or b.language like %:language%) " +
            " and (:authorIds is null or a.id in (:authorIds) )", nativeQuery = true)
    Set<Book> searchBooks(@Param("title") String title,
                          @Param("ISBN") String ISBN,
                          @Param("summary") String summary,
                          @Param("language") String language,
                          @Param("authorIds") List<Long> authorIds);

    @Query("select distinct b from Book b " +
            " join fetch b.authors a " +
            " where (:title is null or b.title like %:title%) " +
            " and (:ISBN is null or b.ISBN like %:ISBN%) " +
            " and (:summary is null or b.summary like %:summary%) " +
            " and (:language is null or b.language = :language) " +
            " and (COALESCE(:authorIds,NULL) is null or a.id in (:authorIds) )")
    List<Book> searchBooks2(@Param("title") String title,
                            @Param("ISBN") String ISBN,
                            @Param("summary") String summary,
                            @Param("language") Languages language,
                            @Param("authorIds") List<Long> authorIds);

}

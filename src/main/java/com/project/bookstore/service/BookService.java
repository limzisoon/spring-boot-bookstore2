package com.project.bookstore.service;

import com.project.bookstore.models.Book;
import com.project.bookstore.payload.request.BookRequestDTO;
import com.project.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    @Autowired
    BookRepository bookRepository;

    /**
     * This method create book.
     *
     * @param bookRequestDTO
     */
    public Optional<Book> createBook(BookRequestDTO bookRequestDTO) throws Exception
    {
        Book book = Book.builder().build();
        BeanUtils.copyProperties(bookRequestDTO, book);
        System.out.println("createBook : " + book.toString());
        bookRepository.save(book);

        return getBook(book.getIsbn());
    }

    /**
     * This method get book by isbn
     *
     * @param isbn
     */
    public Optional<Book> getBook(String isbn) throws Exception
    {
        System.out.println("getBook : " + isbn);
        return bookRepository.getByIsbn(isbn);
    }

    /**
     * This method update book information
     *
     * @param bookRequestDTO
     */
    public Optional<Book> updateBook(BookRequestDTO bookRequestDTO) throws Exception
    {
        Book book = Book.builder().build();
        BeanUtils.copyProperties(bookRequestDTO, book);
        System.out.println("updateBook : " + book.toString());
        bookRepository.save(book);

        return getBook(book.getIsbn());
    }

    /**
     * This method delete book by isbn
     *
     * @param isbn
     */
    public void deleteBook(String isbn) throws Exception
    {
        bookRepository.deleteByIsbn(isbn);
    }

    /**
     * This method find books by Title And Author
     *
     * @param bookRequestDTO
     */
    public List<Book> findBooksByTitleAndAuthor(BookRequestDTO bookRequestDTO) throws Exception
    {
        String title = bookRequestDTO.getTitle()!=null||bookRequestDTO.getTitle()!=""?bookRequestDTO.getTitle():"%";
        String author = bookRequestDTO.getAuthor();
        return bookRepository.findBooksByTitleAndAuthor(title,author);
    }

    /**
     * This method find all books
     *
     */
    public List<Book> findBooks() throws Exception
    {
        return bookRepository.findAll();
    }
}

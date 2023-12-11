package com.project.bookstore.controllers;

import com.project.bookstore.constant.CommonConstant;
import com.project.bookstore.models.Book;
import com.project.bookstore.payload.request.BookRequestDTO;
import com.project.bookstore.payload.response.BookResponseDTO;
import com.project.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping(value = "/addBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST", description = "add book",summary = "add book into bookstore")
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        HttpHeaders header = new HttpHeaders();

        try {
            //check isbn exist
            Optional<Book> book = bookService.getBook(bookRequestDTO.getIsbn());
            if (book.isPresent()) {
                System.out.println(CommonConstant.BUSINESS_ERROR_1);
                header.add("error",CommonConstant.BUSINESS_ERROR_1);
                return new ResponseEntity<>(bookResponseDTO, header,HttpStatus.BAD_REQUEST);
            }

            Optional<Book> createdBook = bookService.createBook(bookRequestDTO);

            if (createdBook.isPresent()) {
                BeanUtils.copyProperties(createdBook.get(), bookResponseDTO);
            }

            return new ResponseEntity<>(bookResponseDTO, HttpStatus.CREATED);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            header.add("error",e.getMessage());
            return new ResponseEntity<>(bookResponseDTO,header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/updateBook/{isbn}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "POST", description = "update book",summary = "update book detail")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable String isbn, @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        HttpHeaders header = new HttpHeaders();
        BookResponseDTO bookResponseDTO = new BookResponseDTO();

        try {
            //load from db
            Optional<Book> book = bookService.getBook(isbn);
            if (!book.isPresent()) {
                System.out.println(CommonConstant.BUSINESS_ERROR_2);
                header.add("error",CommonConstant.BUSINESS_ERROR_2);
                return new ResponseEntity<>(bookResponseDTO, header,HttpStatus.BAD_REQUEST);
            }

            Optional<Book> updatedBook = bookService.updateBook(bookRequestDTO);

            if (updatedBook.isPresent()) {
                BeanUtils.copyProperties(updatedBook.get(), bookResponseDTO);
            }

            return new ResponseEntity<>(bookResponseDTO, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            header.add("error",e.getMessage());
            return new ResponseEntity<>(bookResponseDTO,header,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/deleteBook/{isbn}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("isbn") String isbn) {
        HttpHeaders header = new HttpHeaders();
        try {
            Optional<Book> book = bookService.getBook(isbn);
            if (!book.isPresent()) {
                System.out.println(CommonConstant.BUSINESS_ERROR_3);
                header.add("error",CommonConstant.BUSINESS_ERROR_3);
                return new ResponseEntity<>(null, header,HttpStatus.BAD_REQUEST);
            }

            bookService.deleteBook(isbn);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            header.add("error",e.getMessage());
            return new ResponseEntity<>(null, header,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "POST", description = "find books by title and author",summary = "find book by title and author")
    public ResponseEntity<List<BookResponseDTO>> findBook(@RequestBody BookRequestDTO bookRequestDTO) {
        HttpHeaders header = new HttpHeaders();
        try {
            List<Book> bookList  = bookService.findBooksByTitleAndAuthor(bookRequestDTO);
            List<BookResponseDTO> responseDtoList = new ArrayList<>();
            for(Book b:bookList)
            {
                BookResponseDTO dto = new BookResponseDTO();
                BeanUtils.copyProperties(b, dto);
                responseDtoList.add(dto);
            }
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            header.add("error",e.getMessage());
            return new ResponseEntity<>(null,header,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/books")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "GET", description = "find All book",summary = "find All book from bookstore")
    public ResponseEntity<List<BookResponseDTO>> findBook() {
        try {
            List<Book> bookList  = bookService.findBooks();
            List<BookResponseDTO> responseDtoList = new ArrayList<>();
            for(Book b:bookList)
            {
                BookResponseDTO dto = new BookResponseDTO();
                BeanUtils.copyProperties(b, dto);
                System.out.println(dto.toString());
                responseDtoList.add(dto);
            }
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            HttpHeaders header = new HttpHeaders();
            header.add("error",e.getMessage());
            return new ResponseEntity<>(null,header,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
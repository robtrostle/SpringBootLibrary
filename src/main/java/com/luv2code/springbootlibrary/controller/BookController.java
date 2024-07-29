package com.luv2code.springbootlibrary.controller;

import com.luv2code.springbootlibrary.entity.Book;
import com.luv2code.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;
import com.luv2code.springbootlibrary.service.BookService;
import com.luv2code.springbootlibrary.utils.ExtractJWT;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/books")
public class BookController {

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  //for displaying on our shelf
  @GetMapping("/secure/currentloans")
  public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token)
      throws Exception
  {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    return bookService.currentLoans(userEmail);
  }

  @GetMapping("/secure/currentloans/count")
  public int currentLoansCount(@RequestHeader("Authorization") String token){
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    return bookService.currentLoansCount(userEmail);
  }

  @GetMapping("/secure/ischeckedout/byuser")
  public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token,
      @RequestParam Long bookId) {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    return bookService.checkoutBookByUser(userEmail, bookId);
  }

  @PutMapping("/secure/checkout")
  public Book checkoutBook(@RequestParam Long bookId,
                            @RequestHeader("Authorization") String token) throws Exception {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

    return bookService.checkoutBook(userEmail, bookId);
  }
  @PutMapping("/secure/return")
  public void returnBook(@RequestHeader(value = "Authorization") String token,
      @RequestParam Long bookId) throws Exception {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    bookService.returnBook(userEmail, bookId);
  }

  @PutMapping("/secure/renew/loan")
  public void renewLoan(@RequestHeader(value = "Authorization") String token,
      @RequestParam Long bookId) throws Exception {
    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    bookService.renewLoan(userEmail, bookId);
  }

}
package com.Library.restAPI.controller;

import com.Library.restAPI.exception.*;
import org.springdoc.api.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorMessage> tokenNotFound(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage("Login requite."));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorMessage> wrongPassword(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Wrong password."));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("User not found."));
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorMessage> bookNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Book not found."));
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorMessage> authorNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Author not found."));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorMessage> categoryNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Category not found."));
    }

    @ExceptionHandler(SpecimenNotFoundException.class)
    public ResponseEntity<ErrorMessage> specimenNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Specimen not found."));
    }

    @ExceptionHandler(BorrowNotFoundException.class)
    public ResponseEntity<ErrorMessage> borrowNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Borrow not found."));
    }

    @ExceptionHandler(BorrowHistoryNotFoundException.class)
    public ResponseEntity<ErrorMessage> historyNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Borrow history not found."));
    }

    @ExceptionHandler(DeleteBorrowedException.class)
    public ResponseEntity<ErrorMessage> deleteBorrowed(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Specimen is borrowed, cant delete."));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> badData(DataIntegrityViolationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMostSpecificCause().getMessage()));
    }
}

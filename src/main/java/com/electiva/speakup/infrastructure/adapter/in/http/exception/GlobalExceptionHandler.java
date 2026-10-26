package com.electiva.speakup.infrastructure.adapter.in.http.exception;

import com.electiva.speakup.application.exception.QuestionNotFoundException;
import com.electiva.speakup.domain.exception.InvalidAnswerTypeException;
import com.electiva.speakup.domain.exception.QuestionAlreadyAnsweredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleQuestionNotFound(QuestionNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Question not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(QuestionAlreadyAnsweredException.class)
    public ResponseEntity<ProblemDetail> handleQuestionAlreadyAnswered(QuestionAlreadyAnsweredException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
        problemDetail.setTitle("Question already answered");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(InvalidAnswerTypeException.class)
    public ResponseEntity<ProblemDetail> handleInvalidAnswerType(InvalidAnswerTypeException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Invalid answer type");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}

package com.microservice.articlesservice.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleIntrouvableException extends RuntimeException {

    public ArticleIntrouvableException(String s) {
        super(s);
    }
}

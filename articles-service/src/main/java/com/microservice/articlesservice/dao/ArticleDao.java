package com.microservice.articlesservice.dao;

import com.microservice.articlesservice.model.Article;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, Integer> {

    Article findById(int id);

    List<Article> findByPrixGreaterThan(int prixLimit);
    List<Article> findByNomLike(String recherche);

    @Query("SELECT p FROM Article p WHERE p.prix > :prixLimit")
    List<Article> chercherUnArticleCher(@Param("prixLimit") int prix);

    List<Article> findByOrderByNomAsc();

}

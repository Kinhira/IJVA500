package com.microservice.articlesservice.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.microservice.articlesservice.dao.ArticleDao;
import com.microservice.articlesservice.model.Article;
import com.microservice.articlesservice.web.exceptions.ArticleIntrouvableException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Api(description="API pour les opérations CRUD sur les articles")
public class ArticleController {

    @Autowired
    private ArticleDao articleDao;

    @ApiOperation(value="Récupère la liste des articles (sans prixAchat et marge)")
    @GetMapping(value="/Articles")
    public MappingJacksonValue listeArticles() {
        List<Article> articles = articleDao.findAll();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat", "marge");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue articlesFiltres = new MappingJacksonValue(articles);

        articlesFiltres.setFilters(listDeNosFiltres);
        return articlesFiltres;
    }

    @ApiOperation(value="Récupère un article grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value="/Articles/{id}")
    public MappingJacksonValue afficherUnArticle(@PathVariable int id) {

        Article article = articleDao.findById(id);

        if (article == null) throw new ArticleIntrouvableException("L'article avec l'id " + id + " est INTROUVABLE");

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat", "marge");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue articleFiltres = new MappingJacksonValue(article);

        articleFiltres.setFilters(listDeNosFiltres);
        return articleFiltres;
    }

    @GetMapping(value="/test/Articles/like/{recherche}")
    public List<Article> testeDeRequetes(@PathVariable String recherche) {
        return articleDao.findByNomLike("%" + recherche + "%");
    }


    @ApiOperation(value="Ajoute un article en base grâce à l'article passé en paramètre")
    @PostMapping(value="/Articles")
    public ResponseEntity<Void> ajouterArticle(@RequestBody Article article) {
        Article articleAdded = articleDao.save(article);

        if (articleAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(articleAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value="Supprime un article grâce à son ID")
    @DeleteMapping(value="/Articles/{id}")
    public void supprimerArticle(@PathVariable int id) {
        articleDao.deleteById(id);
    }

    @ApiOperation(value="Mets à jour un article grâce à l'article passé en paramètre")
    @PutMapping(value="/Articles")
    public void updateArticle(@RequestBody Article article) {
        articleDao.save(article);
    }

    @ApiOperation(value="Récupère la liste des articles côté admin")
    @GetMapping(value="/AdminArticles")
    public MappingJacksonValue calculerMargeArticle() {
        List<Article> articles = articleDao.findAll();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAll();

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue articlesFiltres = new MappingJacksonValue(articles);

        articlesFiltres.setFilters(listDeNosFiltres);
        return articlesFiltres;
    }

    @ApiOperation(value="Récupère la liste des articles triés par ordre alphabetique")
    @GetMapping(value="/AdminArticles/Tri")
    public MappingJacksonValue trierArticlesParOrdreAlphabetique() {
        List<Article> articles = articleDao.findByOrderByNomAsc();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAll();

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue articlesFiltres = new MappingJacksonValue(articles);

        articlesFiltres.setFilters(listDeNosFiltres);
        return articlesFiltres;
    }
}

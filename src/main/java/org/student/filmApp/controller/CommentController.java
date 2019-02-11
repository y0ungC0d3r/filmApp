package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.student.filmApp.domain.*;
import org.student.filmApp.service.CommentService;
import org.student.filmApp.service.FilmService;
import org.student.filmApp.service.UserServiceImpl;

import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FilmService filmService;

    @RequestMapping(value = { "/add-film-comment"}, method = RequestMethod.POST)
    String addFilmComment(@RequestParam(value = "film-id") String filmId,
                          @RequestParam(value = "content") String content) throws IllegalAccessException {

        User user = userService.findLoggedUser();

        Comment comment = new Comment(content, filmService.findById(Long.valueOf(filmId)).orElseThrow(IllegalArgumentException::new), user);

        commentService.save(comment);

        return "redirect:/films/" + filmId + "#comment-section";
    }

    @RequestMapping(value = { "/add-person-comment"}, method = RequestMethod.POST)
    String addPersonComment(@RequestParam(value = "person-id") String personId,
                            @RequestParam(value = "content") String content) throws IllegalAccessException {

        User user = userService.findLoggedUser();

        Comment comment = new Comment(content, new Person(Long.valueOf(personId)), user);

        commentService.save(comment);

        return "redirect:/people/" + personId;
    }

    @RequestMapping(value = { "/delete-comment"}, method = RequestMethod.POST)
    String deleteComment(@RequestParam(value = "comment-id") String id) throws IllegalAccessException {

        User user = userService.findLoggedUser();

        Optional<Comment> commentOpt = commentService.findById(Long.valueOf(id));

        Comment comment = commentOpt.filter(c ->
                        c.getUser().getId().equals(user.getId()))
                .orElseThrow(IllegalAccessException::new);

        commentService.deleteById(comment.getId());

        String resourcePath = comment.getFilm() != null ?
                "films/" + comment.getFilm().getId() :
                "people/" + comment.getPerson().getId();

        return "redirect:/" + resourcePath + "#comment-section";
    }
}

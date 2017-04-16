package xyz.jacobclark.integration.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import xyz.jacobclark.controllers.GameController;
import xyz.jacobclark.games.impl.Gomoku;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void get_Games_ReturnsAnArrayOfGames() throws Exception {
        String response = this.mvc.perform(post("/games")).andReturn().getResponse().getContentAsString();

        Gomoku game = new ObjectMapper().readValue(response, Gomoku.class);
        this.mvc.perform(get("/games/" + game.getUuid()).header("Origin", "http://some-random-url.com"))
                .andExpect(jsonPath("$.players[0].pebbleType", is("BLACK")))
                .andExpect(jsonPath("$.title", is("GOMOKU")))
                .andExpect(header().string("Access-Control-Allow-Origin", "http://some-random-url.com"));
    }

    @Test
    public void post_JoinGame_ReturnsCreatedPlayer() throws Exception {
        String response = this.mvc.perform(post("/games")).andReturn().getResponse().getContentAsString();

        Gomoku game = new ObjectMapper().readValue(response, Gomoku.class);
        this.mvc.perform(post("/games/" + game.getUuid() + "/players").header("Origin", "http://some-random-url.com"))
                .andExpect(jsonPath("$.pebbleType", is("WHITE")))
                .andExpect(header().string("Access-Control-Allow-Origin", "http://some-random-url.com"));

        this.mvc.perform(get("/games/" + game.getUuid()).header("Origin", "http://some-random-url.com"))
                .andExpect(jsonPath("$.players[0].pebbleType", is("BLACK")))
                .andExpect(jsonPath("$.players[1].pebbleType", is("WHITE")))
                .andExpect(jsonPath("$.title", is("GOMOKU")))
                .andExpect(header().string("Access-Control-Allow-Origin", "http://some-random-url.com"));
    }
}
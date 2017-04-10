package xyz.jacobclark.integration.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import xyz.jacobclark.controllers.BoardController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BoardController.class)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void get_pieces_hasPermissiveAccessControlAllowOrigin() throws Exception {
        this.mvc.perform(get("/pieces").header("Origin", "http://some-random-url.com"))
                .andExpect(status().isOk()).andExpect(header().string("Access-Control-Allow-Origin", "http://some-random-url.com"));

        this.mvc.perform(get("/pieces").header("Origin", "http://localhost:63342"))
                .andExpect(status().isOk()).andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:63342"));
    }

    @Test
    public void get_pieces_ReturnsAnEmptyArray_WhenNoPiecesOnBoard() throws Exception {
        this.mvc.perform(get("/pieces"))
                .andExpect(content().string("[]"));
    }
}
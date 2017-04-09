package xyz.jacobclark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.jacobclark.models.Piece;

import java.util.List;

@SpringBootApplication
public class BoardGameEngineApplication {
	public static void main(String[] args) {
		SpringApplication.run(BoardGameEngineApplication.class, args);
	}
}

package yctw.feelpick;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import yctw.feelpick.domain.Food;
import yctw.feelpick.repository.FoodRepository;

@SpringBootApplication
public class FeelpickApplication {
	public static void main(String[] args) {
		SpringApplication.run(FeelpickApplication.class, args);

	}

}

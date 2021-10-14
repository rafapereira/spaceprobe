package elo7.spaceprobe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = {"elo7"})
public class SpaceprobeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceprobeApplication.class, args);
	}

}

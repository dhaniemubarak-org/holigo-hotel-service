package id.holigo.services.holigohotelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HoligoHotelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoligoHotelServiceApplication.class, args);
	}

}

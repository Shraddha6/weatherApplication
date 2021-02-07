package assignment.weather.controller;

import assignment.weather.entity.Weather;
import assignment.weather.service.WeatherService;
//import com.example.weather.integration.ows.WeatherForecast;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherApiController {

	private final WeatherService weatherService;

	public WeatherApiController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@RequestMapping("/bycountryandcity/{country}/{city}")
	public Weather getWeather(@PathVariable String country,
							  @PathVariable String city) {
		return this.weatherService.getWeather(country, city);
	}
	@RequestMapping("/bypolarcoordinates/{latitude}/{longitude}")
	public Weather getWeatherByLatitude(@PathVariable String latitude,
							  @PathVariable String longitude) {
		return this.weatherService.getWeatherWithLatLong(latitude, longitude);
	}
}

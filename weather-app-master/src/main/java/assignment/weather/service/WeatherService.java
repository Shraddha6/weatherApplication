package assignment.weather.service;

import java.net.URI;

import assignment.weather.WeatherAppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import assignment.weather.entity.Weather;

@Service
public class WeatherService {

	private static final String WEATHER_URL =
			"http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}";

	private static final String WEATHER_URL_LAT_LONG =
			"http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={long}&APPID={key}";


	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

	private final RestTemplate restTemplate;

	private final String apiKey;

	public WeatherService(RestTemplateBuilder restTemplateBuilder,
			WeatherAppProperties properties) {
		this.restTemplate = restTemplateBuilder.build();
		this.apiKey = properties.getApi().getKey();
	}

	@Cacheable("weather")
	public Weather getWeather(String country, String city) {
		logger.info("Requesting current weather for {}/{}", country, city);
		URI url = new UriTemplate(WEATHER_URL).expand(city, country, this.apiKey);
		return invoke(url, Weather.class);
	}
	@Cacheable("weatherwithlatitide")
	public Weather getWeatherWithLatLong(String latitude, String logitude) {
		logger.info("Requesting current weather for {}/{}", latitude, logitude);
		URI url = new UriTemplate(WEATHER_URL_LAT_LONG).expand(latitude, logitude, this.apiKey);
		return invoke(url, Weather.class);
	}

	private <T> T invoke(URI url, Class<T> responseType) {
		RequestEntity<?> request = RequestEntity.get(url)
				.accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<T> exchange = this.restTemplate
				.exchange(request, responseType);
		return exchange.getBody();
	}
}

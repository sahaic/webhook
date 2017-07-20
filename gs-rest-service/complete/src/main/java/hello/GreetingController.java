package hello;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template,
				name));
	}

	@RequestMapping(value = "/getByAttribute", method = RequestMethod.POST)
	public @ResponseBody WebhookResponse getModels(
			@RequestBody WebhookRequest obj) {
		String response = "";
		if (obj.getResult() != null && obj.getResult().getAction() != null
				&& obj.getResult().getAction().equalsIgnoreCase("get_model")) {
			String category = obj.getResult().getParameters().get("CATEGORY");
			String oem = obj.getResult().getParameters().get("OEM");
			response = getModelByOEMAndCategory(oem, category);
		} else if (obj.getResult() != null
				&& obj.getResult().getAction() != null
				&& obj.getResult().getAction().equalsIgnoreCase("get_category")) {
			String oem = obj.getResult().getParameters().get("OEM");
			response = getCategoryByOEM(oem);
		}
		return new WebhookResponse(response, "");
	}

	private String getModelByOEMAndCategory(String oem, String category) {
		if ("BMW".equalsIgnoreCase(oem)) {
			switch (category.toUpperCase()) {
			case "COUPE":
				return "We have 2 series Coupe, 4 series Coupe and BMW i8. What do you want to go for?";
			case "SEDAN":
				return "We have BMW 3 series , BMW 7 series and Alpina B7. What do you want to go for?";
			case "CONVERTIBLE":
				return "We have Z4 Roadster and 6 Series Convertible. What do you want to go for?";
			case "SPORTWAGONS":
				return " We have X1 and X3 series. What do you want to go for?";
			default:
				return "We don't have that in BMW models";

			}
		} else if ("Volkswagen".equalsIgnoreCase(oem)) {
			switch (category.toUpperCase()) {
			case "SUV":
				return "We have Atlas and Tiguan. What do you want to go for?";
			case "SEDAN":
				return "We have Jetta and Passat. What do you want to go for?";
			case "COMPACT":
				return "We have Beetle and Golf GTI. What do you want to go for?";
			case "CONVERTIBLE":
				return "Beetle Convertible and EGolf. What do you want to go for?";
			default:
				return "We don't have that in Volkswagen models";

			}
		} else if ("Honda".equalsIgnoreCase(oem)) {
			switch (category.toUpperCase()) {
			case "SUV":
				return "We have CRV and Honda Pilot. What do you want to go for?";
			case "SEDAN":
				return "We have Civic and Accord. What do you want to go for?";
			case "HATCHBACK":
				return "We have Fit and Civic HatchBack. What do you want to go for?";
			case "HYBRID":
				return "We have Accord Hybrid and Fit EV. What do you want to go for?";
			default:
				return "We don't have that in Honda models.";

			}
		}

		return "No models found for " + oem + " " + category;
	}

	private String getCategoryByOEM(String oem) {
		switch (oem.toUpperCase()) {
		case "BMW":
			return "We have COUPE , SEDAN , SPORT WAGONS and CONVERTIBLE categories for "
					+ oem;
		case "VOLKSWAGEN":
			return "We have SUV , SEDAN , COMPACT and CONVERTIBLE categories for "
					+ oem;
		case "HONDA":
			return "We have SUV , SEDAN , HATCHBACK and HYBRID categories for "
					+ oem;
		default:
			return "We don't have any " + oem + " cars";
		}
	}

	@RequestMapping(value = "/geoLocation", method = RequestMethod.GET)
	public void getGeoLocation(

	@RequestParam(value = "address") String formattedAddress)
			throws ConfigurationException, IOException {

		File file = new File("src/main/resources/application.properties");

		PropertiesConfiguration config = new PropertiesConfiguration();

		PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout();

		layout.load(config, new InputStreamReader(new FileInputStream(file)));

		config.setProperty("address", formattedAddress);

		layout.save(config, new FileWriter(
				"src/main/resources/application.properties"));

	}

}
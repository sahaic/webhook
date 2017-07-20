package hello;

import java.io.IOException;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DealershipAssistantController {

	private static final String BMW_ADDRESS="15 B, Wellesley Road Camp, Pune 411001";
	private static final String VOLKSWAGEN_ADDRESS="110/7-8 Opposite PMC, Shivaji Road, Pune, 411005";
	private static final String HONDA_ADDRESS="Lalwani Prestige Viman Nagar Pune, 411014";
	private UserLocation userLocation;

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
		else if (obj.getResult() != null
				&& obj.getResult().getAction() != null
				&& obj.getResult().getAction().equalsIgnoreCase("get_make_address")) {
			String oem = obj.getResult().getParameters().get("MAKE");
			response = getLocationByOEM(oem);
		}
		return new WebhookResponse(response, "");
	}

	private String getLocationByOEM(String oem) {

		if ("BMW".equalsIgnoreCase(oem)) {

			return "Your current location is "+userLocation.getUserLocation() +" and nearby BMW store is at "+ BMW_ADDRESS ;

		} else if ("Volkswagen".equalsIgnoreCase(oem)) {

			return "Your current location is "+userLocation.getUserLocation() +" and nearby Volkswagen store is at "+ VOLKSWAGEN_ADDRESS ;

		} else if ("Honda".equalsIgnoreCase(oem)) {

			return "Your current location is "+userLocation.getUserLocation() +" and nearby Honda store is at "+ HONDA_ADDRESS ;

		}

		return "No nearby dealership found for " + oem;
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

	@RequestParam(value = "address") String formattedAddress) throws ConfigurationException, IOException
	{
		userLocation=new UserLocation();
        userLocation.setUserLocation(formattedAddress);		

	}

}
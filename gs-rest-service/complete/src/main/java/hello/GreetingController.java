package hello;

import java.util.concurrent.atomic.AtomicLong;

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

	@RequestMapping(value = "/getModels", method = RequestMethod.POST)
	public @ResponseBody WebhookResponse getModels(@RequestBody WebhookRequest obj) {
		String speech="";

		if (obj.getResult().getAction().equalsIgnoreCase("input.welcome.action.name")) {
			speech="Hello! I am Chitra";
		} else {
			speech="Hello! I am Mitra";
		}
		

		return new WebhookResponse(speech,
				"Text, Yup I told you so");
	}
}
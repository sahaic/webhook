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

	@RequestMapping(value = "/getByAttribute", method = RequestMethod.POST)
	public @ResponseBody WebhookResponse getModels(
			@RequestBody WebhookRequest obj) {
		String model="";
		if (obj.getResult() !=null && obj.getResult().getAction()!=null && obj.getResult().getAction().equalsIgnoreCase("get_model")) {
			String category=obj.getResult().getParameters().get("category");
			String oem=obj.getResult().getParameters().get("oem");
			 model=getModelByOEMAndCategory(oem,category);
		}
		return new WebhookResponse(model,"");
	}

	private String getModelByOEMAndCategory(String oem, String category) {
             if ("BMW".equalsIgnoreCase(oem))	{
            	 switch(category) {
            		 case "suv": return "GT. BMW Gran Turismo.";
            		 case "sedan" : return "BMW 3 series sedan";
            		 case "compact" : return "BMW X3";
            	 }
             } 
             else if ("Volkswagen".equalsIgnoreCase(oem))	{
            	 switch(category) {
        		 case "suv": return "Atlas";
        		 case "sedan" : return "Jetta";
        		 case "compact" : return "Golf GTI";
        	 }
         }
             
             return "No models found for"+" "+oem+" "+category;
	}
}
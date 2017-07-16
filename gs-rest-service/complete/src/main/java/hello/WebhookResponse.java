package hello;

import java.util.ArrayList;
import java.util.List;


public class WebhookResponse {
    private final String speech;
    private final String displayText;

    private final String source = "java-webhook";
    private final List contextOut;
    private final Object data;
          
    
    public WebhookResponse(String speech, String displayText) {
        this.speech = speech;
        this.displayText = displayText;
        this.contextOut = new ArrayList();
        this.data = new Object();
    }

    public String getSpeech() {
        return speech;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getSource() {
        return source;
    }
}
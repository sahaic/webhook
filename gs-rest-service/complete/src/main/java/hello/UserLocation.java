package hello;

import org.springframework.context.annotation.Scope;

@Scope(value="request")
public class UserLocation {
	
	String userLocation;

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

}

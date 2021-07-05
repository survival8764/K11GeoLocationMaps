package com.kosmo.k11geolocationmaps;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	private static final String apiKey = "AIzaSyAkFEPJKBHmLTC-7cVP4OrWBhfREJQE08o";
	
	@RequestMapping(value="/GeoLocation/01GeoLocation.do",
			method=RequestMethod.GET)
	public String geoFunc1(Model model) {
		model.addAttribute("apiKey", apiKey);
		return "01GeoLocation/01GeoLocation";
	}
	
	@RequestMapping(value="/GeoLocation/02GoogleMap.do",
			method=RequestMethod.GET)
	public String geoFunc2(Model model) {
		model.addAttribute("apiKey", apiKey);
		return "01GeoLocation/02GoogleMap";
	}
	
}

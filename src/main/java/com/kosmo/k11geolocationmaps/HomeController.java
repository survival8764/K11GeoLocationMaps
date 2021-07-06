package com.kosmo.k11geolocationmaps;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import geolocation.ISearchRadius;
import geolocation.MyFacilityDTO;

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
	
	// 내위치값 알아내기
	@RequestMapping(value="/GeoLocation/01GeoLocation.do",
			method=RequestMethod.GET)
	public String geoFunc1(Model model) {
		model.addAttribute("apiKey", apiKey);
		return "01GeoLocation/01GeoLocation";
	}
	
	// 구글맵
	@RequestMapping(value="/GeoLocation/02GoogleMap.do",
			method=RequestMethod.GET)
	public String geoFunc2(Model model) {
		model.addAttribute("apiKey", apiKey);
		return "01GeoLocation/02GoogleMap";
	}
	
	@RequestMapping(value="/GeoLocation/03MyLocation.do",
			method=RequestMethod.GET)
	public String geoFunc3(Model model) {
		model.addAttribute("apiKey", apiKey);
		return "01GeoLocation/03MyLocation";
	}
	
	// Mybatis 사용을 위한 빈 자동주입 
	@Autowired
	private SqlSession sqlSession;
	
	// 내 위치기반 시설물 반경검색
	@RequestMapping(value="/GeoLocation/04SearchRadius.do", method=RequestMethod.GET)
	public String geoFunc4(Model model, HttpServletRequest req) {
		
		model.addAttribute("apiKey", apiKey);
		
		// 폼값받기(String으로 전송된 값을 정수 호근 실수형으로 변경함)
		int distance = (req.getParameter("distance")==null) ?
				0 : Integer.parseInt(req.getParameter("distance"));
		double latTxt = (req.getParameter("latTxt")==null) ?
				0 : Double.parseDouble(req.getParameter("latTxt"));
		double lngTxt = (req.getParameter("lngTxt")==null) ?
				0 : Double.parseDouble(req.getParameter("lngTxt"));
		
		// 검색결과 카운트
		int numberPerPage = 200;// 검색결과는 한번에 200개씩 출력
		// 거리, 위도, 경도를 매개변수로 검색결과 카운트
		int resultCount = sqlSession.getMapper(ISearchRadius.class)
				.searchCount(distance, latTxt, lngTxt);
		model.addAttribute("resultCount", " / 검색결과:"+ resultCount +"건");
		/*
			전체 페이지 수(검색결과 / 출력할갯수) - 페이지 번호를 대체할 <select>
			태그에서 사용할 속성값.
		 */
		model.addAttribute("selectNum", Math.ceil(resultCount/numberPerPage));
		
		// 현재 페이지 번호 가져오기
		int pageNum = (req.getParameter("pageNum")==null) ?
				1 : Integer.parseInt(req.getParameter("pageNum"));
		
		// 출력할 게시물의 범위 결정
		int start = ((pageNum - 1) * numberPerPage) + 1;
		int end = pageNum * numberPerPage;
		
		// 위에서 계산한값과 파라미터를 로그로 출력
		System.out.println(distance +" "+ latTxt +" "+ lngTxt +" "+ start +" "+ end);
		ArrayList<MyFacilityDTO> searchLists = null;
		if(distance!=0) {
			searchLists = sqlSession.getMapper(ISearchRadius.class)
					.searchRadius(distance, latTxt, lngTxt, start, end);
		}
		model.addAttribute("searchLists", searchLists);
		
		return "01GeoLocation/04SearchRadius";
	}
}

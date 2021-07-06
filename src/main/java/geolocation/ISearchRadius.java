package geolocation;

import java.util.ArrayList;

public interface ISearchRadius {

	public int searchCount(int distance, double latTxt, double lngTxt);
	
	public ArrayList<MyFacilityDTO> searchRadius(int distance,
			double latTxt, double lngTxt, int start, int end);
}

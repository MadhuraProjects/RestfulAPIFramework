package StandAlone;

public class PutPlaceBody {
	public static String putPlacepayload(String placeId, String newAddress)
	{
		return "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "";
	}

}


package noJunit;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
/**
 * @author Profe
 */
public class TestJson {
  private final static String jString = 
        "{" 
        + "    \"geodata\": [" 
        + "        {" 
        + "                \"id\": \"1\"," 
        + "                \"name\": \"Uma Thurman\","                  
        + "                \"gender\" : \"female\"," 
        + "                \"latitude\" : \"37.33774833333334\"," 
        + "                \"longitude\" : \"-121.88670166666667\""            
        + "        }," 
        + "        {" 
        + "                \"id\": \"2\"," 
        + "                \"name\": \"Johnny Depp\","          
        + "                \"gender\" : \"male\"," 
        + "                \"latitude\" : \"37.336453\"," 
        + "                \"longitude\" : \"-121.884985\""            
        + "        }" 
        + "    ]" 
        + "}"; 
    
    public static void main(String[] args) throws JSONException {
    
       JSONObject jObject = new JSONObject(jString);
       JSONArray geoData = jObject.getJSONArray("geodata");
       JSONObject geoPers;

       int size = geoData.length();
       
       for (int i=0; i < size; i++){
            geoPers = geoData.optJSONObject(i);
            
            if (geoPers.has("id")) {
                String geoId = geoPers.getString("id");
                System.out.println(geoId);
            }            
            if (geoPers.has("name")){
                String name = geoPers.getString("name");
                System.out.println(name);
            }                
            if (geoPers.has("gender")) {
                String gender = geoPers.getString("gender");
                System.out.println(gender);
            }
             if (geoPers.has("latitude")) {
                 String lat = geoPers.getString("latitude");
                System.out.println(lat);
             }
            if (geoPers.has("longitude")){
                String longit = geoPers.getString("longitude");
                System.out.println(longit); 
            }                                     
       }
   }
}

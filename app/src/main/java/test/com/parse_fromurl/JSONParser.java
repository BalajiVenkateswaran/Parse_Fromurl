package test.com.parse_fromurl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

/**
 * Created by BALAJI on 12-07-2016.
 */
public class JSONParser {
    public List<HashMap<String,String>>  parse(JSONObject jobject){
        JSONArray jCities=null;
        try{
            jCities=jobject.getJSONArray("cities");
        }catch(JSONException e){
            e.printStackTrace();;
        }
        return getCities(jCities);
    }
    private  List<HashMap<String,String>> getCities(JSONArray jcities) {
        int citycount=jcities.length();
        List<HashMap<String,String>> citylist=new ArrayList<HashMap<String, String>>();
        HashMap<String,String> city=null;
        for(int i=0;i<citycount;i++){
            try{
                city=getCity((JSONObject)jcities.get(i));
                citylist.add(city);

            }catch(Exception e){

            }

        }
        return citylist;
    }
    private HashMap getCity(JSONObject jcity){
        HashMap city=new HashMap<>();
        String cityname="";
        String cover="";
        try{
            cityname=jcity.getString("name");
            cover=jcity.getString("cover");
            city.put("cityname",cityname);
            city.put("img",R.id.imageView);
            city.put("cover",cover);

        }catch (Exception e){

        }
        return city;
    }

}

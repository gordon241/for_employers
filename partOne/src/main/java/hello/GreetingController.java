package hello;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        
    	JSONObject json_names = null;
    	JSONObject json_phone = null;
    	JSONObject merged = null;
    	
		try {
			json_names = JsonReader.readJsonFromUrl("http://country.io/names.json");
			json_phone = JsonReader.readJsonFromUrl("http://country.io/phone.json");
			
			merged = new JSONObject(json_names, JSONObject.getNames(json_names));
			for(String key : JSONObject.getNames(json_phone))
			{
			  merged.put(key, json_phone.get(key));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	model.addAttribute("name", merged.toString());
        return "greeting";
    }

}
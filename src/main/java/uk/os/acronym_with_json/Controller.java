package uk.os.acronym_with_json;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;


@RestController
public class Controller {

    HashMap<String, String> acronyms = new HashMap<>();

    public Controller(){
        acronyms.put("OS", "Ordnance Survey");
        acronyms.put("CRS", "Coordinate Reference System");
        acronyms.put("BNG", "British National Grid");
        acronyms.put("PSGA", "Public Sector Geospatial Agreement");
        acronyms.put("NGD", "National Geographic Database");
    }
    @GetMapping("test")
    public String acronymJson() throws IOException {
        File resource = new ClassPathResource("acronyms.json").getFile();
        String file = new String(Files.readAllBytes(resource.toPath()));
        return file;

    }



    @GetMapping("/")
    @ResponseBody
    public String searchForAcronym(@RequestParam(name = "acronym", required = false) String acronym) throws IOException {
        if(acronym == null) {
            return acronymJson();
        }
        if(acronyms.containsKey(acronym.toUpperCase())) {
            return String.format("%s: %s",acronym.toUpperCase(),acronyms.get(acronym.toUpperCase()));
        }
        return "This acronym doesn't exist";
    }

    @GetMapping("all")
    //Probably need to make it an object, perhaps. so that it doesnt change all the time.
    //Reading to console, must get it returning
    //Created Stringbuilder object, returns perfectly, display is a mess.
    public String getAcronyms(){
        StringBuilder result = new StringBuilder("");
        for(HashMap.Entry<String, String> entry : acronyms.entrySet()){
                String key = entry.getKey();
                String val = entry.getValue();
                result.append(String.format("%s: %s ", key,val));
        }
        return result.toString();
    }
}

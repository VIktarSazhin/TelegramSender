package Handler.Convertor;

import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JSConverter {

    public static List<User>  parse() throws IOException {
        List<User> listUser = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
//        File file = new File("output.json");
//        try (FileReader fileReader = new FileReader(file.getAbsolutePath())) {
        try {
//            JSONObject object = (JSONObject) jsonParser.parse(fileReader);
            JSONObject object = (JSONObject) jsonParser.parse(JsoupParser.parsURL());
            JSONArray jsonArray = (JSONArray) object.get("Players_data");
            for (Object o :
                    jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                String user_name = (String) jsonObject.get("user_name");
                double spend_time = (Double) jsonObject.get("spend_time");
                String activities = (String) jsonObject.get("activities");
                User user = new User(user_name, String.valueOf(spend_time),activities);
                listUser.add(user);
                System.out.println(listUser);
            }
            Collections.sort(listUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUser;
    }
}

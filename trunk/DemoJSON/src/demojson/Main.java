/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package demojson;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.lang.*;

/**
 *
 * @author VinhPham
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String jsonTxt = "{'foo':'bar','coolness':2.0,'altitude':39000,'pilot':{'firstName':'Buzz','lastName':'Aldrin'},'mission':'apollo 11'}";
        JSONObject json = (JSONObject) JSONSerializer.toJSON( jsonTxt );
        double coolness = json.getDouble( "coolness" );
        int altitude = json.getInt( "altitude" );
        JSONObject pilot = json.getJSONObject("pilot");
        String firstName = pilot.getString("firstName");
        String lastName = pilot.getString("lastName");

        System.out.println( "Coolness: " + coolness );
        System.out.println( "Altitude: " + altitude );
        System.out.println( "Pilot: " + lastName );
    }

}

package test.julian.bitgray.Asynctask;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import test.julian.bitgray.Models.User;
import test.julian.bitgray.Models.UserAddress;
import test.julian.bitgray.Models.UserCompany;

import static test.julian.bitgray.Config.Env.*;

/**
 * Created by JulianStack on 09/06/2017.
 */

public class GetUserList extends AsyncTask<String, String, JSONArray> {

    // GetUser Log Tag
    private String TAG = "GetUserList";

    // Connection objects
    private HttpURLConnection connection;
    private URL url;

    // Objects
    private JSONArray jsonArray;
    private Context mContext;
    private UserListInterface mInterface;
    private ArrayList<User> Lista = new ArrayList<>();


    // Constructor
    public GetUserList(UserListInterface minterface,  Context context) {
        this.mContext = context;
        this.mInterface = minterface;
    }

    // Interface
    public interface UserListInterface {
        void success(ArrayList<User> lista);
        void failed(String messageError);
    }

    @Override
    protected JSONArray doInBackground(String... args) {
        try {


            connection = null;

            //Create connection
            url = new URL(getUsersUrl(mContext));
            connection = (HttpURLConnection)url.openConnection();
            // Set Method to Get
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            // Accept response
            connection.setDoInput(true);


            //Get Response
            String aux = "";
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                aux += line;
            }

            Log.d(TAG,aux);

            // Get Json Array
            JSONArray array = new JSONArray(aux);

            //Fill Users ArrayList
            for(int i = 0; i<array.length(); i++){

                // Get Json Objects
                JSONObject js = array.getJSONObject(i);
                JSONObject cjs = js.getJSONObject("company");
                JSONObject ajs = js.getJSONObject("address");

                Location location = new Location("");

                JSONObject GeoLocation = ajs.getJSONObject("geo");
                location.setLatitude(Double.parseDouble(GeoLocation.getString("lat")));
                location.setLongitude(Double.parseDouble(GeoLocation.getString("lng")));


                UserAddress Address = new UserAddress(ajs.getString("street"),
                        ajs.getString("suite"),
                        ajs.getString("city"),
                        ajs.getString("zipcode"),location);

                UserCompany Company = new UserCompany(cjs.getString("name"),
                        cjs.getString("catchPhrase"),
                        cjs.getString("bs"));

                // Add users to ArrayList
                Lista.add(new User(js.getInt("id"),
                        js.getString("name"),
                        js.getString("username"),
                        js.getString("email"),
                        Address,
                        js.getString("phone"),
                        js.getString("website"),
                        Company));
            }

            // Close connection
            rd.close();
            return jsonArray;

        } catch (Exception e) {

            cancel(true);
            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        Log.d(TAG, "SUCCESS");
        // Send ArrayList
        mInterface.success(Lista);
    }

    @Override
    protected void onCancelled(JSONArray result){
        Log.d(TAG, "CANCELLED");
        // Send Error Request Message
        mInterface.failed(getRequestError(mContext));
    }
}

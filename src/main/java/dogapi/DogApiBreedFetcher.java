package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */


    @Override
    public List<String> getSubBreeds(String breed) {
        DogApiBreedFetcher fetcher = new DogApiBreedFetcher();
        try {
            Request request = new Request.Builder()
                    .url("https://dog.ceo/api/breed/" + breed + "/list")
                    .build();
            String subBreeds = client.newCall(request).execute().body().string();
            JSONObject subBreedsJSON = new JSONObject(subBreeds);
            JSONArray subBreedsJSONArray = subBreedsJSON.getJSONArray("message");
            String subBreedsString = subBreedsJSONArray.toString();
            String subBreedsSubString = subBreedsString.substring(1, subBreedsString.length() - 1);
            String[] subBreedsArray = subBreedsSubString.split(",");
            List<String> subBreedsArrayList = new ArrayList<String>();
            for (int i = 0; i < subBreedsArray.length; i++) {
                String subBreedsArraySubstring = subBreedsArray[i].substring(1, subBreedsArray[i].length() - 1);
                subBreedsArrayList.add(subBreedsArraySubstring);
            }
            return subBreedsArrayList;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
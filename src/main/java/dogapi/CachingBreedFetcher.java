package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {

    private int callsMade = 0;
    private Map<String, List<String>> map = new HashMap<>();
    private BreedFetcher classfetcher;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.classfetcher = fetcher;
        this.callsMade = callsMade;
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        // return statement included so that the starter code can compile and run.
            if (map.containsKey(breed)) {
                return map.get(breed);
            }
            else {
                callsMade += 1;
                try {
                    map.put(breed, ((this.classfetcher.getSubBreeds(breed))));
                    return map.get(breed);
                }
                catch (BreedNotFoundException e) {
                    throw new BreedNotFoundException("Breed not found: " + breed);
                }
            }
    }

    public int getCallsMade() {
        return callsMade;
    }
}
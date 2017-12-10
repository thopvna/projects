package main.com.jlaotsezu.library.support.architecture;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Intent {
    private URL url;
    private Map<String, String> extras;

    public Map<String, String> getExtras() {
        return extras;
    }

    public Intent(URL url) {
        this.url = url;
        this.extras = new HashMap<>();
    }
    public Intent(){
        this(null);
    }

    public URL getUrl() {
        return url;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void putExtra(String key, String value){
        extras.put(key, value);
    }
    public String getExtra(String key){
        if(extras.containsKey(key)){
            return extras.get(key);
        }
        return null;
    }
}

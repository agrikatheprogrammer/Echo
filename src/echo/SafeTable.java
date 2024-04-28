package echo;

import java.util.HashMap;

public class SafeTable extends HashMap<String,String> {
    // Override the get method with synchronized keyword
    @Override
    public synchronized String get(Object key) {
        return super.get(key);
    }

    // Override the put method with synchronized keyword
    @Override
    public synchronized String put(String key, String value) {
        return super.put(key, value);
    }
}

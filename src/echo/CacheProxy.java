package echo;

public class CacheProxy extends ProxyHandler {
    protected static SafeTable cache=new SafeTable();

    @Override
    protected String response(String msg) throws Exception {
        if (cache.containsKey(msg)) {
            System.out.println("Retrieving from the cache...");
            return cache.get(msg);
        }
        System.out.println("Forwarding your request to my peer...");
        peer.send(msg);
        String response=peer.receive();
        cache.put(msg,response);
        return response;
    }
}

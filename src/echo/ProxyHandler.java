package echo;


import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ProxyHandler extends RequestHandler {

    protected Correspondent peer;

    public ProxyHandler(Socket s) { super(s);
    }
    public ProxyHandler() { super(); }

    public void initPeer(String host, int port) {
        peer = new Correspondent();
        peer.requestConnection(host, port);
    }

    protected String response(String msg) throws Exception {
        // forward msg to peer
        new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)),true).println("Forwarding your request to my peer...");
        peer.send(msg);
        // resurn peer's response
        return peer.receive();
    }
}
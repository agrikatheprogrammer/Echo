package echo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

public class Server {

    protected ServerSocket mySocket;
    protected int myPort;
    public static boolean DEBUG = true;
    protected Class<?> handlerType;

    public Server(int port, String handlerType) {
        try {
            myPort = port;
            mySocket = new ServerSocket(myPort);
            this.handlerType = (Class.forName(handlerType));
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } // catch
    }


    public void listen() {
        System.out.println("Server listening at port "+myPort); //del this?
        while(true) {
            // accept a connection
            try {
                Socket clientSocket=mySocket.accept();
                RequestHandler handler=makeHandler(clientSocket);
                Thread handlerThread=new Thread(handler);
                handlerThread.start();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            // make handler
            // start handler in its own thread
        } // while
    }

    public RequestHandler makeHandler(Socket s) {
        // handler = a new instance of handlerType
        try {
            RequestHandler handler = (RequestHandler) handlerType.getDeclaredConstructor().newInstance();
            handler.setSocket(s);
            return handler;

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println(e.getMessage());
            return null;
        }
        //    use: try { handlerType.getDeclaredConstructor().newInstance() } catch ...
        // set handler's socket to s
        // return handler
    }



    public static void main(String[] args) {
        int port = 5555;
        String service =
                 "math.MathHandler";
                //"echo.RequestHandler";
                //"casino.CasinoHandler";
        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(port, service);
        server.listen();
    }
}
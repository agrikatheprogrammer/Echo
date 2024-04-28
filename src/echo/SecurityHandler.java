package echo;

import echo.ProxyHandler;
import echo.SafeTable;

public class SecurityHandler extends ProxyHandler {
    protected static SafeTable users=new SafeTable();//?
    protected boolean loggedin;

    @Override
    protected String response(String msg) throws Exception {
        String response="must be logged in to an account --> create one or log in to an existing one";
        String[] tokens=msg.split("\\s+");
        if (tokens[0].equalsIgnoreCase("new")) {
            if (tokens.length==3) {
                if (!users.containsKey(tokens[1])) {
                    users.put(tokens[1],tokens[2]);
                    active=false;
                    response="success --> created an account";}
                else {
                    response="failure --> username is not unique";
                }
            } else {
                response="invalid input --> format should be new username password";
            }
        }
        else if (tokens[0].equalsIgnoreCase("login")) {
            active=false;
            if (tokens.length==3) {
                if (users.containsKey(tokens[1])){
                    if (users.get(tokens[1]).equals(tokens[2])){
                        loggedin=true;
                        active=true;
                        response= "success --> logged in";
                    } else {
                        response="failure --> wrong password";
                    }
                } else {
                    response="failure --> username not found";
                }
            } else {
                response="invalid input --> format should be login username password";
            }
        }
        else if (loggedin) {
            peer.send(msg);
            response=peer.receive();
        }
        return response;
    }


}

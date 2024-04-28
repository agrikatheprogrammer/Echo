package math;

import echo.RequestHandler;

import java.net.Socket;

public class MathHandler extends RequestHandler {
    public MathHandler() {super();}
    public MathHandler(Socket sock) {super(sock);}



    private String execute(String operation, double[] args) {
        double result = 0.0;
        if (operation.equalsIgnoreCase("add")) {
            for (double arg : args) {
                result += arg;
            }
        } else if (operation.equalsIgnoreCase("mul")) {
            result = 1.0;
            for (double arg : args) {
                result *= arg;
            }
        } else if (operation.equalsIgnoreCase("sub")) {
            result = args[0];
            for (int i = 1; i < args.length; i++) {
                result -= args[i];
            }
        } else if (operation.equalsIgnoreCase("div")) {
            result = args[0];
            for (int i = 1; i < args.length; i++) {
                result /= args[i];
            }
        } else {
            return "invalid operation: "+operation;
        }
        return "" + result;
    }

    protected String response(String request) {
        String[] tokens = request.split("\\s+");
        double[] args = new double[tokens.length - 1];
        try {
            for (int i = 0; i < args.length; i++) {
                args[i] = Double.parseDouble(tokens[i+1]);
            }
        } catch(NumberFormatException e) {
            return "arguments must be numeric";
        }
        return execute(tokens[0], args);
    }
}


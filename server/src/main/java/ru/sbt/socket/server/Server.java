package ru.sbt.socket.server;

/**
 * Created by kirill on 08.09.16
 */
public class Server {
    /*public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9090);
            while(true) {
                try(Socket client = serverSocket.accept()) {
                    byte[] b = new byte[1024];
                    InputStream is = client.getInputStream();
                    int count = is.read(b);
                    System.out.println(new String(b, 0, count));
                    OutputStream os = client.getOutputStream();
                    os.write("12345".getBytes());
                    os.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

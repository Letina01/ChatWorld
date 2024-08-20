 import java.net.*;
 import java.io.*;
 class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;//read
    PrintWriter out;//write


    public Server(){
        try{
            server = new ServerSocket(7777);
            System.out.println("server is ready to aceept connection");
            System.out.println("waiting...");
            socket = server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());


            startReading();
            startWriting();

        }catch(Exception e){
            e.printStackTrace();

        }

    }

    public void startReading(){
      //read thread
        Runnable r1 = ()->{
            System.out.println("reader started");
            try{

            while(true){
                
                    String msg=br.readLine();
                if(msg.equals("exit")){
                    System.out.println("client terminated the chat");
                    break;
                }
                System.out.println("client: "+msg);
                }
            }catch(Exception e){
                    //e.printStackTrace();
                    System.out.println("Connection closed");
                }
        };
        new Thread(r1).start();
        
    }

    public void startWriting(){
        //write thread
        Runnable r2 = ()->{
            System.out.println("writer started");
            try{
            while(true && !socket.isClosed()){
                
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
               
                    out.println(content);
                    out.flush();
                     if(content.equals("exit")){
                   socket.close();
                    break;
                  }
                }
            }catch(Exception e){
                    //e.printStackTrace();
                    System.out.println("Connection closed");
                }
        };

        new Thread(r2).start();
       

    }


    public static void main(String[] args) {
        System.out.println("This is server");
        new Server();

    }
    
}

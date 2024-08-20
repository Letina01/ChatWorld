import java.net.*;
import java.io.*;
 class client {
    Socket socket;
    BufferedReader br;//read
    PrintWriter out;//write

    public client() {
        try {
            System.out.println("sending request to server");
            socket  = new Socket("127.0.0.1",7777);
            System.out.println("Connection Done.");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());


            startReading();
            startWriting();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void startReading(){
        //read thread
          Runnable r1 = ()->{
              System.out.println("reader started");
              try {
  
              while(true){
                      String msg=br.readLine();
                  if(msg.equals("exit")){
                      System.out.println("server terminated the chat");
                      socket.close();
                      break;
                  }
                
                  System.out.println("server: "+msg);
                 
              }
              
            } catch (Exception e) {
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
              try {
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
                  System.out.println("Connection closed");
          };
  
         new Thread(r2).start();
          
  
      }
  

    public static void main(String[] args) {
        System.out.println("This is Client");
        new client();
    }
    
}

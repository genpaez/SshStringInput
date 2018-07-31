package SshStringInput;
import com.jcraft.jsch.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import javax.swing.*;

public class SshStringInput{    // Shell, conexion desde server // Envio y recibe ok Piped -> String
  /**
   * @wbp.parser.entryPoint
   */
  /**
   * @wbp.parser.entryPoint
   */
private static UserInterface UI = new UserInterface();
private static PipedOutputStream pin;
private static PipedInputStream pout;
private static String myCommand, user;
private static Session session;
private static int i=0;
private static Channel channel;


  public static void main(String[] arg){
  

  
    
    try{
      JSch jsch=new JSch();

      String host=null;
      if(arg.length>0){
        host=arg[0];
      }
      session = null;
      

      
      UserInfo ui = new MyUserInfo(){
        public void showMessage(String message){
          JOptionPane.showMessageDialog(null, message);
        }

        @SuppressWarnings("unused")
        public boolean promptYesNo(String message){
            Object[] options={ "yes", "no" };
            int foo = 0;
            return foo==0;     // promptYesNo library method. Return 0 to avoid message
          }  

      };
      
      

      session.setUserInfo(ui);
      
      // It must not be recommended, but if you want to skip host-key check,
      // invoke following,
      //   session.setConfig("StrictHostKeyChecking", "no");

      //session.connect();
      session.connect(5000);   // making a connection with timeout.

      channel=session.openChannel("shell");

    
      

      //*********************
      //*********************  

    //  channel.setInputStream(System.in);
     
      PipedInputStream en = new PipedInputStream();
      pin = new PipedOutputStream((PipedInputStream) en);
      BufferedReader br = new BufferedReader(new InputStreamReader(en = (PipedInputStream) channel.getInputStream()));
      
      channel.connect(5*1000);     
      
//******************************************************************************************************      
      String received;
      StringBuilder sb = new StringBuilder();
      while((received=br.readLine())!=null) {
    	  System.out.println(received);
          sb.append(received+"\n");

          
          if(received.contains("@")) break;                 // no lo toma
          if(received.contains("Telefonica")) break;        // fin de ciclo While

      }
      UI.setRespuesta(sb.toString());                       // Respuesta en UI
    }
    catch(Exception e){
      System.out.println(e);
  	System.out.println("Falló conexión, rechazada por host remoto B"); //// OJO mensaje de error por ocupación
	
    }
    
//******************************************************************************************************
    channel.disconnect();
    session.disconnect();
    System.out.println("Disconnected channel and session");
    
  }

  
  
public static abstract class MyUserInfo
                          implements UserInfo, UIKeyboardInteractive{
    public String getPassword(){ return null; }
    public boolean promptYesNo(String str){ return false; }
    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return false; }
    public boolean promptPassword(String message){ return false; }
    public void showMessage(String message){ }
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
      return null;
    }
  }




public static void conectarRouterPE() {
 
	
	myCommand = "ssh opegpae1@10.250.2.152 \n";
	try {
		pin.write(myCommand.getBytes());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


  }


  
	public static void enviarYes() {
		myCommand = "bas \n";
		try {
			pin.write(myCommand.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void enviarClave() {
		myCommand = "FN5ihAJo";
		try {
			pin.write(myCommand.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myCommand = "\n";
			pin.write(myCommand.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void verInterfaz() {
		try {
			myCommand = "show service id 500151940 interface 10.20.30.1";
			pin.write(myCommand.getBytes());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myCommand = "\n";
			pin.write(myCommand.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   // readChannelOutput(channel);
	}

	private static void readChannelOutput(Channel channel){
		

	      

	    byte[] buffer = new byte[2048];
	    int index =0;
	    StringBuilder sb = new StringBuilder(2048);;
	    String line = "";

	    try{
	        InputStream in = channel.getInputStream();

	        
	        
	        while (true){
	            while (in.available() > 0) {
	                int i = in.read(buffer, 0, 2048);
	                if (i < 0) {
	                    break;
	                }
	                line = new String(buffer, 0, i);
	                sb.insert(index,line);
	                index++;
	                
	            }
	            
	            if(line.contains("A:BOG-32003#")){   /// finaliza el flujo cuando aparece user@server o @host
	                break;
	            }
	            if(line.contains("logout")){   /// finaliza el flujo cuando aparece user@server
	                break;
	            }
	            if(line.contains("opegpae1@secradbogsrv01")){
	                  break;
	            }

	            if (channel.isClosed()){
	                break;
	            }
	            try {
	                Thread.sleep(200);
	            } catch (Exception ee){}
	        }

	    }catch(Exception e){
	        System.out.println("Error while reading channel output: "+ e);
	    }
	    
	    
	}
}


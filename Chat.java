import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

class Chat{

	static void envia_mensaje_multicast(byte[] buffer,String ip,int puerto) throws IOException{
		DatagramSocket socket = new DatagramSocket();
		socket.send(new DatagramPacket(buffer,buffer.length,InetAddress.getByName(ip),puerto));
		socket.close();
	}

	static byte[] recibe_mensaje_multicast(MulticastSocket socket,int longitud_mensaje) throws IOException{
		byte[] buffer = new byte[longitud_mensaje];
		DatagramPacket paquete = new DatagramPacket(buffer,buffer.length);
		socket.receive(paquete);
		return paquete.getData();
	}

	static class Worker extends Thread{
		public void run(){
			//Ciclo infinito para recibir los mensajes enviados al grupo
			try{
				InetAddress group = InetAddress.getByName("230.0.0.0");
				MulticastSocket socket = new MulticastSocket(50000);
				socket.joinGroup(group);

				while(true){
					byte[] mensaje = recibe_mensaje_multicast(socket, 256);
					System.out.println(new String(mensaje,"UTF-8"));
				}

			}catch (Exception e){
				System.err.println(e.getMessage());
			}


		}
	}

	public static void main(String[] args) {
		Worker w = new Worker();
		w.start();

		String nombre = args[0];

		//Ciclo infinito para leer el teclado y enviar los mensajes al grupo

		System.out.println("Usuario->"+nombre);

        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

        try{
	        while (true){
	            System.out.print("Escribe el mensaje: ");
	            String mensaje = nombre  +  ": " + entrada.readLine();
	            envia_mensaje_multicast(mensaje.getBytes(), "230.0.0.0", 50000);
	        }
	    }catch (Exception e){
				System.err.println(e.getMessage());
		}
	}
}
import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.lang.Thread;
import java.nio.ByteBuffer;

class Token{
	static DataInputStream entrada;
	static DataOutputStream salida;
	static boolean primera_vez = true;
	static String ip;
	static int nodo;
	static int token;
	static int contador = 0;

	static class Worker extends Thread{
		public void run(){
			try{
				ServerSocket servidor = new ServerSocket(50000);
				Socket conexion = servidor.accept();
				entrada = new DataInputStream(conexion.getInputStream());
			}catch (Exception e){
				System.err.println(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Se debe pasar como parametros el numero");
			System.exit(1);
		}

		nodo = Integer.valueOf(args[0]);
		ip = args[1];

		Worker w = new Worker();
		w.start();

		Socket conexion = null;

		try{
			for(;;){
				try{
					conexion = new Socket(ip,50000);
					break;
				}
				catch (Exception e){
					Thread.sleep(500);
				}
			}

			salida = new DataOutputStream(conexion.getOutputStream());
			w.join();

			for (; ; ) {
				if (nodo == 0) {
					if (contador == 1000) {
						break;
					}
					if (primera_vez) {
						primera_vez = false;
						token = 1;
					}else{
						token = entrada.readInt();
						contador++;
						System.out.println("Nodo: " + nodo + " Contador: " + contador + " Token: " + token);
					}
				}else{
					token = entrada.readInt();
					contador++;
					System.out.println("Nodo: " + nodo + " Contador: " + contador + " Token: " + token);
				}
				salida.writeInt(token);
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}

	}
}
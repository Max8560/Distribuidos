import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRMI extends Remote{
	public float[][] separa_matriz(float[][] a,int inicio, int n) throws RemoteException;
	public float[][] multiplica_matrices(float[][] a,float[][] b, int n) throws RemoteException;
	public float[][] acomoda_matriz(float[][] c, float[][] a, int renglon, int columna, int n) throws RemoteException;
}
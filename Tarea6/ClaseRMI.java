import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ClaseRMI extends UnicastRemoteObject implements InterfaceRMI{

	public ClaseRMI() throws RemoteException{
		super();
	}

	public float[][] multiplica_matrices(float[][] a,float[][] b, int n) throws RemoteException{
		float[][] c = new float[n/2][n/2];
		for (int i=0;i < n/2;i++) {
			for (int j=0;j < n/2;j++) {
				for (int k=0;k<n;k++) {
					c[i][j] += a[i][k] * b[j][k];
				}
			}
		}
		return c;
	}

	public float[][] separa_matriz(float[][] a,int inicio, int n) throws RemoteException{
		float[][] m = new float[n/2][n];
		for (int i=0;i<n/2;i++) {
			for (int j=0;j<n;j++) {
				m[i][j] = a[i + inicio][j];
			}
		}
		return m;
	}

	public float[][] acomoda_matriz(float[][] c, float[][] a, int renglon, int columna, int n) throws RemoteException{
		for (int i=0;i<n/2;i++) {
			for (int j=0;j<n/2;j++) {
				c[i + renglon][j + columna] = a[i][j];
			}
		}
		return c;
	}
}
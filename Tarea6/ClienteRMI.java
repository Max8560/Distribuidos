import java.rmi.Naming;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClienteRMI{
	public static void main(String[] args) throws Exception{
		String url = "rmi://10.0.0.5/tarea6";

		InterfaceRMI r = (InterfaceRMI)Naming.lookup(url);

		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Valor de N:");
		int n = Integer.parseInt(entrada.readLine());
		double checksum = 0;

		float[][] a = new float[n][n];
		float[][] b = new float[n][n];

		for (int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				a[i][j] = i-3*j;
				b[i][j] = i+3*j;
			}
		}

		for (int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				float x = b[i][j];
				b[i][j] = b[j][i];
				b[j][i] = x;
			}
		}

		float[][] a1 = r.separa_matriz(a,0,n);
		float[][] a2 = r.separa_matriz(a,n/2,n);
		float[][] b1 = r.separa_matriz(b,0,n);
		float[][] b2 = r.separa_matriz(b,n/2,n);

		float[][] c1 = r.multiplica_matrices(a1,b1,n);
		float[][] c2 = r.multiplica_matrices(a1,b2,n);
		float[][] c3 = r.multiplica_matrices(a2,b1,n);
		float[][] c4 = r.multiplica_matrices(a2,b2,n);

		float[][] c = new float[n][n];
		c = r.acomoda_matriz(c,c1,0,0,n);
		c = r.acomoda_matriz(c,c2,0,n/2,n);
		c = r.acomoda_matriz(c,c3,n/2,0,n);
		c = r.acomoda_matriz(c,c4,n/2,n/2,n);

		if (n == 8) {
			System.out.println("Matriz A");
			for (int i=0;i<n;i++){
				for (int j=0;j<n;j++){
					System.out.print(a[i][j] + "\t");
				}
				System.out.println();
			}

			System.out.println("Matriz B");
			for (int i=0;i<n;i++){
				for (int j=0;j<n;j++){
					System.out.print(b[i][j] + "\t");
				}
				System.out.println();
			}

			System.out.println("Matriz C");
			for (int i=0;i<n;i++){
				for (int j=0;j<n;j++){
					System.out.print(c[i][j] + "\t");
				}
				System.out.println();
			}
		}

		for (int i=0;i<n;i++)
				for (int j=0;j<n;j++)
					checksum += (double)c[i][j];

		System.out.println("Checksum: " + checksum);
	}
}
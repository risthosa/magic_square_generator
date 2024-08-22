import java.util.Scanner;
import java.util.Arrays;

class soru1{
	static void yazdir(int[][] a, int n){
		System.out.println("Satır, sütun ve köşegen toplamları = " + (n*n*n + n)/2 + "\n");
		for(int k = 0; k < n; k++){
			for(int l = 0; l < n; l++)
				System.out.print(a[k][l] + "\t");
			System.out.print("\n\n");
		}
		System.out.println(check(a, n));
	}
	
	static boolean check(int[][] a, int n){
		int toplam = 0;
		for(int i = 0; i<n; i++, toplam = 0){
			for(int j = 0; j<n; j++)
				toplam += a[i][j];
			if(toplam != (n*n*n + n)/2)
				return false;
		}
		
		for(int j = 0; j<n; j++, toplam = 0){
			for(int i = 0; i<n; i++)
				toplam += a[i][j];
			if(toplam != (n*n*n + n)/2)
				return false;
		} 
		
		for(int i = 0; i<n; i++)
			toplam += a[i][i];
		if(toplam != (n*n*n + n)/2)
				return false;
		toplam = 0;

		for(int i = 0; i < n; i++)
			toplam += a[i][i+(n-i*2)-1];
 		
 		if(toplam != (n*n*n + n)/2)
				return false;
		return true;
	}
					
	//Sayıların artırımlı olarak, bazen kırılan, ana köşegeni kesen şeritler üzerinde matriste konumlanması
	//prensibine göre çalışır.
	static void oddOrder(int n){
		int i = n/2, j = n-1;
		int[][] matrix = new int[n][n];
		
		for(int num = 1; num <= n*n;){	
			if(i == -1)
				i = n-1;
			if(j == n)
				j = 0;
				
			matrix[i][j] = num++;
			
			if(num % n == 1)
				j--;
			else{
				i--;
				j++;
			}
		}
		yazdir(matrix, n);						
	}
	//Matrisin konumları sıralı şekilde doldurulur ve bazı satır ve sütun kesitleri
	//dikey ve yatay eksene simetrik olarak yer değiştirir.
	//Matris elemanlarının yarısı sıralı düzende kalır.
	static void evenOrder(int n){
		int[][] matrix = new int [n][n];
		int tmp = 0;
		for(int i = 0; i < n; i++) // ---> Matris sıralı şekilde doldurulur.
			for(int j = 0; j < n; j++)
				matrix[i][j] = i*n + j + 1;
		
		for(int i = 0; i < n/4; i++){  // ---> Swap işlemleri burada yapılıyor.
			for(int j = n/4; j < n-n/4; j++){
				tmp = matrix[i][j];
				matrix[i][j] = matrix[n-i-1][n-j-1];	  
				matrix[n-i-1][n-j-1] = tmp;
				
				tmp = matrix[j][i];
				matrix[j][i] = 	matrix[n-j-1][n-i-1];
				matrix[n-j-1][n-i-1] = tmp;
			}
		}
		yazdir(matrix, n);
	}
	
	static void singlyEvenOrder(int n){
		int[][] map = new int[n/2][n/2], matrix = new int[n][n];
		int k = 0 , l = n/4, m = (n-2)/4, tmp = 0;
		for(int num = 1; num < n*n-2; num += 4){
			if(k == -1)
				k = (n/2)-1;
			if(l == (n/2))
				l = 0;
				
			map[k][l] = num;

			if((num+3) % (2*n) == 0)
				k++;
			else{
				k--;
				l++;
			}
		}
		
		for(int i = 0; i < m*2+1; i+=2)
			for(int j = 1; j < n; j+=2){
				matrix[i][j] = map[i/2][(j-1)/2];
				matrix[i+1][j-1] = matrix[i][j]+1;
				matrix[i+1][j] = matrix[i][j]+2;
				matrix[i][j-1] = matrix[i][j]+3;
			}
			
		tmp = matrix[2*m][2*m];
		matrix[2*m][2*m] = matrix[2*m][2*m+1];
		matrix[2*m][2*m+1] = tmp;
		
		for(int j = 0; j < n-1; j+=2){
			matrix[2*m+2][j] = map[m+1][j/2];
			matrix[2*m+3][j] = map[m+1][j/2]+1; 		
			matrix[2*m+3][j+1] = map[m+1][j/2]+2;
			matrix[2*m+2][j+1] = map[m+1][j/2]+3;
		}
		
		tmp = matrix[2*m+2][2*m];
		matrix[2*m+2][2*m] = matrix[2*m+2][2*m+1];
		matrix[2*m+2][2*m+1] = tmp;
		
		for(int i = 2*m+4; i < n-1; i+=2)
			for(int j = 0; j < n-1; j+=2){
				matrix[i][j] = map[i/2][j/2];
				matrix[i+1][j+1] = map[i/2][j/2]+1;
				matrix[i+1][j] = map[i/2][j/2]+2;
				matrix[i][j+1] = map[i/2][j/2]+3;
			}	
		yazdir(matrix, n);			
	}	

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.print("Boyut: ");
		int n = scan.nextInt();
		
		if(n < 1)
			System.out.println("Bu girdi için matris hesaplanamıyor.");
		else if(n % 4 == 0)
			evenOrder(n);
		else if(n % 2 == 0)
			singlyEvenOrder(n);		
		else
			oddOrder(n);
	}
}

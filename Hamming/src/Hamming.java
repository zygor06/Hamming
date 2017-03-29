import java.util.*;

class Hamming {
	public static void main(String args[]) {
		Scanner leitor = new Scanner(System.in);
		System.out.println("Informe o numero de bits dos dados:");
		int num = leitor.nextInt();
		int array[] = new int[num];
		
		for(int i=0 ; i < num ; i++) {
			System.out.println("Informe o bit de numero " + (num-i) + ":");
			array[num-i-1] = leitor.nextInt();
		}
		
		System.out.println("Sua entrada é:");
		for(int i=0 ; i < num ; i++) {
			System.out.print(array[num-i-1]);
		}
		System.out.println();
		
		int b[] = gerarCodigo(array);
		
		System.out.println("O código gerado é:");
		for(int i=0 ; i < b.length ; i++) {
			System.out.print(b[b.length-i-1]);
		}
		System.out.println();
		
		System.out.println("Entre com a posição do bit para alterar e fazer a checagem de detecção de erro(0 para não mostrar erro):");
		int erro = leitor.nextInt();
		if(erro != 0) {
			b[erro-1] = (b[erro-1]+1)%2;
		}
		System.out.println("O código enviado é:");
		for(int i=0 ; i < b.length ; i++) {
			System.out.print(b[b.length-i-1]);
		}
		System.out.println();
		receptor(b, b.length - array.length);
		
		leitor.close();
	}
	
	static int[] gerarCodigo(int array[]) {
		
		int b[];
		
		
		int i=0, contador_paridade=0 ,j=0, k=0;
		while(i < array.length) {
						
			if(Math.pow(2,contador_paridade) == i+contador_paridade + 1) {
				contador_paridade++;
			}
			else {
				i++;
			}
		}
		
		b = new int[array.length + contador_paridade];
		
		
		for(i=1 ; i <= b.length ; i++) {
			if(Math.pow(2, j) == i) {
				
				b[i-1] = 2;
				j++;
			}
			else {
				b[k+j] = array[k++];
			}
		}
		for(i=0 ; i < contador_paridade ; i++) {
			
			b[((int) Math.pow(2, i))-1] = getParidade(b, i);
		}
		return b;
	}
	
	static int getParidade(int b[], int p) {
		int paridade = 0;
		for(int i=0 ; i < b.length ; i++) {
			if(b[i] != 2) {
				
				int k = i+1;
				String s = Integer.toBinaryString(k);
				
				
				int x = ((Integer.parseInt(s))/((int) Math.pow(10, p)))%10;
				if(x == 1) {
					if(b[i] == 1) {
						paridade = (paridade+1)%2;
					}
				}
			}
		}
		return paridade;
	}
	
	static void receptor(int array[], int contador_paridade) {
		
		int p;
		
		int paridade[] = new int[contador_paridade];
		
		String string = new String();
		
		for(p=0 ; p < contador_paridade ; p++) {
			
			for(int i=0 ; i < array.length ; i++) {
				
				int k = i+1;
				String s = Integer.toBinaryString(k);
				int bit = ((Integer.parseInt(s))/((int) Math.pow(10, p)))%10;
				if(bit == 1) {
					if(array[i] == 1) {
						paridade[p] = (paridade[p]+1)%2;
					}
				}
			}
			string = paridade[p] + string;
		}
		
		int local_erro = Integer.parseInt(string, 2);
		if(local_erro != 0) {
			System.out.println("O erro está na posição " + local_erro + ".");
			array[local_erro-1] = (array[local_erro-1]+1)%2;
			System.out.println("O código correto é:");
			for(int i=0 ; i < array.length ; i++) {
				System.out.print(array[array.length-i-1]);
			}
			System.out.println();
		}
		else {
			System.out.println("Não há erros no dado recebido.");
		}
		
		System.out.println("O dado original foi:");
		p = contador_paridade-1;
		for(int i=array.length ; i > 0 ; i--) {
			if(Math.pow(2, p) != i) {
				System.out.print(array[i-1]);
			}
			else {
				p--;
			}
		}
		System.out.println();
	}
}
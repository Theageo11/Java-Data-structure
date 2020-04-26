package hw2;

public class Complexity {
	
	public static void method1(int n){
		for(int i=1;i<n;i++){
			for(int x=1;x<n;x++){
				System.out.println("I love"+i+"and"+x);
				}
			}
	}
	public static void method2(int n){
		for(int i=1;i<n;i++){
			for(int x=1;x<n;x++){
				for(int z=1;z<n;z++){
					System.out.println("I love"+i+"and"+x+"and"+z);
					}
				}
			}
	}
	public static void method3(int n){
		for(int i=1;i<=n;i=i*2){
			System.out.println("I love"+i);
		}
	}
	public static void method4(int n){
		for(int i=1;i<=n;i++){
			for(int x=1;x<10;x=x*2){
				System.out.println("I love"+i+"and"+x);
			}
		}
	}
	public static void method5(int n){
		int x = 0;
		 for (double j = 2; j < n; j = j * j) {
			 System.out.println("Operation " + x);
			 x++;
		}
		 System.out.println("I love " + x);
		 
	}
	public static void method6(int n){
		for (int i=1;i<=Math.pow(2,n);i++){
			System.out.println("I love"+i);
		}
	}

	public static void main(String[] args) {
		Complexity m1=new Complexity();
		m1.method1(3);
		m1.method2(3);
		m1.method3(3);
		m1.method4(3);
		m1.method5(3);
		m1.method6(3);
		// TODO Auto-generated method stub

	}

}

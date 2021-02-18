import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Burcu_Olmez_2017510084 {

	public static double greedyP2(int demands[], int inv[][],int B,int c,int t) {

		double income=0;
		double half=0;
		double money=0;
		double add=0; //second half of the money
		double faiz=0;
		double maxC=0;
		int wk=0;
		double money2=0;
		boolean[] flag= new boolean[c]; //default false
		boolean flag2=false;

		for (int i = 0; i < demands.length; i++) {
			half=demands[i]*B/2;
			for (int k = 0; k < inv[i].length; k++) { //to find bank which has the best interest
				if(inv[i][k]>maxC) {
					maxC=inv[i][k];
					wk=k;
				}
			}
			for (int j = 0; j < flag.length; j++) { //to change bank if needed 
				if (j!=wk && flag[j]==true) { //if our bank hasnt the most interest
					flag[j]=false;
					flag2=true;
					money2 =money;
					money=money-(money*t/100);
					money+=half+add;
					faiz=(money)*(maxC/100);
					money=money+faiz;
					money2+=half+add; //if we dont change the bank we need to check this possibility
					faiz=money2*(inv[i][j]/100.0);
					money2=money2+faiz;
					if(money<money2) {
						money=money2;
						wk=j;
					}
				}				
			}
			if(flag2==false) { //that means we will check that staying in last bank for this month 
				money+=half+add;
				faiz=money*(maxC/100.0);
				money=money+faiz;
				flag[wk]=true;
			}
			else {
				flag[wk]=true;
			}		
			add=half;
			maxC=0;
			flag2=false;
			//System.out.println(wk+1+"  "+money);
		}
	
	  //System.out.println("final value: "+(money+add));
	  return (money+add);
		
	}
	
	public static double dynamicP2(int demands[], int inv[][],int B,int c,int t,int x) {
		double[][] most = new double[x][c];
	//	int[][] which = new int[x][c]; //to see which company that we had chosed but not necessary for this project
		
		double half=0;
		double money=0;
		double add=0; //second half of the money to add beginning of the next month 
		double faiz=0;
		for (int i = 0; i < most.length; i++) { //we need to search in matrix
			half=demands[i]*B/2;
			for (int j = 0; j < most[i].length; j++) { //to check all possibilities
				if(i==0) { //first month we will calculate initial values
					faiz=(half+add)*inv[i][j]/100; //mathematical operations
					money=half+add+faiz;
					most[i][j]=money;
				//	which[i][j]=j;
				}
				else {
					for (int k = 0; k < most[i-1].length; k++) { //other months
						if(k==j) { //if it is the same company
							faiz=(most[i-1][k]+half+add)*inv[i][j]/100;
							money=most[i-1][k]+half+add+faiz;		
						}
						else { //to cut tax
							money=most[i-1][k]-(most[i-1][k]*t/100);
							money+=half+add;
							faiz=money*inv[i][j]/100;
							money=money+faiz;		
						}
						if(money>most[i][j]) {
							most[i][j]=money;
					//		which[i][j]=k;
						}
					}
				}
			}
			add=half;
		}
		/*
		for (int i = 0; i < most.length; i++) {
			for (int j = 0; j < most[i].length; j++) {	
				if(i==most.length-1) {
				System.out.print((most[i][j]+add)+"   ");
				}
				else {
					System.out.print(most[i][j]+"   ");
				}
			}
			System.out.println();
		}
		*/
			double ret=0; //find the most profitable result
			for (int j = 0; j < most[most.length-1].length; j++) {	
				if(most[most.length-1][j]>ret) {
					ret=most[most.length-1][j]+add;
				}
			}
			return ret;
		
		
		/*
		for (int i = 0; i < which.length; i++) {
			for (int j = 0; j < which[i].length; j++) {
			
				System.out.print(which[i][j]+"   ");
			}
			System.out.println();
			
		}
		*/
	}
	
	public static double dynamicP1(int x,int p,int d, int[][]cost, int R,int demands[]) {

		int money;
		int temp;
		int left;
		for (int i = 1; i <=demands.length; i++){
			for (int k = 0; k <=R; k++) { //to add cars for demands
				temp=demands[i-1]+k;
				for (int j = 0; j <= R; j++) { // to check garage
					if((temp-j-p)<0) {  // if the value which all cars - garage- certain amount of cars is less than zero, they have two check possibilities 
						if(temp-p>=0) { //to keep them in garage
							left=j-(temp-p); 
							money=cost[i-1][j]+cost[0][left];
						}
						else { //to produce and use just for demand
							money = cost[i-1][k];
						}
					}
					else { //normal situation, we hire inetrns
						money=cost[i-1][j]+(temp-p-j)*d+cost[0][k];
						
					}
					if(money<cost[i][k])
						cost[i][k]=money;
				}
			}
		}

		/* to see all values inside of cost array
		for (int i = 0; i < cost.length; i++) {
			for (int j = 0; j < cost[i].length; j++) {
				System.out.print(cost[i][j]+"\t");
			}
			System.out.println();
		}
		*/
		//to pick less money
			double ret=Integer.MAX_VALUE;
			for (int j = 0; j < cost[cost.length-1].length; j++)
				if(cost[cost.length-1][j]<ret) 
					ret=cost[cost.length-1][j];
			
		
		return ret;
	
	}
	
	public static double greedyP1(int[]demands,int[][]cost,int d,int p) {
		int money=0;
		int total=Integer.MAX_VALUE;

		for (int i =0 ; i <demands.length; i++){

			if(p<demands[i]){
				if(i==0) { //first month we can get cars from garage if it is logical
					int left= demands[i]-p;
					for (int j = 0; j <=left; j++) {
						money=cost[0][j]+((left-j)*d);
						if(money<total) {
							total=money;
						}
					}

				}
				else { //normally we will produce all of them its convenient way
					money=(demands[i]-p)*d;
					total+=money;

				}
			}
		}
		return total;
	}
	
	public static void main(String[] args) throws IOException {
		///changeable variables
		int x=25; //months 
		int p=2; //count of monthly produced cars 
		int d=2; //money for interns
		int B=100; //cost a car
		int c=6; //company offers
		int t=2; //taxes
		//////
		
		
		//reads files
	    File file = new File("month_demand.txt");  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String st; 
		int[]demands= new int[x];
		String[]splitted= new String[2];
		br.readLine();
		for (int i = 0; i < demands.length; i++) {
			if((st=br.readLine())!=null) {
				splitted = st.split("	");
				demands[i]=Integer.parseInt(splitted[1]);
			}
		}

		File file2 = new File("investment.txt");  
		br = new BufferedReader(new FileReader(file2)); 
		int[][] inv=new int[x][c];
		String[] split= new String[7];
		br.readLine();
		for (int i = 0; i < inv.length; i++) {
			if((st=br.readLine())!=null) {
				split = st.split("	");
				for (int j = 0; j < inv[i].length; j++) {
					inv[i][j]=Integer.parseInt(split[j+1]);
				}
			}
		}
		int R=0; // sum of all the demands 
		for (int i = 0; i < x; i++) {
			R+=demands[i];
		}
		int[][] cost = new int[x+1][R+1];
		File file3 = new File("garage_cost.txt");  
		br = new BufferedReader(new FileReader(file3)); 
		st=""; 
		splitted= new String[2];
		br.readLine();

		for (int j = 1; j < cost[0].length; j++) {
			if((st=br.readLine())!=null) {
				splitted = st.split("	");
				cost[0][j]=Integer.parseInt(splitted[1]);

			}	
		}
		for (int i = 1; i < cost.length; i++) {
			for (int j = 0; j < cost[i].length; j++) {
				cost[i][j]=Integer.MAX_VALUE;
			}
		}
		System.out.println("Part2 dynamic: "+dynamicP2(demands, inv, B, c, t, x));
		System.out.println("Part1 dynamic: "+dynamicP1(x, p, d, cost, R, demands));
		System.out.println("Part2 greedy: "+greedyP2(demands, inv, B, c, t));
		System.out.println("Part1 greedy: "+greedyP1(demands,cost,d,p));
		
		System.out.println("DP results: "+(dynamicP2(demands, inv, B, c, t, x)-dynamicP1(x, p, d, cost, R, demands)));
		System.out.println("Greedy results: "+(greedyP2(demands, inv, B, c, t)-greedyP1(demands,cost,d,p)));
	}
	

}

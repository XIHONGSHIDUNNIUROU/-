package alignment;
import java.io.BufferedReader;
import java.io.InputStreamReader;  
import java.io.IOException;

public class SequenceAlignment {
	 //代价模型
	 public static final int MATCH = 0;     
	 public static final int MISS = 1;     
	 public static final int EMPTY = 2; 
	 
	 public static void main(String args[]) throws IOException {
		 String X,Y;  
	     BufferedReader r = new BufferedReader(new InputStreamReader(System.in));  
	     System.out.println("Please enter one sequence from A,C,G and T:");  
	     X = r.readLine();  
	     System.out.println("Please enter another sequence from A,C,G and T:");  
	     Y = r.readLine();  
	     int x=X.length(), y = Y.length();
	     long start=System.nanoTime();
	     int opt[][] = new int[x+1][y+1];
	     //填表
	     opt[x][y]=0;
	     for(int i=0;i<x;i++) 
	    	 opt[i][y]=EMPTY*(x-i);
	     for(int j=0;j<y;j++) 
	    	 opt[x][j]=EMPTY*(y-j);
	     int minxy = Math.min(x,y);
	      //横纵排填充
	     for(int k=1;k<=minxy;k++){
	    	 for(int i=x-1;i>=0;i--){
	    		 opt[i][y-k] = Min(opt, i, y-k, X, Y);
	           }
	    	 for(int j=y-1;j>=0;j--){
	             opt[x-k][j] = Min(opt, x-k,j, X, Y);
	           }
	      }
	     //填充剩余空格
	     if(minxy==y) {
	    	 for(int k=x-minxy;k>=0;k--){
		          opt[k][0] = Min(opt, k, 0, X , Y);
		        }
	     }
	       else {
	    	   for(int k=y-minxy;k>=0;k--){
		           opt[0][k] = Min(opt, 0, k, X, Y);
	    	   }
	       }
	     System.out.println(opt[0][0]);//输出比对最小代价值(即最优比对序列结果对应的代价值)
	     //回溯找到最优比对序列结果
	     backtrack(opt,x,y,X,Y);
	     long end=System.nanoTime();
	     System.out.println("The runing time is "+(end-start)+"ns");
	 }
	 
	 public static int Min(int opt[][], int x, int y, String X, String Y) {
		 int m,n;
		 if(X.charAt(x)==Y.charAt(y))
			 m=MATCH;
		 else
			 m=MISS;
	     n = Math.min(opt[x][y+1]+EMPTY, (opt[x+1][y]+EMPTY));
	     return Math.min(n, opt[x+1][y+1]+m);
	 }
	 
	 public static int backtrack(int opt[][], int x, int y, String X, String Y){
		 int i=0, j=0;
		 int l;
	     String a1="", a2="";
	     while(i<x&&j<y) {
	    	 if(X.charAt(i) == Y.charAt(j))
	    		 l=MATCH;
	    	 else
	    		 l=MISS;
	         if(opt[i][j]==opt[i+1][j]+EMPTY){
	                a1+=X.charAt(i);
	                a2+='-';
	                i=i+1;
	            }
	         else if(opt[i][j]==opt[i][j+1]+EMPTY){
	                a1+='-';
	                a2+=Y.charAt(j);
	                j=j+1;
	            }
	         else if(opt[i][j] == l + opt[i + 1][j + 1]) {
	                a1 += X.charAt(i);
	                a2 += Y.charAt(j);
	                i = i + 1;
	                j = j + 1;
	            }
	        }
	     
	     int difference = a1.length()-a2.length();
	     for(int t=0;t<-difference;t++) 
	    	 a1+='-';
	     for(int t=0;t<difference;t++) 
	         a2+='-';
	     System.out.println(a1);
	     System.out.println(a2);
	     return 0;
	 }
}

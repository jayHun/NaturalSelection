package file_project;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class Source {

	public static void main(String[] args) throws FileNotFoundException, IOException{
		int s,m=0;
		String str=null;
		String [] temp=new String[1000];
		int [] remain;
		int [] buffer;
		int [] reserve;
		int [][] runfile;
		FileReader fwin = new FileReader("./input.txt");
		BufferedReader bufw = new BufferedReader(fwin);
		
		s=Integer.parseInt(bufw.readLine());
		m=Integer.parseInt(bufw.readLine());
		
		buffer=new int[s];
		reserve=new int[s];
		runfile=new int[(2*m)+1][];
		str=bufw.readLine();
		temp=str.split(" ");
		remain=new int[temp.length];
		for(int i=0; i<temp.length; i++){
			remain[i] = Integer.parseInt(temp[i]);
		}
		
		for(int i=0; i<remain.length; i++){
			System.out.print(remain[i]+ " ");
		}
		System.out.println("");
		System.out.println(s);
		System.out.println(m);
		bufw.close();
		fwin.close();
		return;
	}
	
	
	public static void nat_select(int remain[], int buffer[], int reserve[], int runfile[][]){
		
		List<int[]> runfile_list=new ArrayList<int[]>();
		
		List<Integer> remain_list=new ArrayList<>();
		for(int i : remain){
			remain_list.add(remain[i]);
		}
		
		int res_full=0;
		int rem_idx=0;
		int run_num=0;
		int run_idx=0;
		do{
			if(remain.length<5){
				Arrays.sort(remain);
				for(int i=0; i<remain.length; i++){
				
				}
			}
		}while(remain!=null && reserve!=null);
		
	}
}

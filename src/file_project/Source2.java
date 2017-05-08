package file_project;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Source2 {

	public static void main(String[] args) throws FileNotFoundException, IOException{
		int s,m=0;
		String str=null;
		String [] temp=new String[1000];
		int [] remain;
		ArrayList<Integer> buffer_list = new ArrayList<Integer>();
		ArrayList<Integer> reserve_list = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> rungroup_list=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> runchild_list=new ArrayList<Integer>();
		ArrayList<Integer> remain_list=new ArrayList<Integer>();
		
		FileReader fwin = new FileReader("./input.txt");
		BufferedReader bufw = new BufferedReader(fwin);
		
		s=Integer.parseInt(bufw.readLine());
		m=Integer.parseInt(bufw.readLine());
		
		str=bufw.readLine();
		temp=str.split(" ");
		remain=new int[temp.length];
		
		
		for(int i=0; i<temp.length; i++){
			remain[i] = Integer.parseInt(temp[i]);
		}
		
		for(int i=0; i<remain.length; i++){
			remain_list.add(remain[i]);
		}
		
		
		nat_select(rungroup_list, runchild_list, reserve_list, buffer_list, remain_list, s);
		
		System.out.println("생성된 런");
		for(int i=0; i<rungroup_list.size(); i++){
			System.out.print("run" + (i+1) + ": ");
			for(int j=0; j<runchild_list.size(); j++){
				System.out.print(rungroup_list.get(i).get(j) + " ");
			}
			System.out.println("");
		}
		
		bufw.close();
		fwin.close();
		return;
	}
	
	
	public static void nat_select(ArrayList<ArrayList<Integer>> rungroup_list, ArrayList<Integer> runchild_list, 
			ArrayList<Integer> reserve_list, ArrayList<Integer> buffer_list, ArrayList<Integer> remain_list, int s){
		
		boolean freeze_flag=false;
		boolean res_full;
		
		// 초기 버퍼에 s개의 레코드 분배
		for(int i=0; i<s; i++){
			buffer_list.add(remain_list.get(0));
			remain_list.remove(0);
		}
		
		/*
		for(int i=0; i<buffer_list.size(); i++){
			System.out.print(buffer_list.get(i) + " ");
		}
		System.out.println("");
		for(int i=0; i<remain_list.size(); i++){
			System.out.print(remain_list.get(i) + " ");
		}
		System.out.println("");
		for(int i=0; i<runchild_list.size(); i++){
			System.out.print(runchild_list.get(i) + " ");
		}
		*/
		//런 하나 생성
		do{	
			// 초기 런에 버퍼의 최소값 삽입, 남은 파일에서 첫번째 값 버퍼에 다시 삽입
			runchild_list.add(Collections.min(buffer_list));
			buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));
			buffer_list.add(remain_list.get(0));
			remain_list.remove(0);
			res_full=false;
			do{
				freeze_flag=true;
				do{
					if(!remain_list.isEmpty()){
						if(Collections.min(buffer_list) >= runchild_list.get(runchild_list.size()-1)){
							runchild_list.add(Collections.min(buffer_list));
							buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));	//버퍼에서 동결값 삭제
							buffer_list.add(remain_list.get(0));									//버퍼에 남은 파일의 인덱스 0값 삽입
							remain_list.remove(0);
							freeze_flag=false;
						}else{
							reserve_list.add(Collections.min(buffer_list));								//동결값을 예비 저장소에 저장
							buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));		//버퍼에서 동결값 삭제
							buffer_list.add(remain_list.get(0));										//버퍼에 남은 파일의 인덱스 0값 삽입
							remain_list.remove(0);
							if(reserve_list.size() == s) res_full=true;
						}
					}
				}while(freeze_flag && !res_full && !remain_list.isEmpty());
			}while(!remain_list.isEmpty() && !res_full);
			
			if(res_full && !remain_list.isEmpty()){
				while(Collections.min(buffer_list) >= runchild_list.get(runchild_list.size()-1)){
					runchild_list.add(Collections.min(buffer_list));
					buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));	//버퍼에서 동결값 삭제
					buffer_list.add(remain_list.get(0));									//버퍼에 남은 파일의 인덱스 0값 삽입
					remain_list.remove(0);
					if(remain_list.isEmpty()) break;
				}
				if(Collections.min(buffer_list) < runchild_list.get(runchild_list.size()-1)){
					remain_list.add(0, Collections.min(buffer_list));
					buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));
					for(int i=0; i<buffer_list.size(); i++){
						runchild_list.add(Collections.min(buffer_list));						//마지막 동결 이외의 값들을 런에 분배
						buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));	//버퍼에서 삭제
					}
					buffer_list.addAll(reserve_list);
					rungroup_list.add(runchild_list);
					runchild_list.clear();
					reserve_list.clear();
				}
			}
			
			if(remain_list.isEmpty()){
				if(Collections.min(buffer_list) < runchild_list.get(runchild_list.size()-1)){
					reserve_list.add(Collections.min(buffer_list));
					buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));
					for(int i=0; i<buffer_list.size(); i++){
						runchild_list.add(Collections.min(buffer_list));	//마지막 동결 이외의 값들을 런에 분배
						buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));	//버퍼에서 삭제
					}
					rungroup_list.add(runchild_list);
					runchild_list.clear();
					for(int i=0; i<reserve_list.size(); i++){
						runchild_list.add(Collections.min(reserve_list));
						reserve_list.remove(reserve_list.indexOf(Collections.min(reserve_list)));
					}
					rungroup_list.add(runchild_list);
					runchild_list.clear();
					reserve_list.clear();
				}else{
					for(int i=0; i<buffer_list.size(); i++){
						runchild_list.add(Collections.min(buffer_list));						//마지막 동결 이외의 값들을 런에 분배
						buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));	//버퍼에서 삭제
					}
					rungroup_list.add(runchild_list);
					runchild_list.clear();
					for(int i=0; i<reserve_list.size(); i++){
						runchild_list.add(Collections.min(reserve_list));
						reserve_list.remove(reserve_list.indexOf(Collections.min(reserve_list)));
					}
					rungroup_list.add(runchild_list);
					runchild_list.clear();
					reserve_list.clear();
				}
			}
		}while(!buffer_list.isEmpty() && !remain_list.isEmpty());
	}
}
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
		
		System.out.println("������ ��");
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
		
		// �ʱ� ���ۿ� s���� ���ڵ� �й�
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
		//�� �ϳ� ����
		do{	
			// �ʱ� ���� ������ �ּҰ� ����, ���� ���Ͽ��� ù��° �� ���ۿ� �ٽ� ����
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
							buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));	//���ۿ��� ���ᰪ ����
							buffer_list.add(remain_list.get(0));									//���ۿ� ���� ������ �ε��� 0�� ����
							remain_list.remove(0);
							freeze_flag=false;
						}else{
							reserve_list.add(Collections.min(buffer_list));								//���ᰪ�� ���� ����ҿ� ����
							buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));		//���ۿ��� ���ᰪ ����
							buffer_list.add(remain_list.get(0));										//���ۿ� ���� ������ �ε��� 0�� ����
							remain_list.remove(0);
							if(reserve_list.size() == s) res_full=true;
						}
					}
				}while(freeze_flag && !res_full && !remain_list.isEmpty());
			}while(!remain_list.isEmpty() && !res_full);
			
			if(res_full && !remain_list.isEmpty()){
				while(Collections.min(buffer_list) >= runchild_list.get(runchild_list.size()-1)){
					runchild_list.add(Collections.min(buffer_list));
					buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));	//���ۿ��� ���ᰪ ����
					buffer_list.add(remain_list.get(0));									//���ۿ� ���� ������ �ε��� 0�� ����
					remain_list.remove(0);
					if(remain_list.isEmpty()) break;
				}
				if(Collections.min(buffer_list) < runchild_list.get(runchild_list.size()-1)){
					remain_list.add(0, Collections.min(buffer_list));
					buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));
					for(int i=0; i<buffer_list.size(); i++){
						runchild_list.add(Collections.min(buffer_list));						//������ ���� �̿��� ������ ���� �й�
						buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));	//���ۿ��� ����
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
						runchild_list.add(Collections.min(buffer_list));	//������ ���� �̿��� ������ ���� �й�
						buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));	//���ۿ��� ����
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
						runchild_list.add(Collections.min(buffer_list));						//������ ���� �̿��� ������ ���� �й�
						buffer_list.remove(buffer_list.indexOf(Collections.min(buffer_list)));	//���ۿ��� ����
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
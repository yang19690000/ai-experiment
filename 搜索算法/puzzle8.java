package project_java1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class board{
	public int h;
	public int f;
	public board pre=null;
	public int[][] dot=new int[3][3];
	public int[] pos=new int[2];
	public static int depth0(board b) {
		if(b==null)
			return 0;
		else
			return depth0(b.pre)+1;
	}
	public int depth() {
		return depth0(this);
	}
	public board(int[][] dot) {
		this.dot=dot;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++)
			{
				if(dot[i][j]==0){
					this.pos[0]=i;
					this.pos[1]=j;
				}
			}}
			
	}
	//判断两个节点是否相等
	public Boolean equal(board a) {
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				if(dot[i][j]!=a.dot[i][j])
					return false;
			}
		return true;
	}
	public static board up(int[][] dot,int[] pos) {
		int[][] res=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				res[i][j]=dot[i][j];
		int temp;
		temp=res[pos[0]][pos[1]];
		res[pos[0]][pos[1]]=res[pos[0]-1][pos[1]];
		res[pos[0]-1][pos[1]]=temp;
		return new board(res);
	}
	public static board down(int[][] dot,int[] pos) {
		int[][] res=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				res[i][j]=dot[i][j];
		int temp;
		temp=res[pos[0]][pos[1]];
		res[pos[0]][pos[1]]=res[pos[0]+1][pos[1]];
		res[pos[0]+1][pos[1]]=temp;
		return new board(res);
	}
	public static board left(int[][] dot,int[] pos) {
		int[][] res=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				res[i][j]=dot[i][j];
		int temp;
		temp=res[pos[0]][pos[1]];
		res[pos[0]][pos[1]]=res[pos[0]][pos[1]-1];
		res[pos[0]][pos[1]-1]=temp;
		return new board(res);
	}
	public static board right(int[][] dot,int[] pos) {
		int[][] res=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				res[i][j]=dot[i][j];
		int temp;
		temp=res[pos[0]][pos[1]];
		res[pos[0]][pos[1]]=res[pos[0]][pos[1]+1];
		res[pos[0]][pos[1]+1]=temp;
		return new board(res);
	}
	//返回所有子节点
	public ArrayList<board> move() {
		ArrayList<board> res=new ArrayList<>();
		//四个顶点
		if(pos[0]==0 && pos[1]==0)
		{
			res.add(right(dot,pos));
			res.add(down(dot,pos));
		}
		if(pos[0]==0 && pos[1]==2)
		{
			res.add(left(dot,pos));
			res.add(down(dot,pos));
		}
		if(pos[0]==2 && pos[1]==0)
		{
			res.add(right(dot,pos));
			res.add(up(dot,pos));
		}
		if(pos[0]==2 && pos[1]==2)
		{
			res.add(left(dot,pos));
			res.add(up(dot,pos));
		}
		//四个中点
		if(pos[0]==0 && pos[1]==1)
		{
			res.add(right(dot,pos));
			res.add(left(dot,pos));
			res.add(down(dot,pos));
		}
		if(pos[0]==1 && pos[1]==0)
		{
			res.add(up(dot,pos));
			res.add(down(dot,pos));
			res.add(right(dot,pos));
		}
		if(pos[0]==2 && pos[1]==1)
		{
			res.add(right(dot,pos));
			res.add(left(dot,pos));
			res.add(up(dot,pos));
		}
		if(pos[0]==1 && pos[1]==2)
		{
			res.add(up(dot,pos));
			res.add(down(dot,pos));
			res.add(left(dot,pos));
		}
		//一个中心
		if(pos[0]==1 && pos[1]==1)
		{
			res.add(right(dot,pos));
			res.add(down(dot,pos));
			res.add(left(dot,pos));
			res.add(up(dot,pos));
		}
		return res;
	}
}
public class puzzle8 {
	public static int inversion(board b) {
		int[] arr=new int[9];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				arr[i*3+j]=b.dot[i][j]; 
		int res=0;
		for(int i=0;i<9;i++) {
			if(arr[i]==0)
				continue;
			for(int j=0;j<i;j++) {
				if(arr[j]>arr[i])
					res++;
			}
		}
		return res%2;
	}
	public static Boolean contain(ArrayList<board> a,board b) {
		for (board c : a) {
			if(b.equal(c))
				return true;
		}
		return false;
	}
	public static int[][] b={{1,2,3},{8,0,4},{7,6,5}};
	public static board target= new board(b);
	//输出棋盘状态
	public static void print(board b) {
		int[][] a=b.dot;
		System.out.println("--------------");
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++)
			{
				System.out.print(a[i][j]+" ");
			}
		System.out.println();
		}
		System.out.println("--------------");
		
	}
	//启发函数h(n)，求错牌个数
	public static int h(board dot) {
		int ans=0;
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				if(dot.dot[i][j]!=target.dot[i][j])
					ans+=1;
			}
		return ans;
	}
	public static ArrayList<board> bfs(board now) throws InterruptedException {
		ArrayList<board> open=new ArrayList<board>();
		ArrayList<board> closed=new ArrayList<board>();
		int times=0;
		open.add(now);
		while(open.size()!=0) {
			times++;
			if(times%10000==0)
			{System.out.println("已经尝试了"+(times)+"次");
			print(open.get(0));}
//			TimeUnit.SECONDS.sleep(1);
//			System.out.println("open.size="+open.size());
//			System.out.println("closed.size="+closed.size());
//			System.out.println("!!!!!!!open");

			board X=open.get(0);
			open.remove(0);
			if(X.equal(target)) {
				ArrayList<board> res=new ArrayList<board>();
				res.add(X);
				while(X.pre!=null)
					{res.add(0,X.pre);
					X=X.pre;
					}
				return res;
			}
			else {
				ArrayList<board> children=X.move();
				closed.add(X);
				ArrayList<board> remove_boards=new ArrayList<>();
				for (board a : children) {
					if(contain(open,a)||contain(closed,a)) {
						remove_boards.add(a);
					}
					else
						a.pre=X;
				}
				children.removeAll(remove_boards);
				open.addAll(children);
				
			}
			
		}
		return (new ArrayList<board>());
	}
	public static ArrayList<board> dfs(board now) throws InterruptedException {
		ArrayList<board> sl=new ArrayList<board>();
		ArrayList<board> nsl=new ArrayList<board>();
		ArrayList<board> de=new ArrayList<board>();
		board cs=now;
		sl.add(now);
		nsl.add(now);
		int times=0;
		while(nsl.size()!=0) {
			
			times++;
			System.out.println("已经尝试了"+(times)+"次");
			print(cs);
			if(cs.equal(target))
				return sl;
			ArrayList<board> children=cs.move();
		
			ArrayList<board> remove_boards=new ArrayList<board>();
			for (board b : children) {
				if(contain(de,b)||contain(sl,b)||contain(nsl,b)) {
					remove_boards.add(b);
					
				}
			}
			children.removeAll(remove_boards);
			if(children.size()==0) {

				while(sl.size()!=0 && cs.equal(sl.get(0))) {
			
					for (board t : de) {
					
						print(t);
					}
					
					de.add(cs);
					sl.remove(0);
					nsl.remove(0);
					cs=nsl.get(nsl.size()-1);
				}
				sl.add(cs);
			}
			else {
				nsl.addAll(children);
				cs=nsl.get(nsl.size()-1);
				sl.add(cs);
			}
		}
		
		return new ArrayList<board>();
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<board> best_first_search(board now) throws InterruptedException {
		ArrayList<board> open=new ArrayList<board>();
		ArrayList<board> closed=new ArrayList<board>();
		open.add(now);
		int times=0;
		while(open.size()!=0) {
			times++;
			if(times%10000==0)
			{System.out.println("已经尝试了"+(times)+"次");
			print(open.get(0));}
			board X=open.get(0);
			open.remove(0);
			if(X.equal(target)) {
				ArrayList<board> res=new ArrayList<board>();
				res.add(X);
				while(X.pre!=null)
					{res.add(0,X.pre);
					X=X.pre;
					}
				return res;
			}
			else {
				ArrayList<board> children=X.move();
				closed.add(X);
				ArrayList<board> remove_boards=new ArrayList<>();
				for (board a : children) {
					if(contain(open,a)||contain(closed,a)) {
						remove_boards.add(a);
					}
					else
						{a.pre=X;
						a.h=h(a);
						a.f=a.depth();
						}
				}
				children.removeAll(remove_boards);
				
				open.addAll(children);
				
				//对children列表，按启发值排序,启发值越小越好
				Collections.sort(open, new Comparator() { 
					@Override 
					public int compare(Object o1, Object o2) { 
					
					board b1=(board)o1;
					board b2=(board)o2;
					return (b1.f+b1.h)-(b2.f+b2.h);
				}
				});
//				System.out.println("open排序结果");
//				for(board a:open) {
//					System.out.println("启发函数:"+(a.f+a.h));
//				}
//				System.out.println("排序结束");

				
			}
			
		}
		return (new ArrayList<board>());
	}
	public static void main(String[] args) throws InterruptedException {
		System.out.println("请以排列的形式，输入初始状态:");
		Scanner input=new Scanner(System.in);
		int[][] a=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				a[i][j]=input.nextInt();	
		input.close();
		board in=new board(a);
		if(inversion(in)!=inversion(target)) {
			System.out.println("input逆序数="+inversion(in));
			System.out.println("target逆序数="+inversion(target));
			System.out.println("当前状态无法到达目标状态！");
			return ;
		}
		//ArrayList<board> res=dfs(in);
		//ArrayList<board> res=bfs(in);
		ArrayList<board> res=best_first_search(in);
		System.out.println("一共需要"+res.size()+"步移动");
		if(res.size()==0)
			System.out.println("当前状态无解");
		else
		{
			for (board b : res) 
			{
				print(b);
			}
		}
		
	
	}
}

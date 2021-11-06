package project_java1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class chess{
	public int h;
	public int f;
	public chess pre=null;
	public int[][] dot=new int[3][3];
	public int turn;
	public static int depth0(chess b) {
		if(b==null)
			return 0;
		else
			return depth0(b.pre)+1;
	}
	
	public int depth() {
		return depth0(this);
	}
	public chess(int[][] dot) {
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				this.dot[i][j]=dot[i][j];
		int n0=0,n1=1;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++)
			{	
				
				if(dot[i][j]==0)
					n0++;
				else if(dot[i][j]==1)
					n1++;
			}
		}
		if(n0==n1 || n0>n1)
			turn=1;
		else
			turn=0;
			
	}
	//判断两个节点是否相等
	public Boolean equal(chess a) {
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				if(dot[i][j]!=a.dot[i][j])
					return false;
			}
		return true;
	}
	
	//返回所有子节点
	public ArrayList<chess> move(){
		ArrayList<chess> res=new ArrayList<chess>();
		int[][] temp=new int[3][3];
		//temp=dot
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				temp[i][j]=dot[i][j];
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				if(dot[i][j]==-1){
					temp[i][j]=turn;
				
					res.add(new chess(temp));
					temp[i][j]=dot[i][j];
				}
			}
		return res;
	}
}
public class wordChess {
	public static int row(chess c) {
		for(int i=0;i<3;i++){
			if(c.dot[i][0]==c.dot[i][1]&&c.dot[i][0]==c.dot[i][2])
				return c.dot[i][0];
		}
		return -1;
	}
	public static int col(chess c) {
		for(int i=0;i<3;i++){
			if(c.dot[0][i]==c.dot[1][i]&&c.dot[0][i]==c.dot[2][i])
				return c.dot[0][i];
		}
		return -1;
	}
	public static int diag(chess c) {
		if(c.dot[0][0]==c.dot[1][1]&&c.dot[1][1]==c.dot[2][2])
			return c.dot[0][0];
		else if(c.dot[0][2]==c.dot[1][1]&&c.dot[1][1]==c.dot[2][0])
			return c.dot[0][2];
		return -1;
	}
	//判断游戏结果
	public static int judge(chess c) {
		int[] res= {row(c),col(c),diag(c)};
		for(int i=0;i<3;i++)
			if(res[i]!=-1)
				return res[i];
		return -1;
	}
	public static Boolean contain(ArrayList<chess> a,chess b) {
		for (chess c : a) {
			if(b.equal(c))
				return true;
		}
		return false;
	}

	//输出棋盘状态
	public static void print(chess b) {
		int[][] a=b.dot;
		System.out.println("--------------");
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++)
			{	
				System.out.printf("%2d ",a[i][j]);
			}
		System.out.println();
		}
		System.out.println("--------------");
		
	}
	public static int E(chess c,int k) {
		int res=8;
		int temp;
		if(k==0)
			temp=1;
		else
			temp=0;
		for(int i=0;i<3;i++) {
			if(temp==c.dot[i][1]||temp==c.dot[i][0]||temp==c.dot[i][2])
				res--;
			if(temp==c.dot[1][i]||temp==c.dot[0][i]||temp==c.dot[2][i])
				res--;
		}
		if(temp==c.dot[0][0]||temp==c.dot[2][2])
			res--;
		if(temp==c.dot[2][0]||temp==c.dot[0][2])
			res--;
		return res;
	}
	//启发函数h(n)=E(X)-E(O),求可能的胜利路线条数差值
	public static int h(chess dot) {
		return E(dot,1)-E(dot,0);
	}
	public static ArrayList<chess> bfs(chess now) throws InterruptedException {
		ArrayList<chess> open=new ArrayList<chess>();
		ArrayList<chess> closed=new ArrayList<chess>();
		int times=0;
		open.add(now);
		while(open.size()!=0) {
			times++;
			if(times%10==0)
			{System.out.println("已经尝试了"+(times)+"次");
			print(open.get(0));}
			
			chess X=open.get(0);
			open.remove(0);
			int j=judge(X);
			if(j==1) {
				ArrayList<chess> res=new ArrayList<chess>();
				res.add(X);
				while(X.pre!=null)
					{res.add(0,X.pre);
					X=X.pre;
					}
				System.out.println("X胜利");
				return res;
			}
			else {
				ArrayList<chess> children=X.move();
				
				closed.add(X);
				ArrayList<chess> remove_chesss=new ArrayList<>();
				for (chess a : children) {
					if(contain(open,a)||contain(closed,a)) {
						remove_chesss.add(a);
					}
					else
						a.pre=X;
				}
				children.removeAll(remove_chesss);
				open.addAll(children);
				
			}
			
		}
		return (new ArrayList<chess>());
	}
	public static ArrayList<chess> dfs(chess now) throws InterruptedException {
		ArrayList<chess> sl=new ArrayList<chess>();
		ArrayList<chess> nsl=new ArrayList<chess>();
		ArrayList<chess> de=new ArrayList<chess>();
		chess cs=now;
		sl.add(now);
		nsl.add(now);
		
		while(nsl.size()!=0) {
			
			if(judge(cs)==1)
				return sl;
			ArrayList<chess> children=cs.move();
		
			ArrayList<chess> remove_chesss=new ArrayList<chess>();
			for (chess b : children) {
				if(contain(de,b)||contain(sl,b)||contain(nsl,b)) {
					remove_chesss.add(b);
					
				}
			}
			children.removeAll(remove_chesss);
			if(children.size()==0) {

				while(sl.size()!=0 && cs.equal(sl.get(0))) {
			
					for (chess t : de) {
					
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
		
		return new ArrayList<chess>();
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<chess> best_first_search(chess now) throws InterruptedException {
		ArrayList<chess> open=new ArrayList<chess>();
		ArrayList<chess> closed=new ArrayList<chess>();
		open.add(now);
		int times=0;
		while(open.size()!=0) {
			times++;
			if(times%10000==0)
			{System.out.println("已经尝试了"+(times)+"次");
			print(open.get(0));}
			chess X=open.get(0);
			open.remove(0);
			if(judge(X)==1) {
				ArrayList<chess> res=new ArrayList<chess>();
				res.add(X);
				while(X.pre!=null)
					{res.add(0,X.pre);
					X=X.pre;
					}
				return res;
			}
			else {
				ArrayList<chess> children=X.move();
				closed.add(X);
				ArrayList<chess> remove_chesss=new ArrayList<>();
				for (chess a : children) {
					if(contain(open,a)||contain(closed,a)) {
						remove_chesss.add(a);
					}
					else
						{a.pre=X;
						a.h=h(a);
						a.f=a.depth();
						}
				}
				children.removeAll(remove_chesss);
				
				open.addAll(children);
				
				//对children列表，按启发值排序,启发值越小越好
				Collections.sort(open, new Comparator() { 
					@Override 
					public int compare(Object o1, Object o2) { 
					
					chess b1=(chess)o1;
					chess b2=(chess)o2;
					return (b1.f+b1.h)-(b2.f+b2.h);
				}
				});
//				System.out.println("open排序结果");
//				for(chess a:open) {
//					System.out.println("启发函数:"+(a.f+a.h));
//				}
//				System.out.println("排序结束");

				
			}
			
		}
		return (new ArrayList<chess>());
	}
	public static void main(String[] args) throws InterruptedException {
		System.out.println("请以排列的形式，输入初始状态:");
		System.out.println("-1:空 , 0:O , 1:X");
		Scanner input=new Scanner(System.in);
		int[][] a=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				a[i][j]=input.nextInt();	
		input.close();
		
		chess in=new chess(a);
		print(in);
		//ArrayList<chess> res=dfs(in);
		//ArrayList<chess> res=bfs(in);
		ArrayList<chess> res=best_first_search(in);
		System.out.println("一共需要"+(res.size()-1)+"步移动");
		if(res.size()==0)
			System.out.println("当前状态无解");
		else
		{
			for (chess b : res) 
			{
				print(b);
			}
		}
		
	
	}
}

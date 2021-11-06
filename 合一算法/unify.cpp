#include <iostream> 
#include <string> 
#include <vector> 
using namespace std;
int MN = 0; //标记公式集中括号匹配数 
class Unify {
private:
    struct TF {  // 一个代换(差异集) 
        string t_f1;
        string t_f2;
    };
public:
    bool  HY(string F1, string F2, vector<TF>& t);   //是否能合一 
    TF different(const string F1, const string F2)   //求差异集 
    {
        int i = 0;
        TF t;
        while (F1.at(i) == F2.at(i))
            i++;
        int j1 = i;
        while (j1 < F1.length() - 1 && F1.at(j1) != ',')
            j1++;
        if (j1 - i == 0) return t;
        t.t_f1 = F1.substr(i, j1 - i);
        int j2 = i;
        while (j2 < F2.length() - 1 && F2.at(j2) != ',')
            j2++;
        if (j2 - i == 0) return t;
        t.t_f2 = F2.substr(i, j2 - i);
        while (t.t_f1[j1 - i - 1] == t.t_f2[j2 - i - 1])
        {
            t.t_f1.erase(j1 - 1 - i);
            t.t_f2.erase(j2 - i - 1);
            j1--;
            j2--;
        }
        return t;
    }
    bool same(const string F1, const string F2);      // 判断两个公式是否相同 
    string change(string f, TF t); //用代换q对公式f进行合一代换     // 
    bool legal(TF& t); 
	int var(const string s);  //s中每个()内的子串是变量还是常量 
    void show();//最终演示算法  
};

bool Unify::HY(string F1, string F2, vector<TF>& lan)
{
    while (!same(F1, F2))
    {
        TF t = different(F1, F2);
        bool flag =  legal(t);
        if (!flag)
            return false;
        else
        {
            lan.push_back(t);   //将t加入 vector lan中 
            if (flag)
            {
                F1 = change(F1, lan.back());   //用lan的最后一个元素代换 
                F2 = change(F2, lan.back());
                cout << "变换后:" << endl;
                cout << "F1:" << F1 << endl;
                cout << "F2:" << F2 << endl << endl;
            }
            if (same(F1, F2)) break;
        }
    }
    return true;
}

bool Unify::same(const string F1, const string F2)
{
    if (F1.compare(F2) == 0) return true;
    else return false;
}

string Unify::change(string f, TF t)
{
    int i = f.find(t.t_f2);
    while (i < f.length())
    {
        i = f.find(t.t_f2);
        if (i < f.length())   f = f.replace(i, t.t_f2.length(), t.t_f1);
    }
    return f;
}

bool Unify::legal(TF& t)
{
    if (t.t_f1.length() == 0 || t.t_f2.length() == 0)
        return false;
    else if (var(t.t_f1) == 0 || var(t.t_f2) == 0) return false;
    else if (var(t.t_f1) == 1 && var(t.t_f2) == 1 && t.t_f1.compare(t.t_f2) != 0)
        return false;
    else if (var(t.t_f1) == 2)
    {
        if (var(t.t_f2) == 1)
        {
            string temp = t.t_f1;    //变量常量交换位置 
            t.t_f1 = t.t_f2;
            t.t_f2 = temp;
        }
        else
        {
            int i1 = var(t.t_f2);
            i1 = MN;
            MN = 0;
            int i2 = var(t.t_f1);
            i2 = MN;
            if (i1 < i2)
            {
                string temp = t.t_f1;    //变量常量交换位置 
                t.t_f1 = t.t_f2;
                t.t_f2 = temp;
            }
        }
        return true;
    }
    else
        return true;
}

int Unify::var(const string s)
{
    if (s.length() == 0) return 0; //空 
    if (s.length() == 1 && s[0] >= 'a' && s[0] <= 'g')  return 1; //常量 


    if (s.length() > 1)
    {
        int i = 0;
        while (i < s.length() && s.at(i) != '(')
            i++;
        MN++;
        string ss = s.substr(i + 1, s.length() - i - 2); //抽取s中的匹配的()中的子串 
        return var(ss);
    }
    else return 2;
}

void Unify::show()
{
    int i;
	cout << "常量:a,b,c,d(a-g)等" << endl << "变量:x,y,z,u等" << endl;
    string F1, F2;
    cout << "输入 第一个表达式:";
    cin >> F1;
    cout << "输入 第二个表达式:";
    cin >> F2;
    vector <TF> lan;
    if (HY(F1, F2, lan))
    {
        if (same(F1, F2))       //如果F1,F2相同则合一为ε 
        {
            cout << "合一式=ε" << endl;
            return;
        }
        cout << "合一式={ ";
        for (i = 0; i < lan.size() - 1; i++)
            cout << lan[i].t_f1 << "/" << lan[i].t_f2 << ",";
        cout << lan[i].t_f1 << "/" << lan[i].t_f2 << " }" << endl;
    }
    else
        cout << "不能进行合一" << endl;
}

int main()
{
    Unify Sy;
    Sy.show();
    return 0;
}


#include <iostream> 
#include <string> 
#include <vector> 
using namespace std;
int MN = 0; //��ǹ�ʽ��������ƥ���� 
class Unify {
private:
    struct TF {  // һ������(���켯) 
        string t_f1;
        string t_f2;
    };
public:
    bool  HY(string F1, string F2, vector<TF>& t);   //�Ƿ��ܺ�һ 
    TF different(const string F1, const string F2)   //����켯 
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
    bool same(const string F1, const string F2);      // �ж�������ʽ�Ƿ���ͬ 
    string change(string f, TF t); //�ô���q�Թ�ʽf���к�һ����     // 
    bool legal(TF& t); 
	int var(const string s);  //s��ÿ��()�ڵ��Ӵ��Ǳ������ǳ��� 
    void show();//������ʾ�㷨  
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
            lan.push_back(t);   //��t���� vector lan�� 
            if (flag)
            {
                F1 = change(F1, lan.back());   //��lan�����һ��Ԫ�ش��� 
                F2 = change(F2, lan.back());
                cout << "�任��:" << endl;
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
            string temp = t.t_f1;    //������������λ�� 
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
                string temp = t.t_f1;    //������������λ�� 
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
    if (s.length() == 0) return 0; //�� 
    if (s.length() == 1 && s[0] >= 'a' && s[0] <= 'g')  return 1; //���� 


    if (s.length() > 1)
    {
        int i = 0;
        while (i < s.length() && s.at(i) != '(')
            i++;
        MN++;
        string ss = s.substr(i + 1, s.length() - i - 2); //��ȡs�е�ƥ���()�е��Ӵ� 
        return var(ss);
    }
    else return 2;
}

void Unify::show()
{
    int i;
	cout << "����:a,b,c,d(a-g)��" << endl << "����:x,y,z,u��" << endl;
    string F1, F2;
    cout << "���� ��һ�����ʽ:";
    cin >> F1;
    cout << "���� �ڶ������ʽ:";
    cin >> F2;
    vector <TF> lan;
    if (HY(F1, F2, lan))
    {
        if (same(F1, F2))       //���F1,F2��ͬ���һΪ�� 
        {
            cout << "��һʽ=��" << endl;
            return;
        }
        cout << "��һʽ={ ";
        for (i = 0; i < lan.size() - 1; i++)
            cout << lan[i].t_f1 << "/" << lan[i].t_f2 << ",";
        cout << lan[i].t_f1 << "/" << lan[i].t_f2 << " }" << endl;
    }
    else
        cout << "���ܽ��к�һ" << endl;
}

int main()
{
    Unify Sy;
    Sy.show();
    return 0;
}


import java.util.Arrays;

public class Matrix {
    Data data=new Data();
    public int[][] oblicz_Zysk(int[] sprzedaz, int[][]transport, int[] zakup){
        int [][] zysk = new int[2][2];
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                zysk[i][j]=sprzedaz[j]-transport[i][j] - zakup[i];
            }
            System.out.println(Arrays.toString(zysk[i]));
        }
        return zysk;
    }
    public int[][] mNEM(int [] O,int[] D, int[][]zysk,int [][] tab){ //metoda najwiekszego elementu macierzy

        int max=-4000,maxi=0,maxj=0;
        for (int i =0;i<2;i++){
            for (int j =0; j<2; j++){
                if (max<zysk[i][j]&& O[j]!=0 && D[i]!=0){
                    max=zysk[i][j];
                    maxi=i;
                    maxj=j;
                }
            }
        }
        if(max == -4000){
            O= data.getO();
            D=data.getD();
            return tab;
        }
        if(O[maxj]>D[maxi]&&D[maxi]>0){
            tab[maxi][maxj]=D[maxi];
            O[maxj]-=D[maxi];
            D[maxi]=0;

        }else if(O[maxj]<=D[maxi]&&O[maxj]>0){
            tab[maxi][maxj] = O[maxj];
            D[maxi] -= O[maxj];
            O[maxj] = 0;
        }

        return mNEM(O,D,zysk,tab);
    }

    public int [][] wsk_opt(int[][]zysk,int[][] tab){
        int alfa1=0;
        int[]alfa=new int[2];
        int[]beta=new int[2];
        alfa[0]=alfa1;

        for(int i=0;i<2;i++){
            if(tab[0][i]>0){
                beta[i]=zysk[0][i]-alfa[0];
                if(tab[1][i]>0){
                    alfa[1]=zysk[1][i]-beta[i];
                }
            }
        }
        System.out.println("Alfa "+Arrays.toString(alfa));
        System.out.println("Beta "+Arrays.toString(beta));
        int [][]wsk = zysk;
        for(int i = 0;i<2;i++){
            for(int j=0;j<2;j++){
                wsk[i][j]-=alfa[i];
                wsk[i][j]-=beta[j];
            }
        }
        System.out.println(Arrays.toString(wsk[0]));
        System.out.println(Arrays.toString(wsk[1]));
        return wsk;
    }
}

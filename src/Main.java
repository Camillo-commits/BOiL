import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String [] args){
        Data data=new Data();
        int[][] tablica=new int[2][2];
        Matrix matrix=new Matrix();
        int[][] zysk=matrix.oblicz_Zysk(data.getCs(),data.getKt(),data.getKz());
        tablica=matrix.mNEM(data.getO(),data.getD(),zysk,tablica);
        System.out.println("Pierwsza iteracja!!");
        for(int i=0;i<2;i++){
            System.out.println(Arrays.toString(tablica[i]));
        }
        matrix.wsk_opt(zysk,tablica);


    }
}

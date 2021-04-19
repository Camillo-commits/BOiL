package com.example.application.solver;


import com.example.application.modules.Cycle;
import com.example.application.modules.Dostawca;
import com.example.application.modules.Odbiorca;
import com.example.application.modules.ZyskAndResult;

import java.util.LinkedList;
import java.util.List;

public class Solver {
    public static ZyskAndResult[][] solve(List<Dostawca> dostawcy, List <Odbiorca> odbiorcy, double[][] trasnport, ZyskAndResult[][] zysk, boolean first){
        //ZyskAndResult[][] zysk = new ZyskAndResult[trasnport.length][trasnport[0].length];

        if(trasnport[0].length != odbiorcy.size() || trasnport.length != dostawcy.size()){
            try {
                throw new Exception("Wrong input data size");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(first) {
            for (int kolumna = 0; kolumna < trasnport[0].length; ++kolumna) {
                for (int wiersz = 0; wiersz < trasnport.length; ++wiersz) {
                    if (trasnport[wiersz][kolumna] != 999999999999999.0) {
                        zysk[wiersz][kolumna] = new ZyskAndResult(false, 0);
                        zysk[wiersz][kolumna].setZyskValue(odbiorcy.get(kolumna).getSprzedaż() - dostawcy.get(wiersz).getCenaZakupu() - trasnport[wiersz][kolumna]);
                    } else {
                        zysk[wiersz][kolumna] = new ZyskAndResult(false, 0);
                        zysk[wiersz][kolumna].setZyskValue(0.0);
                    }
                }
            }
            //first fit
            for (int i = 0; i < trasnport.length * trasnport[0].length; ++i) {
                int[] indexes = findMaxZysk(zysk);
                zysk[indexes[0]][indexes[1]].setMaxAmountValue(dostawcy.get(indexes[0]), odbiorcy.get(indexes[1]));
            }
        }
        //alfa & beta
        double[] alfa = new double[dostawcy.size()];
        double[] beta = new double[odbiorcy.size()];
        double[][] optimalArr = new double[zysk[0].length][zysk.length];
        for(double a : alfa){
            a = 99999999999999.0;
        }
        for(double b : beta){
            b = 99999999999999.0;
        }
        alfa[0] = 0;
        // for with k is necessary although i dont know how many times XD
        for(int k = 0; k < dostawcy.size() * odbiorcy.size();++k) {
            for (int i = 0; i < dostawcy.size(); ++i) {
                for (int j = 0; j < odbiorcy.size(); ++j) {
                    if (zysk[i][j].isUsed()) {
                        if (beta[j] == 99999999999999.0 && alfa[i] != 99999999999999.0)
                            beta[j] = zysk[i][j].getZyskValue() - alfa[i];
                        else if (beta[j] != 99999999999999.0 && alfa[i] == 99999999999999.0)
                            alfa[i] = zysk[i][j].getZyskValue() - beta[j];
                    }
                }
            }
        }
        //generating optimalArr
        for(int i = 0; i < dostawcy.size();++i){
            for(int j = 0; j < odbiorcy.size(); ++j){
                if(!zysk[i][j].isUsed()){
                    optimalArr[i][j] = zysk[i][j].getZyskValue() - alfa[i] - beta[j];
                }
            }
        }

        //checking if this is the end
        boolean end = true;
        int[] wrongNumberIndex = new int[2];
        for(int i = 0; i < optimalArr.length; ++i){
            for(int j = 0; j < optimalArr[0].length; ++j){
                if(optimalArr[i][j] > 0){
                    end = false;
                    wrongNumberIndex[0] = i;
                    wrongNumberIndex[1] = j;
                }
            }
        }

        if(!end){
            cycle(zysk,optimalArr,wrongNumberIndex);
            solve(dostawcy,odbiorcy,trasnport,zysk,false);
        }
        /*System.out.println("zysk table");
        for(ZyskAndResult[] ZARArr : zysk) {
            for (ZyskAndResult ZAR : ZARArr) {
                System.out.print(ZAR.getAmountValue() + ", ");
            }
            System.out.println("");
        }
        System.out.println("alfa");
        System.out.print("[ ");
        for(double a : alfa){
            System.out.print(a + ", ");
        }
        System.out.println("]");

        System.out.println("beta");
        System.out.print("[ ");
        for(double a : beta){
            System.out.print(a + ", ");
        }
        System.out.println("]");

        System.out.println("optimalArr");
        for(double[] optimArr : optimalArr) {
            for (double d : optimArr) {
                System.out.print(d + ", ");
            }
            System.out.println("");
        }

         */
        return zysk;
    }

    private static void cycle(ZyskAndResult[][] zysk,double[][] optimalArr, int[] startIndex){
        //finding smallest path
        int[] size = {optimalArr.length,optimalArr[0].length};
        List<List<Cycle>> cyclesList = new LinkedList<>();
        int up = 1;
        int right = 1;
        int left = 1;
        int down = 1;

        //1.1 AKA pierwsza ćwiartka
        while(startIndex[0] + right < size[1]) {    //should it be size[0] or size[1]
            while (startIndex[1] - up > 0) {
                List<Cycle> cycles = new LinkedList<>();
                cycles.add(new Cycle(startIndex[0], startIndex[1], optimalArr[startIndex[1]][startIndex[0]]));
                cycles.add(new Cycle(startIndex[0], startIndex[1] - up, optimalArr[startIndex[1] - up][startIndex[0]]));
                cycles.add(new Cycle(startIndex[0] + right, startIndex[1], optimalArr[startIndex[1]][startIndex[0] + right]));
                cycles.add(new Cycle(startIndex[0], startIndex[1] + up, optimalArr[startIndex[1] + up][startIndex[0]]));
                ++up;
                cyclesList.add(cycles);
            }
            up = 1;
            ++right;
        }
        right = 1;
        //1.2 AKA druga ćwiartka
        while(startIndex[0] - left > 0 ) {    //should it be size[0] or size[1]
            while (startIndex[1] - up > 0) {
                List<Cycle> cycles = new LinkedList<>();
                cycles.add(new Cycle(startIndex[0], startIndex[1], optimalArr[startIndex[1]][startIndex[0]]));
                cycles.add(new Cycle(startIndex[0], startIndex[1] - up, optimalArr[startIndex[1] - up][startIndex[0]]));
                cycles.add(new Cycle(startIndex[0] - left, startIndex[1], optimalArr[startIndex[1]][startIndex[0] - left]));
                cycles.add(new Cycle(startIndex[0], startIndex[1] + up, optimalArr[startIndex[1] + up][startIndex[0]]));
                ++up;
                cyclesList.add(cycles);
            }
            up = 1;
            ++left;
        }
        left = 1;
        //1.3 AKA trzecia ćwiartka
        while(startIndex[0] - left > 0 ) {    //should it be size[0] or size[1]
            while (startIndex[1] + down < size[0]) {
                List<Cycle> cycles = new LinkedList<>();
                cycles.add(new Cycle(startIndex[0], startIndex[1], optimalArr[startIndex[1]][startIndex[0]]));
                cycles.add(new Cycle(startIndex[0], startIndex[1] + down, optimalArr[startIndex[1] + down][startIndex[0]]));
                cycles.add(new Cycle(startIndex[0] - left, startIndex[1], optimalArr[startIndex[1]][startIndex[0] - left]));
                cycles.add(new Cycle(startIndex[0], startIndex[1] - down, optimalArr[startIndex[1] - down][startIndex[0]]));
                ++down;
                cyclesList.add(cycles);
            }
            down = 1;
            ++left;
        }
        left = 1;
        //1.4 AKA czwarta ćwiartka
        while(startIndex[0] + right < size[1]) {    //should it be size[0] or size[1]
            while (startIndex[1] + down < size[0]) {
                List<Cycle> cycles = new LinkedList<>();
                cycles.add(new Cycle(startIndex[0], startIndex[1], optimalArr[startIndex[1]][startIndex[0]]));
                cycles.add(new Cycle(startIndex[0], startIndex[1] + down, optimalArr[startIndex[1] + down][startIndex[0]]));
                cycles.add(new Cycle(startIndex[0] + right, startIndex[1], optimalArr[startIndex[1]][startIndex[0] + right]));
                cycles.add(new Cycle(startIndex[0], startIndex[1] - down, optimalArr[startIndex[1] - down][startIndex[0]]));
                ++down;
                cyclesList.add(cycles);
            }
            down = 1;
            ++right;
        }
        right = 1;
        int minimalIndex = -1;
        double minVal = 0.0;
        boolean first = true;
        for(List<Cycle> list : cyclesList){
            double sum = 0.0;

            for(Cycle c : list){
                sum += c.getValue();
                if(first){
                    minVal += c.getValue();
                    minimalIndex = 0;
                }
            }
            first = false;
            if(sum < minVal){
                minimalIndex = cyclesList.indexOf(list);
            }
        }
        // in minimalIndex is the right cycle
        List<int[]> toAdd = new LinkedList<>();
        List<int[]> toSubtract = new LinkedList<>();

        int[] tmp = new int[2];
        // X
        tmp[0] = cyclesList.get(minimalIndex).get(0).getX();
        // Y
        tmp[1] = cyclesList.get(minimalIndex).get(0).getY();
        toAdd.add(tmp);

        tmp[0] = cyclesList.get(minimalIndex).get(2).getX();
        tmp[1] = cyclesList.get(minimalIndex).get(2).getY();
        toAdd.add(tmp);

        // X
        tmp[0] = cyclesList.get(minimalIndex).get(1).getX();
        // Y
        tmp[1] = cyclesList.get(minimalIndex).get(1).getY();
        toSubtract.add(tmp);

        tmp[0] = cyclesList.get(minimalIndex).get(3).getX();
        tmp[1] = cyclesList.get(minimalIndex).get(3).getY();
        toSubtract.add(tmp);

        int subtractValue = 0;
        if(zysk[toSubtract.get(0)[0]][toSubtract.get(0)[1]].getAmountValue() < zysk[toSubtract.get(1)[0]][toSubtract.get(1)[1]].getAmountValue())
            subtractValue = zysk[toSubtract.get(0)[0]][toSubtract.get(0)[1]].getAmountValue();
        else
            subtractValue = zysk[toSubtract.get(1)[0]][toSubtract.get(1)[1]].getAmountValue();

        zysk[toAdd.get(0)[0]][toAdd.get(0)[1]].setAmountValue(zysk[toAdd.get(0)[0]][toAdd.get(0)[1]].getAmountValue() + subtractValue);
        zysk[toAdd.get(1)[0]][toAdd.get(1)[1]].setAmountValue(zysk[toAdd.get(1)[0]][toAdd.get(1)[1]].getAmountValue() + subtractValue);
        zysk[toSubtract.get(0)[0]][toSubtract.get(0)[1]].setAmountValue(zysk[toSubtract.get(0)[0]][toSubtract.get(0)[1]].getAmountValue() - subtractValue);
        zysk[toSubtract.get(1)[0]][toSubtract.get(1)[1]].setAmountValue(zysk[toSubtract.get(1)[0]][toSubtract.get(1)[1]].getAmountValue() - subtractValue);
    }

    private static int[] findMaxZysk(ZyskAndResult[][] zysk){
        int[] retVal = new int[2];
        if(zysk == null){
            throw new NullPointerException("zysk = null");
        }
        double max = -999999999999999999999999.0;
        int wierszIndex = -1;
        int kolumnaIndex = -1;
        double ficMax = max;
        int wierszFicMax = wierszIndex;
        int kolumnaFicMax = kolumnaIndex;
        Boolean end = false;
        for(int kolumna = 0; kolumna < zysk[0].length; ++kolumna) {
            if(end){
                break;
            }
            for (int wiersz = 0; wiersz < zysk.length; ++wiersz) {
                if(!zysk[wiersz][kolumna].isUsed()){
                    if(!zysk[wiersz][kolumna].isFictional()) {
                        max = zysk[wiersz][kolumna].getZyskValue();
                        wierszIndex = wiersz;
                        kolumnaIndex = kolumna;
                        end = true;
                        break;
                    }
                    else{
                        ficMax = zysk[wiersz][kolumna].getZyskValue();
                        wierszFicMax = wiersz;
                        kolumnaFicMax = kolumna;
                    }
                }

            }
        }
        //there still is nonFictionalData
        if(max != -999999999999999999999999.0) {
            for (int kolumna = 0; kolumna < zysk[0].length; ++kolumna) {
                for (int wiersz = 0; wiersz < zysk.length; ++wiersz) {
                    if (max < zysk[wiersz][kolumna].getZyskValue() && !zysk[wiersz][kolumna].isUsed() && !zysk[wiersz][kolumna].isFictional()) {
                        wierszIndex = wiersz;
                        kolumnaIndex = kolumna;
                        max = zysk[wiersz][kolumna].getZyskValue();
                        //zysk[wiersz][kolumna].setUsed(true);
                    }
                }
            }
        }
        //fictionalData
        else{
            for (int kolumna = 0; kolumna < zysk[0].length; ++kolumna) {
                for (int wiersz = 0; wiersz < zysk.length; ++wiersz) {
                    if (max < zysk[wiersz][kolumna].getZyskValue() && !zysk[wiersz][kolumna].isUsed()) {
                        wierszIndex = wiersz;
                        kolumnaIndex = kolumna;
                        max = zysk[wiersz][kolumna].getZyskValue();
                        //zysk[wiersz][kolumna].setUsed(true);
                    }
                }
            }
        }
        retVal[0] = wierszIndex;
        retVal[1] = kolumnaIndex;
        zysk[wierszIndex][kolumnaIndex].setUsed(true);
        return retVal;
    }
}

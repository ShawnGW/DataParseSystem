package com.vtest.it.adjacentFailDieCheck;

import com.vtest.it.rawdataParse.parseRawdata;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class DieCheck {
    public static void main(String[] args) {
        File file=new File("E:\\AK86RN.1\\NKLLL19-A3.raw");
        parseRawdata parseRawdata=new parseRawdata(file);
        String[][] dieMap=parseRawdata.getSoftBinTestDiesDimensionalArray();
        int row=Integer.valueOf(parseRawdata.getProperties().get("TestDieRow"));
        int col=Integer.valueOf(parseRawdata.getProperties().get("TestDieCol"));
        DieCheck dieCheck=new DieCheck();
        ArrayList<String> passBins=new ArrayList<>();
        passBins.add("1");
        long start=System.currentTimeMillis();
        Set<String> set=dieCheck.check(dieMap, row, col, passBins,11,1);
        long end=System.currentTimeMillis();
        System.out.println("***************************");
        for (String coordinate : set) {
            System.out.println(String.format("%-4s", coordinate.split(":")[1])+String.format("%-4s", (Integer.valueOf(coordinate.split(":")[0])+1)));
        }
        System.out.println();
        System.out.println("times: "+(end-start));
    }
    public Set<String> check(String[][] dieMap,int row,int col,ArrayList<String> passBins,int checkNum,int cycleNumber) {
        Set<String> singleDieSet=new HashSet<>();
        Set<String> set=new HashSet<>();
        ArrayList<Set<String>> failDieChains=new ArrayList<>();
        HashSet<String> failDieSet=new HashSet<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (null!=dieMap[i][j]&&!passBins.contains(dieMap[i][j])) {
                    failDieSet.add(i+":"+j);
                }
            }
        }
        for (String failDie : failDieSet) {
            if (!singleDieSet.contains(failDie)) {
                Set<String> initFailDieSet=new HashSet<>();
                initFailDieSet.add(failDie);
                failDieChains.add(initFailDieSet);
                findNextFailDie(dieMap, passBins, initFailDieSet,initFailDieSet,singleDieSet);
            }
        }
        System.out.println("chains size:"+failDieChains.size());
        System.out.println("singleDieSet size: "+singleDieSet.size());
        for (Set<String> node : failDieChains) {
            if (node.size()>=checkNum) {
                System.out.println("......................");
                for (String coordinate : node) {
                    System.out.println(String.format("%-4s", coordinate.split(":")[1])+String.format("%-4s", (Integer.valueOf(coordinate.split(":")[0])+1)));
                    set.add(coordinate);
                }
            }
        }
        Set<String> needCoverDie=inkDie(set, dieMap, passBins, cycleNumber);
        return needCoverDie;
    }
    public void findNextFailDie(String[][] dieMap,ArrayList<String> passBins,Set<String> node,Set<String> needCheckNode,Set<String> singleDieSet) {
        for (String failDie : needCheckNode) {
            if (!singleDieSet.contains(failDie)) {
                int i=Integer.valueOf(failDie.split(":")[0]);
                int j=Integer.valueOf(failDie.split(":")[1]);
                LinkedList<String> adjacentFailDieList=adjacentCheck(i, j, dieMap, passBins,singleDieSet);
                if (adjacentFailDieList.size()>0) {
                    Set<String> needCheckNodeOthers=new HashSet<>();
                    for (String adjacentFailDie : adjacentFailDieList) {
                        if (!singleDieSet.contains(adjacentFailDie)) {
                            node.add(adjacentFailDie);
                            needCheckNodeOthers.add(adjacentFailDie);
                            findNextFailDie(dieMap, passBins, node,needCheckNodeOthers,singleDieSet);
                        }
                    }
                }
            }
        }
    }
    public LinkedList<String> adjacentCheck(int i,int j,String[][] dieMap,ArrayList<String> passBins,Set<String> singleDieSet) {
        singleDieSet.add(i+":"+j);
        LinkedList<String> adjacentFailDieList=new LinkedList<>();
        for (int k = -1; k <=1 ; k++) {
            int tempRow=i+k;
            for (int k2 = -1; k2 <= 1; k2++) {
                int tempCol=j+k2;
                if (k==0&&k2==0) {
                    continue;
                }else {
                    if (tempRow>=0&&tempCol>=0) {
                        try {
                            if (null!=dieMap[tempRow][tempCol]&&!passBins.contains(dieMap[tempRow][tempCol])) {
                                adjacentFailDieList.add(tempRow+":"+tempCol);
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }
            }
        }
        return adjacentFailDieList;
    }
    public Set<String> inkDie(Set<String> set,String[][] dieMap,ArrayList<String> passBins,int cycleNumber) {
        Set<String> needCoverDie=new HashSet<>();
        for (String adjacentDie : set) {
            int i=Integer.valueOf(adjacentDie.split(":")[0]);
            int j=Integer.valueOf(adjacentDie.split(":")[1]);
            for (int k = -cycleNumber; k <=cycleNumber ; k++) {
                int tempRow=i+k;
                for (int k2 = -cycleNumber; k2 <= cycleNumber; k2++) {
                    int tempCol=j+k2;
                    if (k==0&&k2==0) {
                        continue;
                    }else {
                        if (tempRow>=0&&tempCol>=0) {
                            try {
                                if (null!=dieMap[tempRow][tempCol]&&passBins.contains(dieMap[tempRow][tempCol])) {
                                    needCoverDie.add(tempRow+":"+tempCol);
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                        }
                    }
                }
            }
        }
        return needCoverDie;
    }
}

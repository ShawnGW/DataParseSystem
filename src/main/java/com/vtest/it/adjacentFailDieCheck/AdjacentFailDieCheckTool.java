package com.vtest.it.adjacentFailDieCheck;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
public class AdjacentFailDieCheckTool {
    public  ArrayList<Set<String>> check(String[][] dieMap,int row,int col,ArrayList<String> passBins) {
        String[][] finDieMap=new String[row-6][col-6];
        for (int i = 3; i < row-3; i++) {
            for (int j = 3; j < col-3; j++) {
                finDieMap[i-3][j-3]=dieMap[i][j];
            }
        }
        dieMap=finDieMap;
        Set<String> singleDieSet=new HashSet<>();
        ArrayList<Set<String>> failDieChains=new ArrayList<>();
        HashSet<String> failDieSet=new HashSet<>();
        for (int i = 0; i < row-6; i++) {
            for (int j = 0; j < col-6; j++) {
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
        return failDieChains;
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

package com.vtest.it.adjacentFailDieCheck;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FailTypeCheck {
    public boolean eightAdjacentFailDieCheck(Set<String> set)
    {
        Set<Boolean> checkFlagSet = new HashSet<>();
        for (String coordinate : set) {
            boolean flag = true;
            Integer xCoordnate = Integer.valueOf(coordinate.split(":")[0]);
            Integer yCoordnate = Integer.valueOf(coordinate.split(":")[1]);
            if (!set.contains((xCoordnate+1)+":"+yCoordnate))
            {
                flag=false;
            }
            if (!set.contains((xCoordnate-1)+":"+yCoordnate))
            {
                flag=false;
            }
            if (!set.contains((xCoordnate)+":"+(yCoordnate+1)))
            {
                flag=false;
            }
            if (!set.contains((xCoordnate)+":"+(yCoordnate-1)))
            {
                flag=false;
            }
            if (!set.contains((xCoordnate+1)+":"+(yCoordnate+1)))
            {
                flag=false;
            }
            if (!set.contains((xCoordnate-1)+":"+(yCoordnate-1)))
            {
                flag=false;
            }
            if (!set.contains((xCoordnate-1)+":"+(yCoordnate+1)))
            {
                flag=false;
            }
            if (!set.contains((xCoordnate+1)+":"+(yCoordnate-1)))
            {
                flag=false;
            }
            checkFlagSet.add(flag);
        }
        if (checkFlagSet.contains(true))
        {
            return true;
        }
        return  false;
    }
    public boolean fourAdjacentFailDieCheck(Set<String> set)
    {
        Set<Boolean> checkFlagSet = new HashSet<>();
        for (String coordinate : set) {
            boolean flag = true;
            Integer xCoordnate = Integer.valueOf(coordinate.split(":")[0]);
            Integer yCoordnate = Integer.valueOf(coordinate.split(":")[1]);
            if (!set.contains((xCoordnate+1)+":"+yCoordnate))
            {
                flag=false;
            }
            if (!set.contains((xCoordnate-1)+":"+yCoordnate))
            {
                flag=false;
            }
            if (!set.contains((xCoordnate)+":"+(yCoordnate+1)))
            {
                flag=false;
            }
            if (!set.contains((xCoordnate)+":"+(yCoordnate-1)))
            {
                flag=false;
            }
            checkFlagSet.add(flag);
        }
        if (checkFlagSet.contains(true))
        {
            return true;
        }
        return  false;
    }public boolean xDirectionAdjacentFailDieCheck(Set<String> set)
    {
        Set<Boolean> checkFlagSet = new HashSet<>();
        for (String coordinate : set) {
            boolean flag = true;
            Integer xCoordnate = Integer.valueOf(coordinate.split(":")[0]);
            Integer yCoordnate = Integer.valueOf(coordinate.split(":")[1]);
            for (int i = 1; i < 7; i++) {
                if (!set.contains((xCoordnate+i) + ":" +yCoordnate)) {
                    flag = false;
                }
            }
            checkFlagSet.add(flag);
        }
        if (checkFlagSet.contains(true))
        {
            return true;
        }
        return  false;
    }
    public boolean yDirectionAdjacentFailDieCheck(Set<String> set) {
        Set<Boolean> checkFlagSet = new HashSet<>();
        for (String coordinate : set) {
            boolean flag = true;
            Integer xCoordnate = Integer.valueOf(coordinate.split(":")[0]);
            Integer yCoordnate = Integer.valueOf(coordinate.split(":")[1]);
            for (int i = 1; i < 7; i++) {
                if (!set.contains(xCoordnate + ":" + (yCoordnate+i))) {
                    flag = false;
                }
            }
            checkFlagSet.add(flag);
        }
        if (checkFlagSet.contains(true))
        {
            return true;
        }
        return  false;
    }

}

package com.kefu.test;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by wenfeng.zhu on 2019/7/23
 */

public class CardDeck {

    private static final String BLACK_JOKE = "black joke";
    private static final String RED_JOKE = "red joke";

    //init en orderedPokers
    public static LinkedList initOrderedPokers(){
        LinkedList list = new LinkedList();
        String[] colors={"黑桃","红桃","梅花","方块"};
        String[] nums={"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        for(int i=0;i<nums.length;i++){
            for(int j=0;j<colors.length;j++){
                list.add(new Poker(colors[j]+nums[i],getNumber(nums[i])));
            }
        }
        list.add(new Poker(BLACK_JOKE,20));
        list.add(new Poker(RED_JOKE,20));
        return list;
    }


    //init en unorderedPokers
    public static  LinkedList initUnOrderedPokers(){
        LinkedList pokers =initOrderedPokers();
        for(int i= pokers.size()-1; i>0; i--){
            int rand=(new Random()).nextInt(i+1);
            Object temp = pokers.get(rand);
            pokers.set(rand,pokers.get(i));
            pokers.set(i,temp);
        }
        return pokers;
    }


    private static int getNumber(String num) {
        int result= 0;
        switch (num){
            case "A":
                result=1;
                break;
            case "J":
                result=11;
                break;
            case "Q":
                result=12;
                break;
            case "K":
                result=13;
                break;
        }
        return result==0?Integer.parseInt(num):result;
    }

}

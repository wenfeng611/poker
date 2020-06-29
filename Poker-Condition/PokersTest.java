package com.kefu.test;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wenfeng.zhu on 2019/7/23
 */

public class PokersTest {

    private int singal=0;
    private int countA=0;
    private int countB=0;
    private int countC=0;
    Lock lock = new ReentrantLock();
    Condition conditionA=lock.newCondition();
    Condition conditionB=lock.newCondition();
    Condition conditionC=lock.newCondition();
    LinkedList<Poker> list = CardDeck.initUnOrderedPokers();
    boolean hasWin=false;

    public static void main(String[] args){
        PokersTest pokersTest =new PokersTest();
        new Thread(new PersonA(pokersTest)).start();
        new Thread(new PersonB(pokersTest)).start();
        new Thread(new PersonC(pokersTest)).start();
    }

    public void calcCountA(){
        do{
            lock.lock();
            while(singal!=0){
                try {
                    conditionA.await();
                    if(hasWin) return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Poker poker = list.removeFirst();
            countA+=poker.getNum();
            System.out.println("PersonA get " + poker.getName()+ "   countA="+countA);
            singal++;
            if(countA>50){
                hasWin=true;
            }
            conditionB.signal();
            lock.unlock();
        }while(countA<50 && !hasWin);

        System.out.println("countA win");
    }

    public void calcCountB(){
        do{
            lock.lock();
            while (singal != 1) {
                try {
                    conditionB.await();
                    if(hasWin) return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Poker poker = list.removeFirst();
            countB += poker.getNum();
            System.out.println("PersonB get " + poker.getName() + "   countB=" + countB);
            singal++;
            if(countB>50){
                hasWin=true;
            }
            conditionC.signal();
            lock.unlock();
        }while(countB<50 && !hasWin);
        System.out.println("countB win");
    }

    public void calcCountC(){
       do{
            lock.lock();
            while (singal != 2) {
                try {
                    conditionC.await();
                    if(hasWin) return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Poker poker = list.removeFirst();
            countC += poker.getNum();
            System.out.println("PersonC get " +poker.getName() + "   countC=" + countC);
            singal = 0;
           if(countC>50){
               hasWin=true;
           }
            conditionA.signal();
            lock.unlock();
        }while(countC<50 && !hasWin);
        System.out.println("countC win");
    }
}

class PersonA implements Runnable{

    private  PokersTest pokersTest;

    PersonA(PokersTest pokersTest){
        this.pokersTest=pokersTest;
    }

    @Override
    public void run() {
        pokersTest.calcCountA();
    }
}

class PersonB implements Runnable{

    private  PokersTest pokersTest;

    PersonB(PokersTest pokersTest){
        this.pokersTest=pokersTest;
    }

    @Override
    public void run() {
        while (true){
            pokersTest.calcCountB();
        }

    }
}


class PersonC implements Runnable{

    private  PokersTest pokersTest;

    PersonC(PokersTest pokersTest){
        this.pokersTest=pokersTest;
    }

    @Override
    public void run() {
        pokersTest.calcCountC();
    }
}
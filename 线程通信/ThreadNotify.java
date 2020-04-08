package src.com.practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sofency
 * @date 2020/4/8 17:02
 * @package IntelliJ IDEA
 * @description 线程通讯
 * 1. 通过对象唤醒
 * 2. 通过 condition 唤醒
 */
public class ThreadNotify {
    public static void main(String[] args) {
        Number number = new Number();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.printEven();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number.printOdd();
            }
        }).start();
    }
}
class Number {
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();
    private int i=0;
    void printOdd(){//打印偶数
        while(i<10){
            lock.lock();
            try {
                if(i%2==0){
                    System.out.println("偶数"+":"+i);
                    i++;
                    condition.signalAll();//唤醒其他的线程
                }else{
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();//解锁
            }
        }
    }

    void printEven(){//打印偶数
        while(i<10){
            lock.lock();
            try{
                if(i%2==1){
                    System.out.println("奇数"+":"+i);
                    i++;
                    condition.signalAll();//唤醒其他的线程
                }else{
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();//解锁
            }

        }
    }
}
//偶数线程打印偶数  奇数线程打印奇数
//class Number {
//    private Object object = new Object();
//    private int i=0;
//    void printOdd(){//打印偶数
//        synchronized (object){
//            while(i<10){
//                if(i%2==0){
//                    System.out.println("偶数"+":"+i);
//                    i++;
//                    object.notify();//唤醒其他的线程
//                }else{
//                    try {
//                        object.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//    }
//
//    void printEven(){//打印偶数
//        synchronized (object){
//            while(i<10){
//                if(i%2==1){
//                    System.out.println("奇数"+":"+i);
//                    i++;
//                    object.notify();//唤醒其他的线程
//                }else{
//                    try {
//                        object.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//    }
//}
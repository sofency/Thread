package src.com.practice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author sofency
 * @date 2020/4/8 18:37
 * @package IntelliJ IDEA
 * @description  使用CountDOwnLatch
 */
public class ThreadNotifyDemo {

    public static void main(String[] args) {
        CoachAndRacer coachAndRacer = new CoachAndRacer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                coachAndRacer.racer();
            }
        },"student1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                coachAndRacer.racer();
            }
        },"student2").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                coachAndRacer.racer();
            }
        },"student3").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                coachAndRacer.coach();
            }
        },"teacher").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                coachAndRacer.racer();
            }
        },"student4").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                coachAndRacer.racer();
            }
        },"student5").start();
    }
}

class CoachAndRacer {

    CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

    private CountDownLatch countDownLatch = new CountDownLatch(5);//指定等待5个线程执行完
    public void racer(){
        String name = Thread.currentThread().getName();
        System.out.println(name+"正在准备中");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("准备完毕");
        countDownLatch.countDown();
    }


    //教练等待所有线程执行完
    public void coach(){
        String name = Thread.currentThread().getName();
        System.out.println("所有学生开始准备");
        try {
            countDownLatch.await();//等待
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("学生已就绪,"+name+"要开始训练了");
    }


}
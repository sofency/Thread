package src.com.practice;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sofency
 * @date 2020/4/8 14:58
 * @package IntelliJ IDEA
 * @description 线程同步的方法
 * 1. 同步代码块
 * 2 同步锁
 * 3. 同步锁
 */
public class ThreadSynchronous {
    public static void main(String[] args) {
        Ticket ticket = new Ticket(100);
        //三个窗口买票
        TicketWindow ticketWindow = new TicketWindow(ticket);
        new Thread(ticketWindow).start();
        new Thread(ticketWindow).start();
        new Thread(ticketWindow).start();
    }
}
class TicketWindow implements Runnable{
    private Ticket ticket;
    private Lock lock = new ReentrantLock(true);//参数true为公平锁  多个线程公平拥有执行权 false为非公平锁 独占锁
    public TicketWindow(Ticket ticket) {
        this.ticket = ticket;
    }
    @Override
    public void run() {
        //同步方法
        //soldTicket();

        //同步锁
        while (true) {
            lock.lock();
            try {
                if (ticket.getCount() > 0) {
                    ticket.soldTicket1();
                    try {
                        Thread.sleep((long) (Math.random() * 800));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }




        //同步代码块
//        while (true) {
//            synchronized (ticket) {
//                if (ticket.getCount() > 0) {
//                    ticket.soldTicket();
//                    try {
//                        Thread.sleep((long) (Math.random() * 800));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName() + ":" + ticket.getCount());
//                } else {
//                    break;
//                }
//            }
//        }
    }
    public void soldTicket(){
        while (true) {
            boolean flag = ticket.soldTicket();
            if(!flag){
                break;
            }
            try {
                Thread.sleep((long) (Math.random() * 800));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Ticket{
    private int count;
    public Ticket(int count) {
        this.count = count;
    }

    public  boolean soldTicket1(){
        if(count>0){
            count--;//票数减1
            System.out.println(Thread.currentThread().getName() + ":" + count);
            return true;
        }else {
            return  false;
        }
    }

    //同步方法
    public synchronized boolean soldTicket(){
        if(count>0){
            count--;//票数减1
            System.out.println(Thread.currentThread().getName() + ":" + count);
            return true;
        }else {
            return  false;
        }
    }
    public int getCount() {
        return count;
    }
}

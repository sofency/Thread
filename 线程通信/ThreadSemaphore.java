package src.com.practice;

import java.util.concurrent.Semaphore;

/**
 * @author sofency
 * @date 2020/4/8 19:32
 * @package IntelliJ IDEA
 * @description
 */
public class ThreadSemaphore {
    static class Worker implements Runnable{
        private Semaphore semaphore;//信号量

        public Worker(Semaphore semaphore) {
            this.semaphore = semaphore;
        }
        @Override
        public void run() {
            try {
                //工人获取机器
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+"获取到机器开始工作");
                Thread.sleep(2000);
                semaphore.release();//释放机器
                System.out.println(Thread.currentThread().getName()+"使用完毕释放机器");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int workers = 8;
        Semaphore semaphore = new Semaphore(3);
        for(int i=0;i<workers;i++){
            new Thread(new Worker(semaphore),"worker"+i).start();
        }
    }
}

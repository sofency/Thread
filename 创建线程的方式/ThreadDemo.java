package src.com.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author sofency
 * @date 2020/4/8 11:32
 * @package IntelliJ IDEA
 * @description
 */
public class ThreadDemo {
    //创建线程的的方式
    //1. 继承Thread
    //2. 实现Runnable接口
    //3. 实现callable接口
    //4. 使用匿名内部类  也就是lambda表达式
    //5. 使用线程池 创建线程
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //继承Thread
        ThreadDemo1 threadDemo1 = new ThreadDemo1();
        threadDemo1.start();

        //使用runnable
        ThreadDemo2 threadDemo2 = new ThreadDemo2();
        new Thread(threadDemo2).start();

        //使用Callable  结合FutureTask和list集合批量接收线程的返回值  接收的get()是阻塞接收的  线程不执行完是不会执行该方法的
        ThreadDemo3 threadDemo3 = new ThreadDemo3(100);
        ThreadDemo3 threadDemo4 = new ThreadDemo3(200);
        FutureTask<Integer> future = new FutureTask(threadDemo3);
        FutureTask<Integer> future1 = new FutureTask(threadDemo4);
        List<FutureTask<Integer>> list = new ArrayList<>();
        list.add(future);
        list.add(future1);

        for(FutureTask<Integer> futureTask: list){
            new Thread(futureTask).start();
        }

        for(FutureTask<Integer> futureTask: list){
            Integer sum = futureTask.get();
            System.out.println(sum);
        }
        //使用lambda表达式
        new  Thread(() -> {
            System.out.println("lambda表达式");
        }).start();

        //线程池执行
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Thread thread2 = new Thread(new ThreadDemo2());
        executorService.submit(threadDemo1);
        executorService.submit(thread2);//提交线程
    }
    static class  ThreadDemo1 extends Thread{
        @Override
        public void run() {
            System.out.println("继承thread类");
        }
    }
    static class ThreadDemo2 implements Runnable{
        @Override
        public void run() {
            System.out.println("通过实现Runnable接口");
        }
    }
    static class ThreadDemo3 implements Callable<Integer>{
        private int begin;

        public ThreadDemo3(int begin) {
            this.begin = begin;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("实现callable接口");
            int sum =0;
            for(int i=0;i<begin;i++){
                sum+=i;
            }
            return sum;
        }
    }
}

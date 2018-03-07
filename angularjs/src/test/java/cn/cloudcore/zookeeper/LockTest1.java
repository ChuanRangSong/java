package cn.cloudcore.zookeeper;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest1 {

    public static void main(String[] args) {
        test1();

    }

    public static void test1() {
        Ticket ticket = new Ticket(1000);

        new Thread(() -> {
            while (ticket.getSum() > 0) {
                synchronized (ticket) {
                    ticket.setSum(ticket.getSum() - 1);
                    System.out.println(Thread.currentThread() + "----" + ticket.getSum());
                }
            }
        }).start();

        new Thread(() -> {
            while (ticket.getSum() > 0) {
                synchronized (ticket) {
                    ticket.setSum(ticket.getSum() - 1);
                    System.out.println(Thread.currentThread() + "----" + ticket.getSum());
                }
            }
        }).start();
    }



    public static void test() {
        List<Integer> num = new ArrayList<>(1);
        num.add(0);

        Lock lock = new ReentrantLock();

        Condition consumeCondition = lock.newCondition();
        Condition produceCondition = lock.newCondition();


        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    while (num.get(0) > 0) {
                        produceCondition.await();
                    }
                    new Producer(num).produce();
                    consumeCondition.signal();
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();
        }

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    while (num.get(0) < 1) {
                        consumeCondition.await();
                    }
                    new Consumer(num).consume();
                    produceCondition.signal();
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();
        }
    }
}

class Ticket {

    private int sum;

    public Ticket(int sum){
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}

class Producer {

    private List<Integer> num;

    public Producer(List<Integer> num) {
        this.num = num;
    }

    public void produce() {
        num.set(0, num.get(0) + 1);
        System.out.println("生产：" + num.get(0));
    }
}

class Consumer {

    private List<Integer> num;

    public Consumer(List<Integer> num) {
        this.num = num;
    }

    public void consume() {
        num.set(0, num.get(0) - 1);
        System.out.println("消费：" + num.get(0));
    }

}

import java.util.ArrayList;
import java.util.List;

/**
 * 线程测试类
 * Created by song on 2017/3/6.
 */
public class ThreadTest {

    public static void main(String[] args){
        Storehose storehose = new Storehose(100);
        Produce produce = new Produce(storehose);
        Produce produce1 = new Produce(storehose);
        Consume consume = new Consume(storehose);
        Consume consume1 = new Consume(storehose);
        Consume consume2 = new Consume(storehose);
        Consume consume3 = new Consume(storehose);

        produce.start();
        produce1.start();
        consume.start();
        consume1.start();
        consume2.start();
        consume3.start();
    }
}
class Produce extends Thread {
    private Storehose storehose;
    Produce (Storehose storehose) {
        this.storehose = storehose;
    }
    @Override
    public void run() {
        while (true) {
            produce(5);
        }
    }
    private void produce(int num){
        List<Object> members = storehose.getMembers();
        int max_size = storehose.getMax_size();
        synchronized (storehose) {
            while (num + members.size() > max_size) {
                try {
                    storehose.wait();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 0; i < num; i++){
                try {
                    Thread.currentThread().sleep(10L);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                members.add(new Object());
                System.out.println("生产者----" + Thread.currentThread() + "----" + num + "----" + members.size());
            }
            storehose.notifyAll();
        }
    }
}
class Consume extends Thread {
    private Storehose storehose;
    public Consume(Storehose storehose){
        this.storehose = storehose;
    }
    @Override
    public void run() {
        while (true) {
            consume(3);
        }
    }
    public void consume(int num) {
        List<Object> members = storehose.getMembers();
        int max_size = storehose.getMax_size();
        synchronized (storehose) {
            while (members.size() - num < 0){
                try {
                    storehose.wait();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 0; i < num; i++) {
                try {
                    Thread.currentThread().sleep(10L);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread() + "----" + num + "----" + members.size());
                members.remove(members.size() - 1);
            }
            storehose.notifyAll();
        }
    }
}
class Storehose {
    private int max_size;
    private List<Object> members = new ArrayList();
    public Storehose(int max_size){
        this.max_size = max_size;
    }

    public int getMax_size() {
        return max_size;
    }

    public void setMax_size(int max_size) {
        this.max_size = max_size;
    }

    public List<Object> getMembers() {
        return members;
    }

    public void setMembers(List<Object> members) {
        this.members = members;
    }
}

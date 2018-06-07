/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/6/6 0006-下午 10:40<br>
 */
public class Test {

    static boolean a = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("进入run");
                    while(a){
                        //当这句被注释掉，出现死循环
                        System.out.println("run...");
                    }
                    System.out.println("结束了");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1500);
        a = false;
        System.out.println("a:"+a);
    }
}



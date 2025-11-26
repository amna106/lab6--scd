package lab6;
public class ThreadDeadlock {
	private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();
    private static final Object lock3 = new Object();
    public static void main(String[] args) throws InterruptedException{
    	System.out.println("Deadlocks..");
    	Thread t1 = new Thread (()-> deadlock("Thread 1."));
    	Thread t2 = new Thread (()-> deadlock("Thread 2."));
    	Thread t3 = new Thread (()-> deadlock("Thread 3."));
    	t1.start();
    	t2.start();
    	t3.start();
    	Thread.sleep(2000);
    	System.out.println("Resolve Deadlock through locking..");
    	Thread t4 = new Thread(() -> safeLocks("Thread 1."));
        Thread t5 = new Thread(() -> safeLocks("Thread 2."));
        Thread t6 = new Thread(() -> safeLocks("Thread 3."));
        t4.start();
        t5.start();
        t6.start();
}
        private static void deadlock(String threadName) {
        	try {
        		if(threadName.equals("Thread 1")) {
                    synchronized (lock1) {
                        System.out.println(threadName + ": Holding lock1.");
                        Thread.sleep(50);
                        synchronized (lock2) {
                            System.out.println(threadName + ": Holding lock1 & lock2.");
                        }
                    }
                } else if(threadName.equals("Thread 2")) {
                    synchronized (lock2) {
                        System.out.println(threadName + ": Holding lock2.");
                        Thread.sleep(50);
                        synchronized (lock3) {
                            System.out.println(threadName + ": Holding lock2 & lock3.");
                        }
                    } } else {
                        synchronized (lock3) {
                            System.out.println(threadName + ": Holding lock3.");
                            Thread.sleep(50);
                            synchronized (lock1) {
                                System.out.println(threadName + ": Holding lock3 & lock1.");
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        private static void safeLocks(String threadName) {
                synchronized (lock1) {
                    System.out.println(threadName + ": Secured lock1");
                    try { Thread.sleep(50); } catch (InterruptedException e) {}
                    synchronized (lock2) {
                        System.out.println(threadName + ": Secured lock2");
                        try { Thread.sleep(50); } catch (InterruptedException e) {}
                        synchronized (lock3) {
                            System.out.println(threadName + ": Secured lock3");
                            System.out.println(threadName + ": Doing work safely with all locks");
                        }
                    }
                }
            }
        }
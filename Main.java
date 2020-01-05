import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {
	
	final static CyclicBarrier gate = new CyclicBarrier(3);
	
	static void firstMethod() {
		System.out.print("HELLO ");
	}
	
	static void secondMethod() {
		System.out.print("WORLD ");
	}

	public static void main(String[] args) {
		
		int times = 6;
		while (times > 0) {
			Thread firstThread = new Thread() {
				@Override
				public void run() {
					try {
						gate.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
					
					firstMethod();
				}
			};
			
			Thread secondThread = new Thread() {
				@Override
				public void run() {
					
					try {
						gate.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
					
					secondMethod();
				}
			};
			
			firstThread.start();
			secondThread.start();
			
			try {
				gate.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			
			try {
				firstThread.join();
				secondThread.join();
				Thread.sleep(10 * 1_000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			gate.reset();
			System.out.println("");
			times--;
		}		
	}

}
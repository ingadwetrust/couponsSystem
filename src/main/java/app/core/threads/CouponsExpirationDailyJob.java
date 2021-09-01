package app.core.threads;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;
import app.core.services.JobService;

@Component
public class CouponsExpirationDailyJob implements Runnable {

	@Autowired
	JobService jobService;

	private boolean quit;
	private Thread job;

	/**
	 * This class is a thread that runs once a day after initialized. the thread
	 * goes through the array of the coupons and checks if they are expired. If they
	 * are, the thread deletes them from the database.
	 */

	@Override
	public void run() {
		while (!quit) {
			try {
				System.out.println();
				System.out.println("===============================================================");
				System.out.println("Running CouponsExpirationDailyJob....");
				System.out.println("Searching for expired coupons to erase...");
				jobService.deleteExpiredCoupons(LocalDate.now());
				System.out.println("===============================================================");
				System.out.println();
				Thread.sleep(86400000);

			} catch (CouponSystemException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				System.out.println("The CouponsExpirationDailyJob is shutting down");
				quit = true;

			}
		}
	}

	@PreDestroy
	public void stop() {
		System.out.println();
		System.out.println("===============================================================");
		System.out.println("The CouponsExpirationDailyJob is shutting down");
		System.out.println("===============================================================");
		System.out.println();
		job.interrupt();
		quit = true;
	}

	@PostConstruct
	public void start() {
		job = new Thread(this);
		job.start();
	}
}

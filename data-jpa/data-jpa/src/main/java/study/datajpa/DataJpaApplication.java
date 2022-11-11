package study.datajpa;

import org.apache.camel.component.quartz2.QuartzComponent;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
//		printDate();
		new LcmgrTestScheduler();
	}
//
//	@Scheduled(cron = "10 * * * * *")
//	static void printDate(){
//		try{
//			System.out.println(new Date().toString());
//			Thread.sleep(65000);
//		}catch (InterruptedException e){
//			e.printStackTrace();
//		}
//	}

	public static class LcmgrTestJob implements Job{

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			try{
				System.out.println(new Date().toString());
				System.out.println("==============job==============");
				Thread.sleep(65000);
			}catch (InterruptedException e){
				e.printStackTrace();
			}

		}
	}

	@Component
	static class LcmgrTestScheduler {
		private SchedulerFactory schedulerFactory;
		private Scheduler scheduler;

		@PostConstruct
		public void start() throws SchedulerException {

			schedulerFactory = new StdSchedulerFactory();
			scheduler = schedulerFactory.getScheduler();
			scheduler.start();

			//job 지정
			JobDetail job = JobBuilder.newJob(LcmgrTestJob.class).withIdentity("testJob").build();

			//trigger 생성
			Trigger trigger = TriggerBuilder.newTrigger().
					withSchedule(CronScheduleBuilder.cronSchedule("15 * * * * ?")).build();
//        startAt과 endAt을 사용해 job 스케쥴의 시작, 종료 시간도 지정할 수 있다.
//        Trigger trigger = TriggerBuilder.newTrigger().startAt(startDateTime).endAt(EndDateTime)
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * *")).build();

			scheduler.scheduleJob(job, trigger);

		}
	}
}

package study.datajpa;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class jobTest {

    public static void main(String[] args) {
        QuartzExample quartzExample = new QuartzExample();
        
        quartzExample.run();
    }

    public static class QuartzExample{
        private void run() {

            // First we must get a reference to a scheduler
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            try {
                Scheduler scheduler = schedulerFactory.getScheduler();

                // define the job and tie it to our HelloJob class
                JobDetail job = newJob(ExampleJob.class).withIdentity("MyJobName", "MyJobGroup").build();

                // Trigger the job to run after 5 seconds
                Date date = Date.from(LocalDateTime.now().plusSeconds(5).atZone(ZoneId.systemDefault()).toInstant());
                Trigger trigger = newTrigger().withIdentity("MyTriggerName", "MyTriggerGroup").startAt(date).build();

                // Tell quartz to schedule the job using our trigger
                scheduler.scheduleJob(job, trigger);
                System.out.println(job.getKey() + " will run at: "+ date);

                System.out.println(String.format("Trigger %s state: %s", trigger.getKey().getName(), scheduler.getTriggerState(trigger.getKey())));

                // Start up the scheduler (nothing can actually run until the scheduler has been started)
                scheduler.start();

                // wait long enough so that the scheduler has an opportunity to run the job!
                System.out.println("Waiting for 5 seconds");
                try {
                    Thread.sleep(5*1000);
                } catch (Exception e) {
                }

                // Shutdown the scheduler
                scheduler.shutdown(true);

            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

        public class ExampleJob implements Job {

            public void execute(JobExecutionContext jobExecutionContext) {
                System.out.println("Waiting for 3 seconds");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

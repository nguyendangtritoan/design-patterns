package victor.training.oo.behavioral.command;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import victor.training.oo.stuff.ThreadUtils;

@EnableAsync // SOLUTION
@SpringBootApplication
public class CommandSpringApp {
	public static void main(String[] args) {
		SpringApplication.run(CommandSpringApp.class, args).close(); // Note: .close to stop executors after CLRunner finishes
	}

	@Bean
	public ThreadPoolTaskExecutor executor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("barman-");
		executor.initialize();
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

}

@Slf4j
@Component
class Drinker implements CommandLineRunner {
	@Autowired
	private Barman barman;
	
	// TODO [1] inject and use a ThreadPoolTaskExecutor.submit
	// TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
	public void run(String... args) throws Exception {
		
		/// TODO InheritableThreadLocal try
		log.debug("Submitting my order to " + barman.getClass());
		CompletableFuture<Pivo> futurePivo = barman.getOnePivo();
		CompletableFuture<Vodka> futureVodka = barman.getOneVodka();
		
		log.debug("The afsenta took my command");
		Pivo pivo = futurePivo.get();
		Vodka vodka = futureVodka.get();
		log.debug("Got my order! Thank you lad! " + Arrays.asList(pivo, vodka));
	}
}

@Slf4j
@Service
class Barman {
	@Async
	public CompletableFuture<Pivo> getOnePivo() {
		 log.debug("Pouring Pivo...");
		 ThreadUtils.sleep(1000);
		 return CompletableFuture.completedFuture(new Pivo());
	 }
	
	@Async
	 public CompletableFuture<Vodka> getOneVodka() {
		 log.debug("Pouring Vodka...");
		 ThreadUtils.sleep(1000);
		 return CompletableFuture.completedFuture(new Vodka());
	 }
}

@Data
class Pivo {
}

@Data
class Vodka {
}

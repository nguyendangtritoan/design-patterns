package victor.training.oo.structural.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

@Slf4j
@EnableCaching
@SpringBootApplication
public class ProxySpringApp implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ProxySpringApp.class, args);
	}

	
	// TODO [1] implement decorator 
	// TODO [2] apply decorator via Spring
	// TODO [3] generic java.lang.reflect.Proxy 
	// TODO [4] Spring aspect 
	// TODO [5] Spring cache support
	// TODO [6] Back to singleton (are you still alive?)


//	@CuCache
//	@Qualifier("expensiveOpsCached")

	@Autowired
	ExpensiveOps ops;

	public void run(String... args) throws Exception {
		metodaDeLogicaZEN(ops);
	}

	private void metodaDeLogicaZEN(ExpensiveOps ops) {
		log.debug("Tu oare cine esti ?" + ops.getClass());
		log.debug("\n");
		log.debug("---- CPU Intensive ~ memoization?");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");

		log.debug("---- I/O Intensive ~ \"There are only two things hard in programming...\"");
		log.debug("Folder MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
		log.debug("Am detectat o schimbare a unui fisier: ");
		ops.aruncaCacheul(new File("."));
		log.debug("Folder MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
	}

}

@Slf4j
@Aspect
@Component
class LoggingInterceptor {

//	@Around("execution(* victor..*.*(..))")
//	@Around("execution(* *(..)) && @within(victor.training.oo.structural.proxy.LoggedClass)")
	@Around("execution(* *(..)) && @annotation(victor.training.oo.structural.proxy.LoggedMethod)")
	public Object interceptAndLog(ProceedingJoinPoint point) throws Throwable {
		log.debug("Ma cheama fraeru metoda: " +
				point.getSignature().getName() + " si param: " +
				Arrays.toString(point.getArgs()));
		return point.proceed();
	}
}

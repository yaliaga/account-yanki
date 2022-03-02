package pe.com.accountyanki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.com.accountyanki.model.AccountYanki;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accountyanki-2")
public class AccountYankiWebFluxReactive {
	@Autowired
	private ReactiveRedisTemplate reactiveRedisTemplate;

	@GetMapping(value = "/{id}")
	public Mono<AccountYanki> findAccountYankiById(@PathVariable("id") Long id) {
		String key = "accountYanki_" + id;
		ReactiveValueOperations<String, AccountYanki> operations = reactiveRedisTemplate.opsForValue();
		Mono<AccountYanki> accountYanki = operations.get(key);
		return accountYanki;
	}
	

	@GetMapping("/all")
	@ResponseBody
	public Flux<AccountYanki> findAllAccountYanki() {
       	 return (Flux<AccountYanki>) reactiveRedisTemplate.opsForValue();
       
             
         
	}
	@PostMapping
	public Mono<AccountYanki> saveAccountYanki(@RequestBody AccountYanki accountYanki) {
		String key = "accountYanki_" + accountYanki.getId();
		ReactiveValueOperations<String, AccountYanki> operations = reactiveRedisTemplate.opsForValue();
		return operations.getAndSet(key, accountYanki);
	}

	@DeleteMapping(value = "/{id}")
	public Mono<Long> deleteAccountYanki(@PathVariable("id") Long id) {
		String key = "accountYanki_" + id;
		return reactiveRedisTemplate.delete(key);
	}
}

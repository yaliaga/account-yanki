package pe.com.accountyanki.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.accountyanki.model.AccountYanki;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accountyanki")
public class AccountYankiController {

	   @Autowired
	    private RedisTemplate redisTemplate;

	    @GetMapping(value = "/{id}")
	    public Mono<AccountYanki> findAccountYankiById(@PathVariable("id") Long id) {
	        String key = "accountYanki_" + id;
	        ValueOperations<String, AccountYanki> operations = redisTemplate.opsForValue();
	        boolean hasKey = redisTemplate.hasKey(key);
	        AccountYanki accountYanki = operations.get(key);

	        if (!hasKey) {
	            return Mono.create(monoSink -> monoSink.success(null));
	        }
	        return Mono.create(monoSink -> monoSink.success(accountYanki));
	    }

	    @PostMapping()
	    public Mono<AccountYanki> saveAccountYanki(@RequestBody AccountYanki accountYanki) {
	        String key = "accountYanki_" + accountYanki.getId();
	        ValueOperations<String, AccountYanki> operations = redisTemplate.opsForValue();
	        operations.set(key, accountYanki, 60, TimeUnit.SECONDS);

	        return Mono.create(monoSink -> monoSink.success(accountYanki));
	    }

	    @DeleteMapping(value = "/{id}")
	    public Mono<Long> deleteAccountYanki(@PathVariable("id") Long id) {
	        String key = "accountYanki_" + id;
	        boolean hasKey = redisTemplate.hasKey(key);
	        if (hasKey) {
	            redisTemplate.delete(key);
	        }
	        return Mono.create(monoSink -> monoSink.success(id));
	    }
	
}

package com.hks.lettuce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
	  private static final String KEY = "userKey";
	  
	  @Autowired
	  private StringRedisTemplate stringRedisTemplate;
	  
	  public void addUserName(String uname) {
		  stringRedisTemplate.opsForValue().setIfAbsent(KEY, uname);
	  }
	  public void updateUserName(String uname) {
		  stringRedisTemplate.opsForValue().set(KEY, uname);
	  }	  
	  public String getUserName() {
		  return stringRedisTemplate.opsForValue().get(KEY);
	  }
	  public void deleteUser() {
		  stringRedisTemplate.delete(KEY);
	  }	  
}

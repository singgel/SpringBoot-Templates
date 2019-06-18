package com.hks.lettuce.dao;

import com.hks.lettuce.entity.Person;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Set;

@Repository
public class FamilyDAO {
	  private static final String KEY = "myFamilyKey";
	  
	  // inject the redisTemplate as SetOperations
	  @Resource(name="redisTemplate")
	  private SetOperations<String, Person> setOps;
	  
	  public void addFamilyMembers(Person... persons) {
		  setOps.add(KEY, persons);  	
	  }
	  public Set<Person> getFamilyMembers() {
		  return setOps.members(KEY);
	  }
	  public long getNumberOfFamilyMembers() {
		  return setOps.size(KEY);
	  }
	  public long removeFamilyMembers(Person... persons) {
		  return setOps.remove(KEY, (Object[])persons);
	  }	 
}

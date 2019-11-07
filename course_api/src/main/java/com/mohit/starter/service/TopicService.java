package com.mohit.starter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohit.starter.model.Topic;
import com.mohit.starter.repository.TopicRepository;

@Service
public class TopicService {
	
	@Autowired
	private TopicRepository topicRepository;

	/*
	 * List<Topic> topics=new ArrayList<>(Arrays.asList( new
	 * Topic(1,"Mohit","XSJTE"), new Topic(2,"Ambrish","XSJTE"), new
	 * Topic(3,"Mukesh","ABI") ));
	 */

	public List<Topic> getTopics()
	{
		List<Topic> topics= new ArrayList<Topic>();
		 topicRepository.findAll().forEach(topics::add);
		 return topics;
	}
	
	public Topic getTopic(int id)
	{
	//	return topics.stream().filter(t -> t.getId()==id).findFirst().get();
		//return topicRepository.findById(id);
		Optional<Topic> optinalEntity =  topicRepository.findById(id);
		 Topic topic = optinalEntity.get();
		 return topic;
	}

	public void addTopic(Topic topic) {
		
		topicRepository.save(topic);
	}

	public void updateTopic(int id, Topic topic) {

		/*
		 * for(int i=0;i<topics.size();i++) { Topic t= topics.get(i); if(t.getId()==id)
		 * { topics.set(id, topic); return; } }
		 */
		topicRepository.save(topic);
		
	}

	public void deleteTopic(int id) {
		//topics.removeIf(t -> t.getId()==id);		
		topicRepository.deleteById(id);
	}
	
}

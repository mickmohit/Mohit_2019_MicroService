package com.mohit.starter.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mohit.starter.model.Topic;
import com.mohit.starter.service.TopicService;

@RestController
public class TopicController {

	@Autowired
	private TopicService topicService;
	
	@RequestMapping("/topics")
	public List<Topic> alltopic()
	{
		return topicService.getTopics();
	}
	
	@RequestMapping("/topics/{id}")
	public Topic getTopic(@PathVariable("id") int id)
	{
		return topicService.getTopic(id);
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/topics")
	public void postTopic(@RequestBody Topic topic)
	{
		 topicService.addTopic(topic);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/topics/{id}")
	public void putTopic(@PathVariable("id") int id,@RequestBody Topic topic)
	{
		 topicService.updateTopic(id,topic);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}")
	public void deleteTopic(@PathVariable("id") int id)
	{
		 topicService.deleteTopic(id);
	}
}

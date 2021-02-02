package kafka.demo.controller;

import java.util.List;
import kafka.demo.model.User;
import kafka.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kafka.demo.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/kafka")
public class KafkaController {

	@Autowired
	private UserRepository repo;
	  
    @Autowired
    private KafkaProducer messageProducer;

	@GetMapping(value = "/publish")
    public User sendMessageToKafkaTopic(@RequestParam("id") String msg, @RequestParam("name") String msg2) {
        messageProducer.sendMessage("id:"+msg);
        log.info("message send : {}", msg);

        messageProducer.sendMessage("name:"+msg2);
        log.info("message send : {}", msg2);
        
        User updatedUser = null;
        updatedUser.setId(msg);
        updatedUser.setName(msg2);
        return repo.saveAndFlush(updatedUser);
    }
    

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findUsers() {
      return repo.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User addUser(@RequestBody User user) {
      user.setId(null);
      return repo.saveAndFlush(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User updatedUser, @PathVariable String id) {
      updatedUser.setId(id);
      return repo.saveAndFlush(updatedUser);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable Integer id) {
      repo.deleteById(id);
    }
}

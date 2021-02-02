package kafka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kafka.demo.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}

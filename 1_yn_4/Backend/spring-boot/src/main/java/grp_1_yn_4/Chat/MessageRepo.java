 package grp_1_yn_4.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import grp_1_yn_4.Chat.Message;
public interface MessageRepo extends JpaRepository<Message, Long>{
}
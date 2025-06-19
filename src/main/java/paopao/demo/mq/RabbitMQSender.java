//package paopao.demo.mq;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import paopao.demo.entity.LotteryRecord;
//
//@Component
//       public class RabbitMQSender {
//           @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//                   public void send(LotteryRecord record) {
//               rabbitTemplate.convertAndSend("lottery.result.exchange", "lottery.result.key", record);
//           }
//       }
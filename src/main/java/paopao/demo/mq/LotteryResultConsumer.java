//package paopao.demo.mq;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//import paopao.demo.entity.LotteryResult;
//
//@Component
//public class LotteryResultConsumer {
//    @RabbitListener(queues = "lottery.queue")
//    public void receive(LotteryResult result) {
//        System.out.println("用户 " + result.getUserId() + " 抽中了：" + result.getPrizeName());
//    }
//}
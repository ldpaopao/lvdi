//package paopao.demo.config;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.QueueBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitConfig {
//
//    @Bean
//    public Queue lotteryQueue() {
//        return QueueBuilder.durable("lottery.queue") // 名称与消费者一致
//                .autoDelete() // 若使用 auto-delete，请明确配置
//                .build();
//    }
//}

package paopao.demo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paopao.demo.entity.LotteryResult;
import paopao.demo.entity.Prize;
import paopao.demo.mapper.PrizeMapper;
import paopao.demo.mapper.UserPrizeMapper;
import paopao.demo.mq.RabbitMQSender;
import paopao.demo.service.LotteryService;

import java.util.List;
import java.util.Random;

@Service
public class LotteryServiceImpl implements LotteryService {
    @Autowired
    private PrizeMapper prizeMapper;
    @Autowired
    private UserPrizeMapper userPrizeMapper;
    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Override
    public Prize draw(int userId) {
        List<Prize> availablePrizes = prizeMapper.findAvailablePrizes();
        
        if (new Random().nextDouble() < 0.5) {
            return new Prize(0, "谢谢参与", 999999);
        }

        if (availablePrizes.isEmpty()) return null;

        Prize prize = availablePrizes.get(new Random().nextInt(availablePrizes.size()));
        if (prize.getQuantity() > 0) {
            prizeMapper.decrementPrize(prize.getId());
            userPrizeMapper.insert(userId, prize.getId());
            
            // 发送抽奖结果到RabbitMQ
            rabbitMQSender.send(new LotteryResult(userId, prize.getName()));
            return prize;
        }
        return null;
    }
}
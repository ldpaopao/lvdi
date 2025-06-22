package paopao.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import paopao.demo.dto.UserPrizeDTO;
import paopao.demo.entity.Prize;
import paopao.demo.entity.User;
import paopao.demo.mapper.UserPrizeMapper;
import paopao.demo.security.JwtUtils;
import paopao.demo.service.LotteryService;
import paopao.demo.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired private LotteryService lotteryService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("用户名已存在");
        }
        userService.register(user);
        return ResponseEntity.ok("注册成功");
    }

  // ... existing code ...
// ... existing code ...
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) {
      log.info("登录请求：username={}, password={}", user.getUsername(), user.getPassword());
      User dbUser = userService.findByUsername(user.getUsername());
      if (dbUser != null && dbUser.getPassword().equals(user.getPassword())) {

          Map<String,Object> userInfo = new HashMap<>();
          userInfo.put("id", dbUser.getId());
          userInfo.put("name", dbUser.getUsername());
          String token = JwtUtils.generateToken(userInfo);

          System.out.println(token);
          return ResponseEntity.ok(token);
      }
      return ResponseEntity.status(401).body("用户名或密码错误");
  }
// ... existing code ...
// ... existing code ...

    @PostMapping("/lottery")
    public ResponseEntity<?> lottery(@RequestHeader("Token") String token) {
        Integer id = JwtUtils.parseToken(token).get("id", Integer.class);
        if (id == null) return ResponseEntity.status(401).build();


        Prize prize = lotteryService.draw(id);
        if (prize != null) {
            return ResponseEntity.ok(prize.getName()); // 显示中奖奖品名称
        } else {
            return ResponseEntity.ok("谢谢参与"); // 未中奖显示“谢谢参与”
        }
    }


    @Autowired private UserPrizeMapper userPrizeMapper;

//    @GetMapping("/my-prizes")
//    public ResponseEntity<List<UserPrizeDTO>> getUserPrizes(@RequestHeader("Authorization") String token) {
//        Integer userId = tokenCache.get(token);
//        if (userId == null) return ResponseEntity.status(401).build();
//        return ResponseEntity.ok(userPrizeMapper.findByUserId(userId));
//    }
}
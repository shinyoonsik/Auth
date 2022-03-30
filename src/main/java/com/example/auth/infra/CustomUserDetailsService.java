package com.example.auth.infra;

import com.example.auth.entity.UserEntity;
import com.example.auth.entity.UserRepository;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// user의 data를 관리하기위해 UserDetailsService를 구현
@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder){
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;

    // 간단한 테스트
    final UserEntity testUserEntity = new UserEntity();
    testUserEntity.setUsername("ysshin");
    testUserEntity.setPassword(passwordEncoder.encode("ysshin"));
    this.userRepository.save(testUserEntity);
  }

  /**
   * @param username 사용자가 입력한 username
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final UserEntity userEntity = userRepository.findByUsername(username);
    return new User(username, userEntity.getPassword(), new ArrayList<>());
  }
}

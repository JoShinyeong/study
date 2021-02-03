package com.example.study.repository;

import com.example.study.StudyApplication;
import com.example.study.StudyApplicationTests;


import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.entity.User;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {

        String account="Test03";
        String password="Test03";
        String status ="REGISTERED";
        String email= "TEST01@gmail.com";
        String phoneNumber ="010-1111-3333";
        LocalDateTime registeredAt=LocalDateTime.now();
        LocalDateTime createdAt= LocalDateTime.now();
        String createdBy = "AdminSever";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);


        User newUser= userRepository.save(user);
        Assertions.assertNotNull(newUser);


    }

    @Test
    @Transactional
    public void read() {

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");
        if(user != null) {
            user.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println("------------------주문묶음--------------");
                System.out.println("수령인 : " + orderGroup.getRevName());
                System.out.println("수령지 : " + orderGroup.getRevAddress());
                System.out.println("총금액 : " + orderGroup.getTotalPrice());
                System.out.println("총수량 : " + orderGroup.getTotalQuantity());

                System.out.println("------------------주문상태--------------");
                orderGroup.getOrderDetailList().stream().forEach(orderDetail ->{
                    System.out.println("파트너사 이름 : "+ orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 카테고리 : " + orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문상품 :" + orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 :" + orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문상태 :" + orderDetail.getStatus());
                    System.out.println("도착예정일자 : " + orderDetail.getArrivalDate());
                        }
                );


            });

        }


        Assertions.assertNotNull(user);
    }

    @Test
    public void update() {

        Optional<User> user = userRepository.findById(2L);
        user.ifPresent(selectUser -> {
            selectUser.setAccount("PPPP");
            selectUser.setCreatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser);

        });


    }

    @Test
    @Transactional
    public void delete() {

        Optional<User> user = userRepository.findById(5L);

        Assertions.assertTrue(user.isPresent()); // true -> false

        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(5L);
//
//        if(deleteUser.isPresent()){
//            System.out.println("데이터 존재 : " + deleteUser.get());
//        }else{
//            System.out.println("데이터 삭제 데이터 없음");
//        }


        Assertions.assertFalse(deleteUser.isPresent());

    }

}
package com.dayang.miki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="order_id")
    private Long id;
    private Date orderDate; //주문시간
    private int pay; //주문 가격
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태

    @OneToMany(mappedBy = "order", cascade =CascadeType.ALL) //order를 persist하면 여기 있는 orderitem도 다 persist해준다.
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    //==생성 메서드==//

    //==비즈니스 로직==//
/*    public static Orders createOrder(double pay, OrderItem... orderItems){
        Orders order = new Orders();
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setPay(pay);
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }*/
    /**
     * 주문취소
     */
    public void cancel(){
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
    //==조회 로직==//

    /**
     *전체 주문 가격 조회
     */
    public int getTotalPrice(){
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}

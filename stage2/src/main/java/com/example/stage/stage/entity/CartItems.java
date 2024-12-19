package com.example.stage.stage.entity;

import com.example.stage.stage.dto.CartItemsDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data

public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;


// No usages

    private Long quantity;

// No usages

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicament_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Medicaments medicaments;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    public CartItemsDto getCartto(String lang){
        CartItemsDto cartItemsDto=new CartItemsDto();
        cartItemsDto.setId(id);
        cartItemsDto.setPrice(price);
        cartItemsDto.setProductId(medicaments.getId());
        cartItemsDto.setQuantity(quantity);
        cartItemsDto.setUserId(user.getId());
        if("ar".equals(lang) && medicaments.getName_ar()!=null ){
            cartItemsDto.setProductNane(medicaments.getName_ar());
        }

        else {
            cartItemsDto.setProductNane(medicaments.getName());
        }
        cartItemsDto.setReturnedImg(medicaments.getImg());




        return cartItemsDto;
    }

}

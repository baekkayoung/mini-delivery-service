package com.example.delivery.menu.entity;

import com.example.delivery.global.entity.BaseEntity;
import com.example.delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "menu")
@EntityListeners(AuditingEntityListener.class)
public class Menu extends BaseEntity {
    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name="menu_name",  nullable = false)
    private String name;

    @Column(name="menu_price", nullable = false)
    private Integer price;

    @Column(name = "menu_description")
    private String description;

    @Column(name="is_deleted", nullable = false)
    private Boolean isDeleted = false;


    public Menu(
            String name,
            Integer price,
            String description,
            User owner
    ) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.user = owner;
        this.isDeleted = false;
    }

    public void update(String name, int price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void delete(){
        this.isDeleted = true;
    }
}

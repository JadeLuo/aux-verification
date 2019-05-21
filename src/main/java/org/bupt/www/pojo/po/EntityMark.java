package org.bupt.www.pojo.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "entity_mark")
@Data
public class EntityMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "default 0")
    private Boolean reviewed = false;

    @Column(columnDefinition = "default 0")
    private Boolean passed = false;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "ver_date", columnDefinition = "DATE")
    private Date verDate;
}

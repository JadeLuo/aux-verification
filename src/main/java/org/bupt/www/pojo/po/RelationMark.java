package org.bupt.www.pojo.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "relation_mark")
@Data
public class RelationMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

//    @Column(name = "relation_name", nullable = false)
//    private String relationName;

    @Column(name = "relation_id", nullable = false)
    private Long relationId;

    @Column(columnDefinition = "default 0")
    private Boolean reviewed = false;

    @Column(columnDefinition = "default 0")
    private Boolean passed = false;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "ver_date", columnDefinition = "DATE")
    private Date verDate;

}

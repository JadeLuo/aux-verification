package org.bupt.www.pojo.po;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "rela_name_no")
@Data
public class RelationReflect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rela_name", nullable = false)
    private String relaName;

    @Column(name = "rela_no", nullable = false)
    private Long relaNo;

}

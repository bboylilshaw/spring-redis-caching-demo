package org.jasonxiao.demo.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Jason Xiao
 */
@Entity
@Table(name = "t_user")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

}

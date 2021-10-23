package com.springboot.learning.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: 坚持的力量
 * @Date: 2021/10/20 15:54
 * @Version: 11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StuRole implements Serializable {

    private Integer id;
    private List<Roles> roles;
}

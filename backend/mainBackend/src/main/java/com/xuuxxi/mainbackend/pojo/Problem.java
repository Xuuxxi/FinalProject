package com.xuuxxi.mainbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/22
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String level;

    private String description;

    private String templateCode;

    private String testCode;

}

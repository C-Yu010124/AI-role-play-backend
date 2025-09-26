package com.niuniu.airoleplaybackend.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 智能体模板类
 * <p>
 * 作者：
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_agent_template")
public class AgentTemplateDO {
    /**
     * 模板ID
     */
    private Long id;

    /**
     * 设计者ID
     */
    private Long userId;

    /**
     * 模板Key
     */
    private String templateKey;

    /**
     * 智能体名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 头像key
     */
    private String avatar;

    /**
     * 对话场景可选列表
     */
    private List<String> Scene;

    /**
     * 语音类型
     */
    private String voiceType;

    /**
     * 版本
     */
    @TableField("version_number")
    private Integer versionNumber;

    /**
     * 提示语
     */
    private String prompt;

    /**
     * 是否为当前最新版本 (1: 是, 0: 否)
     */
    @TableField("is_current")
    private String isCurrent;


    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 软删除标识 0：未删除 1：已删除
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;
}

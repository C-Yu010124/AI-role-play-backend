package com.niuniu.airoleplaybackend.dto.resp;

import lombok.Data;

import java.util.List;

/**
 * 查询声音列表
 * <p>
 * 作者：
 */
@Data
public class VoiceListResponseDTO {

    private List<VoiceInfo> list;

    static class VoiceInfo {
        /**
         * 音色名称
         */
        private String voiceName;

        /**
         * 音色类型
         */
        private String voiceType;

        /**
         * 试听音频链接
         */
        private String url;

        /**
         * 音色分类
         */
        private String category;

        /**
         * 更新时间（毫秒）
         */
        private Integer updatetime;
    }

}

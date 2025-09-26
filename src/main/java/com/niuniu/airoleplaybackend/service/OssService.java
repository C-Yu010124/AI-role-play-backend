package com.niuniu.airoleplaybackend.service;

import java.io.InputStream;

/**
 * OSS对象存储服务接口
 */
public interface OssService {
    
    /**
     * 上传文件到OSS
     * 
     * @param fileName 文件名
     * @param inputStream 文件输入流
     * @return 文件访问URL
     */
    String uploadFile(String fileName, InputStream inputStream);
}
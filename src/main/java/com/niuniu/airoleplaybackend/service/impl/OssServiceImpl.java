package com.niuniu.airoleplaybackend.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.niuniu.airoleplaybackend.config.OssConfiguration;
import com.niuniu.airoleplaybackend.service.OssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OssServiceImpl implements OssService {

    private final OssConfiguration ossConfiguration;
    private final OSS ossClient;

    @Override
    public String uploadFile(String fileName, InputStream inputStream) {
        try {
            // 生成OSS文件路径，按日期和UUID组织
            String filePath = generateFilePath(fileName);

            // 设置元数据
            ObjectMetadata metadata = new ObjectMetadata();

            // 根据文件扩展名设置Content-Type
            String contentType = getContentType(fileName);
            metadata.setContentType(contentType);

            // 创建上传请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    ossConfiguration.getBucketName(),
                    filePath,
                    inputStream,
                    metadata);

            // 执行上传
            ossClient.putObject(putObjectRequest);
            log.info("文件上传成功: bucketName={}, filePath={}", ossConfiguration.getBucketName(), filePath);

            // 生成带签名的URL（私有Bucket需要签名才能访问）
            Date expiration = new Date(System.currentTimeMillis() + ossConfiguration.getUrlExpiration() * 1000);
            URL signedUrl = ossClient.generatePresignedUrl(
                    ossConfiguration.getBucketName(),
                    filePath,
                    expiration);

            String fileUrl = signedUrl.toString();
            log.info("生成带签名的访问URL: {}", fileUrl);

            return fileUrl;
        } catch (Exception e) {
            log.error("上传文件到OSS时出错: {}", e.getMessage(), e);
            throw new RuntimeException("上传文件到OSS时出错", e);
        }
    }

    /**
     * 生成OSS文件路径
     *
     * @param fileName 原始文件名
     * @return OSS文件路径
     */
    private String generateFilePath(String fileName) {
        // 按日期组织文件
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        // 提取文件扩展名
        String extension = "";
        if (fileName.contains(".")) {
            extension = fileName.substring(fileName.lastIndexOf("."));
        }

        // 生成唯一文件名
        String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extension;

        // 返回完整路径
        return "audio/" + datePath + "/" + uniqueFileName;
    }

    /**
     * 根据文件名获取Content-Type
     *
     * @param fileName 文件名
     * @return Content-Type
     */
    private String getContentType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";

            // 音频类型
            case "mp3":
                return "audio/mpeg";
            case "wav":
                return "audio/wav";
            case "ogg":
                return "audio/ogg";
            case "aac":
                return "audio/aac";
            case "flac":
                return "audio/flac";
            case "m4a":
                return "audio/mp4";
            case "webm":
                return "audio/webm";


            case "mp4":
                return "video/mp4";
            case "pdf":
                return "application/pdf";
            case "doc":
            case "docx":
                return "application/msword";
            case "xls":
            case "xlsx":
                return "application/vnd.ms-excel";
            default:
                return "application/octet-stream";
        }
    }
}
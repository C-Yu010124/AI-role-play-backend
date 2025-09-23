package com.niuniu.airoleplaybackend.errorcode;

public enum BaseErrorCode implements IErrorCode{
    // ========== 一级宏观错误码 客户端错误 ==========
    CLIENT_ERROR("A000001", "用户端错误"),

    // ========== 二级宏观错误码 用户注册错误 ==========
    USER_REGISTER_ERROR("A000100", "用户注册错误"),
    USER_NAME_VERIFY_ERROR("A000110", "用户名校验失败"),
    USER_NAME_EXIST_ERROR("A000111", "用户名已存在"),
    USER_NAME_SENSITIVE_ERROR("A000112", "用户名包含敏感词"),
    USER_NAME_SPECIAL_CHARACTER_ERROR("A000113", "用户名包含特殊字符"),
    PASSWORD_VERIFY_ERROR("A000120", "密码校验失败"),
    PASSWORD_SHORT_ERROR("A000121", "密码长度不够"),
    PASSWORD_WEAK_ERROR("A000122", "密码强度不够"),
    PHONE_VERIFY_ERROR("A000151", "手机格式校验失败"),
    EMAIL_VERIFY_ERROR("A000152", "邮箱格式校验失败"),
    CAPTCHA_ERROR("A000160", "验证码错误"),
    CAPTCHA_EXPIRED_ERROR("A000161", "验证码已过期"),

    // ========== 二级宏观错误码 用户登录错误 ==========
    USER_LOGIN_ERROR("A000200", "用户登录错误"),
    USER_ACCOUNT_DISABLED_ERROR("A000201", "账号已被禁用"),
    USER_ACCOUNT_LOCKED_ERROR("A000202", "账号已被锁定"),
    USER_CREDENTIALS_ERROR("A000203", "用户名或密码错误"),
    USER_LOGIN_EXPIRED_ERROR("A000204", "登录已过期，请重新登录"),
    USER_NOT_LOGIN_ERROR("A000205", "用户未登录"),

    // ========== 二级宏观错误码 请求参数错误 ==========
    PARAM_ERROR("A000300", "请求参数错误"),
    PARAM_NULL_ERROR("A000301", "关键参数为空"),
    PARAM_FORMAT_ERROR("A000302", "参数格式错误"),
    PARAM_OUT_OF_RANGE_ERROR("A000303", "参数值超出范围"),

    // ========== 二级宏观错误码 资源访问错误 ==========
    RESOURCE_ERROR("A000400", "资源访问错误"),
    RESOURCE_NOT_FOUND_ERROR("A000401", "资源不存在"),
    RESOURCE_ACCESS_DENIED_ERROR("A000402", "资源访问权限不足"),
    RESOURCE_EXPIRED_ERROR("A000403", "资源已过期"),
    RESOURCE_REACH_LIMIT_ERROR("A000404", "资源已达访问上限"),

    // ========== 二级宏观错误码 系统请求缺少幂等Token ==========
    IDEMPOTENT_TOKEN_NULL_ERROR("A000500", "幂等Token为空"),
    IDEMPOTENT_TOKEN_DELETE_ERROR("A000501", "幂等Token已被使用或失效"),

    // ========== 一级宏观错误码 系统执行出错 ==========
    SERVICE_ERROR("B000001", "系统执行出错"),
    // ========== 二级宏观错误码 系统执行超时 ==========
    SERVICE_TIMEOUT_ERROR("B000100", "系统执行超时"),
    // ========== 二级宏观错误码 数据处理错误 ==========
    DATA_ERROR("B000200", "数据处理错误"),
    DATA_SAVE_ERROR("B000201", "数据保存失败"),
    DATA_UPDATE_ERROR("B000202", "数据更新失败"),
    DATA_DELETE_ERROR("B000203", "数据删除失败"),
    DATA_QUERY_ERROR("B000204", "数据查询失败"),
    DATA_DUPLICATE_ERROR("B000205", "数据重复"),
    // ========== 二级宏观错误码 系统资源错误 ==========
    RESOURCE_LIMIT_ERROR("B000300", "系统资源不足"),
    SYSTEM_BUSY_ERROR("B000301", "系统繁忙，请稍后再试"),
    FILE_UPLOAD_ERROR("B000302", "文件上传失败"),
    FILE_DOWNLOAD_ERROR("B000303", "文件下载失败"),
    FILE_SIZE_EXCEED_ERROR("B000304", "文件大小超出限制"),

    // ========== 一级宏观错误码 调用第三方服务出错 ==========
    REMOTE_ERROR("C000001", "调用第三方服务出错"),
    REMOTE_CONNECT_ERROR("C000101", "第三方服务连接失败"),
    REMOTE_TIMEOUT_ERROR("C000102", "第三方服务调用超时"),
    REMOTE_RESPONSE_ERROR("C000103", "第三方服务返回异常"),
    REMOTE_AUTH_ERROR("C000104", "第三方服务认证失败");


    private final String code;
    private final String message;

    BaseErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }


    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}


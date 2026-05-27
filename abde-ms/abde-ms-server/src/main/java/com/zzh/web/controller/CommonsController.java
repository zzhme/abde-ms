package com.zzh.web.controller;

import com.aliyun.utils.AliOSSUtils;
import com.zzh.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传和下载控制器
 */
@RestController
@RequestMapping("/commons")
public class CommonsController {
    @Autowired
    private AliOSSUtils aliOSSUtils;
    /**
     * 文件上传
     *
     * @param uploadFile 必须和前端文件上传表单项的name属性值保持一致
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile uploadFile) throws IOException {
        //调用AliOSSUtils工具类实现文件上传,获取文件在阿里云OSS服务器上的路径
        String url = aliOSSUtils.upload(uploadFile);
        //响应结果:url作为Result对象中的数据传递的
        return Result.success((Object)url);
    }
}

package com.zzh.web.controller;

import com.zzh.pojo.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 文件上传和下载控制器
 */
//@RestController
@RequestMapping("/commons")
public class CommonsControllerDrop {
    /**
     * 文件上传
     *
     * @param uploadFile 必须和前端文件上传表单项的name属性值保持一致
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile uploadFile) throws IOException {
        //定义保存上传文件的目录
        File baseDir = new File("C:\\Users\\smhjx\\Desktop\\upload");
        //如果目录不存在,则创建目录
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        //保证同名文件不被覆盖,生成一个新的文件名(uuid字符串+"-"+原始文件名)
        String fileName = UUID.randomUUID().toString().replace("-", "") + "-" + uploadFile.getOriginalFilename();
        //创建文件字节输出流
        FileOutputStream os = new FileOutputStream(new File(baseDir, fileName));
        //获取字节输入流
        InputStream is = uploadFile.getInputStream();
        //字节数组循环读写
        byte[] bs = new byte[1024];
        int len;
        while ((len = is.read(bs)) != -1) {
            os.write(bs,0,len);
        }
        //关闭流对象
        is.close();
        os.close();
        //以下是简化方式
        //uploadFile.transferTo(new File(baseDir, fileName));
        //响应结果:文件名作为Result对象中的数据传递的
        return Result.success((Object)fileName);
    }


    /**
     * 文件下载
     * @param fileName 要下载的文件名
     * @param resp     HttpServletResponse对象,给浏览器响应的
     * @return
     * @throws IOException
     */
    @GetMapping("/download")
    public Result download(String fileName, HttpServletResponse resp) throws IOException {
        //定义保存下载文件的目录
        File baseDir = new File("C:\\Users\\smhjx\\Desktop\\upload");
        if (!baseDir.exists()) {
            return Result.error("服务器下载文件目录不存在,下载文件失败!!!");
        }
        //创建要下载的文件对应的File对象
        File downloadFile = new File(baseDir, fileName);
        //判断下载文件不存在或者不是文件,直接响应错误结果对象
        if (!downloadFile.exists() || !downloadFile.isFile()) {
            return Result.error("下载文件不存在,下载失败!!!");
        }
        //创建字节输入流
        FileInputStream is = new FileInputStream(downloadFile);
        //获取字节输出流
        ServletOutputStream os = resp.getOutputStream();
        //字节数组循环读写
        byte[] bs = new byte[1024];
        int len;
        while ((len = is.read(bs)) != -1) {
            os.write(bs,0,len);
        }
        //关闭资源
        is.close();
        os.close();
        return Result.success("文件下载成功!!!");
    }
}

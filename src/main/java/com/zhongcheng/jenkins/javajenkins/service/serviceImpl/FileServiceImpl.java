package com.zhongcheng.jenkins.javajenkins.service.serviceImpl;


import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import com.zhongcheng.jenkins.javajenkins.model.ErrorEnum;
import com.zhongcheng.jenkins.javajenkins.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {
    public HashMap<String ,String> upload(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String realPath;
        if (filename == null) {
            filename = "";
        }
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            String pathStr = path.getAbsolutePath();
            realPath = pathStr.replace("\\target\\classes", "")+"\\src\\main\\resources\\static\\zip";
            file.transferTo(new File(realPath, filename));
        } catch (IOException e) {
            throw new BaseException(ErrorEnum.IO_ERROR);
        }
        HashMap<String ,String> map = new HashMap<>();
        map.put("url", realPath+filename);
        return map;
    }
}

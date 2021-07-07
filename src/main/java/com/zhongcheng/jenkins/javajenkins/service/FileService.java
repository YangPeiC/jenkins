package com.zhongcheng.jenkins.javajenkins.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;

public interface FileService {
    HashMap<String ,String> upload(MultipartFile file);
}

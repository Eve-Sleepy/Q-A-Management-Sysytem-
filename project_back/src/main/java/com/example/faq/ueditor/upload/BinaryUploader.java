package com.example.faq.ueditor.upload;

import com.example.faq.ueditor.PathFormat;
import com.example.faq.ueditor.define.AppInfo;
import com.example.faq.ueditor.define.BaseState;
import com.example.faq.ueditor.define.FileType;
import com.example.faq.ueditor.define.State;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class BinaryUploader {

    public static final State save(HttpServletRequest request, Map<String, Object> conf) {
        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }

        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());
            if (multipartFile == null) {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }
            String savePath = (String) conf.get("savePath");
            String originFileName = multipartFile.getOriginalFilename();
            String suffix = FileType.getSuffixByFilename(originFileName);

            originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
            savePath = savePath + suffix;

            long maxSize = ((Long) conf.get("maxSize")).longValue();

            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {

                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);

            }

            // 图片或者文件存储的真正实现
            savePath = PathFormat.parse(savePath, originFileName);
            String physicalPath = (String) conf.get("saveRootPath") + savePath;
            InputStream is = multipartFile.getInputStream();
            State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
            is.close();

            //
            if (storageState.isSuccess()) {
                storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
            }


            return storageState;

        } catch (IOException e) {

        }

        return new BaseState(false, AppInfo.IO_ERROR);

    }


    private static boolean validType(String type, String[] allowTypes) {

        List<String> list = Arrays.asList(allowTypes);


        return list.contains(type);

    }

}

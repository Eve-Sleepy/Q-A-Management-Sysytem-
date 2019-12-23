package com.example.faq.controllers;

import com.example.faq.ueditor.ActionEnter;
import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 *  rootPath: C:\Users\isubokuro\AppData\Local\Temp\tomcat-docbase.13062095969592516910.5000\
 *  saveRootPath: E:\Git_workplace\project_web\project_web_back
 *      System.getProperty("user.dir") 获取当前项目的路径
 * 		System.out.println(System.getProperty("user.dir"));
 */
@CrossOrigin
@RestController
public class UeditorController {
	@Value(value="classpath:config.json")
	private Resource resource;
	private static String UPLOAD_IMAGE_PATH = "File/image/upload";

	@RequestMapping("/ueditor/ueditorConfig")
	public @ResponseBody Object getConfig(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {

		response.setContentType("application/json;charset=UTF-8");

		String rootPath = request.getSession().getServletContext().getRealPath("/");
		request.setCharacterEncoding("UTF-8");
		String callback = request.getParameter("callback");
		String actionType = request.getParameter("action");
		response.setContentType("application/json;charset=UTF-8");
		String s = new String();

		// ResourcePath: 是ueditor的config.json文件的地址
		String ResourcePath = ResourceUtils.getURL("classpath:").getPath()+"config.json";

		String saveRootPath = System.getProperty("user.dir");

		if(actionType.equals("config")){
			s = callback + "(" + new JSONObject(new ActionEnter(request, ResourcePath,saveRootPath).exec()) + ")";
		}else if(actionType.equals("uploadimage")){
			s = new ActionEnter(request, rootPath,saveRootPath).exec();
		}else if(actionType.equals("uploadfile") || actionType.equals("uploadvideo")){
			s = new ActionEnter(request, rootPath,saveRootPath).exec();
		}

		 return s;
	}


}


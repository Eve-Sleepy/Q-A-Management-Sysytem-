package com.example.faq.controllers;

import com.example.faq.models.ApiResult;
import com.example.faq.models.Product;
import com.example.faq.models.Tag;
import com.example.faq.persistences.TagDao;
import com.example.faq.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/tag")
@Controller
public class TagCtrller {

    @Autowired
    private TagService tagService;

    @GetMapping("list")
    @ResponseBody
    public ApiResult tagList() {
        ApiResult apiResult = new ApiResult();
        //tagDao.getTagList返回的是标签类型列表组
        List<Tag> tags = tagService.findTags();
        if (tags == null || tags.size() == 0) {
            apiResult.setStatus(500);
            apiResult.setMsg("列表返回失败或者不存在标签");
            return apiResult;
        }
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("total", tags.size());
        ArrayList<Map<String, Object>> arr = new ArrayList<>();
        //将每个标签对象以特定格式保存到tag对象中
        for (Tag tag : tags) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", tag.getId());
            item.put("name", tag.getName());
            arr.add(item);
        }

        data.put("arr", arr);

        apiResult.setStatus(200);
        apiResult.setData(data);
        apiResult.setMsg("标签列表返回成功");
        return apiResult;
    }

}

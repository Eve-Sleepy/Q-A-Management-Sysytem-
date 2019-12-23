package com.example.faq.controllers;

import com.example.faq.models.ApiResult;
import com.example.faq.models.Reply;
import com.example.faq.models.User;
import com.example.faq.services.ReplyService;
import com.example.faq.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/api/reply")
@Controller
public class ReplyCtrller {
    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;

    @GetMapping("list")
    @ResponseBody
    public ApiResult findReplyListByDocId(Integer docId) {
        ApiResult apiResult = new ApiResult();

        List<Reply> replies = replyService.findReplyListByDocId(docId);
        if (replies == null || replies.size() == 0) {
            apiResult.setStatus(501);
            apiResult.setMsg("没有回复/数据库查询失败");
            return apiResult;
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("total", replies.size());
        ArrayList<Map<String, Object>> arr = new ArrayList<>();
        for (Reply reply : replies) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("replyId", reply.getId());
            // 1、查询回复者的姓名
            User user = userService.findUserById(reply.getReplierId());
            item.put("replierId", user.getId());
            item.put("replierName", user.getRealname());
            item.put("replyContent", reply.getReplyContent());
            item.put("replyTime", reply.getReplyTime());

            arr.add(item);
        }

        data.put("arr", arr);
        apiResult.setStatus(200);
        apiResult.setMsg("回复列表返回成功");
        apiResult.setData(data);
        return apiResult;
    }

    @RequestMapping("create")
    @ResponseBody
    public ApiResult addReply(@RequestBody Reply reply) {
        ApiResult apiResult = new ApiResult();

        // 1、记录操作时间
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tablename = dateFormat.format(now);

        reply.setReplyTime(tablename);

        if (replyService.addReply(reply) == 0) {
            apiResult.setStatus(501);
            apiResult.setMsg("回复失败/数据库插入失败");
            return apiResult;
        }

        apiResult.setStatus(200);
        apiResult.setMsg("回复成功");
        return apiResult;
    }

    @GetMapping("delete")
    @ResponseBody
    public ApiResult addReply(Integer replyId) {
        ApiResult apiResult = new ApiResult();


        if (replyService.removeReplyByReplyId(replyId) == 0) {
            apiResult.setStatus(501);
            apiResult.setMsg("回复删除失败/数据库插入失败");
            return apiResult;
        }

        apiResult.setStatus(200);
        apiResult.setMsg("回复删除成功");
        return apiResult;
    }
}

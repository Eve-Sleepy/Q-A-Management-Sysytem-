package com.example.faq.controllers;

import com.example.faq.models.*;
import com.example.faq.services.DocService;
import com.example.faq.services.MsgService;
import com.example.faq.services.ProductService;
import com.example.faq.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/msg")
@Controller
public class MsgCtrller {
    @Autowired
    private MsgService msgService;
    @Autowired
    private DocService docService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/list")
    @ResponseBody
    public ApiResult findMsgListByUserId(@RequestBody Map<String,Object> params){
        ApiResult apiResult = new ApiResult();
        // 有分页
        Map<String, Object> query = new LinkedHashMap<>();
        query.put("userId",(Integer)params.get("userId"));
        query.put("currentPage",null);
        query.put("perPage",null);
        List<Msg> msgsWithoutPage = msgService.findMsgListByReceiverId(query);
        
        List<Msg> msgs = msgService.findMsgListByReceiverId(params);
        if(msgs == null || msgs.size() == 0 || msgsWithoutPage == null || msgsWithoutPage.size() == 0){
            apiResult.setStatus(501);
            apiResult.setMsg("没有私信");
            return apiResult;
        }
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("total", msgsWithoutPage.size());

        ArrayList<Map<String,Object>> arr = new ArrayList<>();
        for(Msg msg:msgs){
            Map<String,Object> item = new LinkedHashMap<>();

            item.put("msgId",msg.getId());
            // 根据文档id查文档相关信息
            item.put("docId",msg.getDocId());
            Doc doc = docService.findDocById(msg.getDocId());
            String title = doc.getTitle();
            item.put("title",title);
            item.put("edition",doc.getEdition());
            // 根据 产品id查产品相关信息
            Product productFind = productService.findProductById(doc.getProductId());
            Map<String, Object> product = new LinkedHashMap<>();
            product.put("color",productFind.getColor());
            product.put("name", productFind.getName());
            item.put("product",product);
            item.put("deptBelong", doc.getDeptBelong());

            // 根据senderId查用户相关信息
           User user = userService.findUserById(msg.getSenderId());
           String senderName = user.getRealname();
           item.put("senderName",senderName);
           item.put("state",msg.getState());
           item.put("sendTime",msg.getSendTime());

           arr.add(item);
        }
        data.put("arr",arr);

        apiResult.setStatus(200);
        apiResult.setMsg("私信列表返回成功");
        apiResult.setData(data);
        return apiResult;
    }

    @GetMapping("/delete")
    @ResponseBody
    public ApiResult deleteMsgByMsgId(Integer msgId){
        ApiResult apiResult = new ApiResult();
        if(msgService.removeMsgByMsgId(msgId) == 0){
            apiResult.setStatus(501);
            apiResult.setMsg("私信删除失败/数据库更新失败");
            return apiResult;
        }

        apiResult.setStatus(200);
        apiResult.setMsg("私信删除成功");
        return apiResult;
    }

    @GetMapping("/check")
    @ResponseBody
    public ApiResult checkMsgByMsgId(Integer msgId){
        ApiResult apiResult = new ApiResult();
        if(msgService.checkMsgByMsgId(msgId) == 0){
            apiResult.setStatus(501);
            apiResult.setMsg("私信标为已读失败/数据库更新失败");
            return apiResult;
        }

        apiResult.setStatus(200);
        apiResult.setMsg("私信标为已读成功");
        return apiResult;
    }
}

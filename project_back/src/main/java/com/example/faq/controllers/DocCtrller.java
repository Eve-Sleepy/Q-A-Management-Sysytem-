package com.example.faq.controllers;


import com.example.faq.dto.DocCreateDto;
import com.example.faq.dto.DocEditDto;
import com.example.faq.dto.DocSearchDto;
import com.example.faq.models.*;
import com.example.faq.persistences.*;
import com.example.faq.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/doc")
public class DocCtrller {

    @Autowired
    private DocService docService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TagForDocService tagForDocService;
    @Autowired
    private MsgService msgService;
    @Autowired
    private ReplyService replyService;


    @RequestMapping("list")
    @ResponseBody
    public ApiResult showDocList(@RequestBody DocSearchDto docSearchDto) {

        ApiResult apiResult = new ApiResult();
        ArrayList<Map<String, Object>> datas = new ArrayList<>();

        // 1、处理传入标签数组是未空数组和null的情况
        if (docSearchDto.getTagGroup() != null && docSearchDto.getTagGroup().length == 0) {
            docSearchDto.setTagGroup(null);
        }

        // 2、返回符合筛选条件的文档列表
        Object[] docSelectArr = docService.findDocList(docSearchDto);
        Integer totalDoc = (Integer) docSelectArr[0];
        List<Doc> docSelectOnePage = (List<Doc>) docSelectArr[1];

        if (docSelectOnePage.size() == 0 || docSelectOnePage == null) {
            apiResult.setStatus(201);
            apiResult.setMsg("没有符合筛选条件的文档列表");
            return apiResult;
        }
        // 3、包装返回的每条文档记录
        for (Doc doc : docSelectOnePage) {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("docId", doc.getId());
            data.put("title", doc.getTitle());

            // 4、查询对应作者信息
            User userFind = userService.findUserById(doc.getAuthorId());
            Map<String, Object> user = new LinkedHashMap<>();
            user.put("id", userFind.getId());
            user.put("realname", userFind.getRealname());
            data.put("author", user);

            data.put("deptBelong", doc.getDeptBelong());
            data.put("operation", doc.getOperation());
            data.put("operationTime", doc.getOperationTime());

            // 5、查询对应的产品信息
            Product productFind = productService.findProductById(doc.getProductId());
            Map<String, Object> product = new LinkedHashMap<>();
            product.put("name", productFind.getName());
            product.put("edition", doc.getEdition());
            product.put("color", productFind.getColor());
            data.put("product", product);

            // 6、查询对应文档标签组的信息
            ArrayList<String> tagGroup = tagForDocService.findTagNameGroupByDocId(doc.getId());
            data.put("tagGroup", tagGroup);

            // 7、对搜索标签的匹配度高的放靠前？
            datas.add(data);
        }
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("total", totalDoc);
        data.put("arr", datas);
        apiResult.setStatus(200);
        apiResult.setMsg("返回文档列表成功");
        apiResult.setData(data);
        return apiResult;

    }

    @RequestMapping("create")
    @ResponseBody
    public ApiResult createDoc(@RequestBody DocCreateDto docCreateDto) {

        ApiResult apiResult = new ApiResult();

        // 1、记录文档操作时间
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tablename = dateFormat.format(now);

        docCreateDto.setOperationTime(tablename);
        docCreateDto.setOperation(0);
        // 2、有关文档信息插入doc表
        if (docService.addDocUserInfo(docCreateDto) == 0) {
            apiResult.setStatus(501);
            apiResult.setMsg("doc表插入失败");
            return apiResult;
        }
        // 1、获取成功插入文档的id
        Integer docId = docCreateDto.getId();

        // 3、有关标签信息插入tagsForDocs表
        // 1、得到请求中所有标签名的id。根据标签名在tag表中找对应id，如果是新标签，拿到插入数据库中后返回的id
        ArrayList<Integer> tagIds = new ArrayList<>();
        for (String tagName : docCreateDto.getTagGroup()) {
            Integer tagId = tagService.findTagIdByName(tagName);
            if (tagId != null) {
                tagIds.add(tagId);
            } else {
                Tag tag = new Tag();
                tag.setName(tagName);
                tag.setCreatedTime(tablename);
                Integer newTagId = tagService.addTag(tag);
                if (newTagId == null) {
                    apiResult.setStatus(501);
                    apiResult.setMsg("tag表插入失败");
                    return apiResult;
                }
                if (newTagId == 0) {
                    return apiResult;
                }
                tagIds.add(newTagId);
            }
        }
        // 2、将所有的id与该文档id对应关系添加到tagForDoc表。
        for (Integer tagId : tagIds) {
            TagForDoc tagForDoc = new TagForDoc();
            tagForDoc.setDocId(docId);
            tagForDoc.setTagId(tagId);
            tagForDoc.setCreatedTime(tablename);
            if (tagForDocService.addTagForDoc(tagForDoc) == 0) {
                apiResult.setStatus(501);
                apiResult.setMsg("tagForDoc表插入失败");
                return apiResult;
            }
        }
        // 4、有关私信人员组插入msgs表 [int]
        for (Integer receiverId : docCreateDto.getMsgGroup()) {
            Msg msg = new Msg();
            msg.setDocId(docId);
            msg.setSenderId(docCreateDto.getAuthorId());
            msg.setReceiverId(receiverId);
            msg.setSendTime(tablename);
            msg.setState(0);
            if (msgService.addMsg(msg) == 0) {
                apiResult.setStatus(501);
                apiResult.setMsg("msg表插入失败");
                return apiResult;
            }
        }

        apiResult.setStatus(200);
        apiResult.setMsg("新建文档成功");
        //新建map键值对对象存文档id属性
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("docId", docId);
        apiResult.setData(map);
        return apiResult;
    }

    @RequestMapping("update")
    @ResponseBody
    public ApiResult updateDoc(@RequestBody DocEditDto docEditDto) {
        ApiResult apiResult = new ApiResult();

        // 1、记录文档修改操作时间
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tablename = dateFormat.format(now);

        docEditDto.setOperation(1);
        docEditDto.setOperationTime(tablename);

        // 2、有关文档信息更新doc表
        if (docService.editDocUserInfo(docEditDto) == 0) {
            apiResult.setStatus(501);
            apiResult.setMsg("doc表插入失败");
            return apiResult;
        }
        // 3、有关标签信息更新tagForDoc表
        // 1、删除文档id对应的tagfordoc表记录
        tagForDocService.deleteTagsForDoc(docEditDto.getDocId());
        // 2、插入
        // 1、得到请求中所有标签名的id。根据标签名在tag表中找对应id，如果是新标签，拿到插入数据库中后返回的id
        ArrayList<Integer> tagIds = new ArrayList<>();
        for (String tagName : docEditDto.getTagGroup()) {
            Integer tagId = tagService.findTagIdByName(tagName);
            if (tagId != null) {
                tagIds.add(tagId);
            } else {
                Tag tag = new Tag();
                tag.setName(tagName);
                tag.setCreatedTime(tablename);
                Integer newTagId = tagService.addTag(tag);
                if (newTagId == null) {
                    apiResult.setStatus(501);
                    apiResult.setMsg("tag表插入失败");
                    return apiResult;
                }
                if (newTagId == 0) {
                    return apiResult;
                }
                tagIds.add(newTagId);
            }
        }
        // 2、将所有的id与该文档id对应关系添加到tagForDoc表。
        for (Integer tagId : tagIds) {
            TagForDoc tagForDoc = new TagForDoc();
            tagForDoc.setDocId(docEditDto.getDocId());
            tagForDoc.setTagId(tagId);
            tagForDoc.setCreatedTime(tablename);
            if (tagForDocService.addTagForDoc(tagForDoc) == 0) {
                apiResult.setStatus(501);
                apiResult.setMsg("tagForDoc表插入失败");
                return apiResult;
            }
        }

        apiResult.setStatus(200);
        apiResult.setMsg("更新文档成功");
        return apiResult;
    }

    @GetMapping("select")
    @ResponseBody
    public ApiResult selectDocById(Integer docId) {

        Map<String, Object> data = new LinkedHashMap<>();
        ApiResult apiResult = new ApiResult();

        Doc doc = docService.findDocById(docId);
        if (doc == null) {
            apiResult.setStatus(500);
            apiResult.setMsg("该文档不存在或已被删除");
            return apiResult;
        }

        // 1、记录文档修改操作时间
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tablename = dateFormat.format(now);

        // 2、获取doc表中的信息
        data.put("title", doc.getTitle());
        data.put("content", doc.getContent());
        data.put("edition", doc.getEdition());
        data.put("deptBelong", doc.getDeptBelong());
        // 3、获取作者信息
        User author = userService.findUserById(doc.getAuthorId());
        data.put("authorId", author.getId());
        data.put("authorName", author.getRealname());

        data.put("operation", doc.getOperation());
        data.put("operationTime", tablename);

        // 4、获取产品信息
        Product productFind = productService.findProductById(doc.getProductId());
        Map<String, Object> product = new LinkedHashMap<>();
        product.put("id", productFind.getId());
        product.put("name", productFind.getName());
        product.put("color", productFind.getColor());
        data.put("product", product);

        // 5、获取标签
        ArrayList<String> tagGroup = tagForDocService.findTagNameGroupByDocId(doc.getId());
        data.put("tagGroup", tagGroup);


        apiResult.setStatus(200);
        apiResult.setMsg("返回文档内容成功");
        apiResult.setData(data);
        return apiResult;
    }

    @GetMapping("delete")
    @ResponseBody
    public ApiResult deleteDoc(Integer docId) {
        ApiResult apiResult = new ApiResult();
        // 删除msgs表中的doc_id的记录 和 回复表中的记录 和tagfordoc表中记录
        if (docService.removeDocById(docId) == 0) {
            apiResult.setStatus(501);
            apiResult.setMsg("删除时数据库遇到问题或删除不存在的文档");
            return apiResult;
        }

        apiResult.setStatus(200);
        apiResult.setMsg("删除成功");
        return apiResult;
    }
}

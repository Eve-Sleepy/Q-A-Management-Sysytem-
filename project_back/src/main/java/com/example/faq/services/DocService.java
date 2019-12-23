package com.example.faq.services;

import com.example.faq.dto.DocCreateDto;
import com.example.faq.dto.DocEditDto;
import com.example.faq.dto.DocSearchDto;
import com.example.faq.models.Doc;
import com.example.faq.models.Product;
import com.example.faq.persistences.DocDao;
import com.example.faq.models.User;
import org.apache.tomcat.util.descriptor.tld.TagFileXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocService {
    @Autowired
    private DocDao docDao;
    @Autowired
    private MsgService msgService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private TagForDocService tagForDocService;


    public Object[] findDocList(DocSearchDto docSearchDto) {
        Object[] docList = new Object[2];
        // 1、获取分页查询
        Integer perPage = docSearchDto.getPerPage();
        Integer currentPage = docSearchDto.getCurrentPage();

        // 2、count未分页时的文档数量totalDoc
        docSearchDto.setPerPage(null);
        docSearchDto.setCurrentPage(null);
        List<Doc> docSelect = docDao.selectDocList(docSearchDto);
        Integer totalDoc = docSelect.size();

        // 3、筛选进行分页后的文档
        docSearchDto.setPerPage(perPage);
        docSearchDto.setCurrentPage(currentPage);
        List<Doc> docSelectOnePage = docDao.selectDocList(docSearchDto);

        docList[0] = totalDoc;
        docList[1] = docSelectOnePage;

        return docList;
    }

    public Integer addDocUserInfo(DocCreateDto docCreateDto) {
        Integer state = docDao.insertDoc(docCreateDto);
        return state;
    }

    public Integer editDocUserInfo(DocEditDto docEditDto) {
        Integer state = docDao.updateDoc(docEditDto);
        return state;
    }

    public Doc findDocById(Integer docId) {
        Doc doc = docDao.findDocById(docId);
        return doc;
    }

    public Integer removeDocById(Integer docId) {
        msgService.removeMsgByDocId(docId);
        replyService.removeReplyByDocId(docId);
        tagForDocService.deleteTagsForDoc(docId);
        Integer state = docDao.deleteDoc(docId);
        return state;
    }

    public Integer getDeptBelongNumByQuery(Map<String, Object> deptQuery) {

        Integer num = docDao.getDeptBelongNum(deptQuery);
        if (num != null) {
            return num;
        } else {
            return 0;
        }

    }

    public Integer deleteDocByProductId(Integer id) {
        Doc doc = docDao.findDocByProductId(id);
        msgService.removeMsgByDocId(doc.getId());
        replyService.removeReplyByDocId(doc.getId());
        tagForDocService.deleteTagsForDoc(doc.getId());
        return docDao.deleteDocByProductId(id);
    }
}

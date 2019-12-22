package com.example.faq.services;

import com.example.faq.models.Reply;
import com.example.faq.persistences.ReplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;

    public List<Reply> findReplyListByDocId(Integer docId){
            return replyDao.selectReplyListByDocId(docId);
    }

    public Integer addReply(Reply reply){
        return replyDao.insertReply(reply);
    }
    public Integer removeReplyByDocId(Integer docId){
        return replyDao.deleteReplyByDocId(docId);
    }

    public Integer removeReplyByReplyId(Integer replyId){
        return replyDao.deleteReplyByReplyId(replyId);
    }
}

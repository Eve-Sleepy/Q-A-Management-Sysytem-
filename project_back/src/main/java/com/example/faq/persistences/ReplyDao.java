package com.example.faq.persistences;

import com.example.faq.models.Reply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyDao {
    List<Reply> selectReplyListByDocId(Integer docId);

    Integer insertReply(Reply reply);

    Integer deleteReplyByDocId(Integer docId);

    Integer deleteReplyByReplyId(Integer replyId);
}

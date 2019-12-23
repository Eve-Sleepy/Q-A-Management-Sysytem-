package com.example.faq.services;

import com.example.faq.models.Msg;
import com.example.faq.persistences.MsgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MsgService {
    @Autowired
    private MsgDao msgdao;

    public Integer addMsg(Msg msg) {
        return msgdao.insertMsg(msg);
    }

    public List<Msg> findMsgListByReceiverId(Map<String, Object> query) {

        return msgdao.selectMsgListByReceiverId((Integer) query.get("userId"),
                (Integer) query.get("currentPage"),
                (Integer) query.get("perPage"));
    }

    public Integer removeMsgByDocId(Integer docId) {
        return msgdao.deleteMsgByDocId(docId);
    }

    public Integer removeMsgByMsgId(Integer msgId) {
        return msgdao.deleteMsgByMsgId(msgId);
    }

    public Integer checkMsgByMsgId(Integer msgId) {
        return msgdao.checkMsgByMsgId(msgId);
    }
}

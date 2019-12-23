package com.example.faq.persistences;

import com.example.faq.models.Msg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsgDao {
    Integer insertMsg(Msg msg);

    List<Msg> selectMsgListByReceiverId(@Param(value = "userId") Integer userId,
                                        @Param(value = "currentPage") Integer currentPage,
                                        @Param(value = "perPage") Integer perPage);

    Integer deleteMsgByDocId(Integer docId);

    Integer deleteMsgByMsgId(Integer msgId);

    Integer checkMsgByMsgId(Integer msgId);
}

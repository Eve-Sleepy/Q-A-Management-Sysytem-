package com.example.faq.persistences;

import com.example.faq.dto.DocCreateDto;
import com.example.faq.dto.DocEditDto;
import com.example.faq.dto.DocSearchDto;
import com.example.faq.models.Doc;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface DocDao {
    Doc findDocById(Integer id);
    Doc findDocByProductId(Integer id);
    List<Doc> selectDocList(@Param("docSearchDto") DocSearchDto docSearchDto);
    Integer insertDoc(DocCreateDto docCreateDto);
    Integer updateDoc(DocEditDto docEditDto);
    Integer deleteDoc(Integer id);
    Integer getDeptBelongNum(@Param("deptQuery") Map<String,Object> deptQuery);
    Integer deleteDocByProductId(Integer productId);
}

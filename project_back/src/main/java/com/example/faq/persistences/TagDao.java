package com.example.faq.persistences;

import com.example.faq.models.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface TagDao {
    ArrayList<Map<String,Object>> getTagIdNamesArr();
    Integer addTag(Tag tag);
    List<Tag> getTagList();
    List<Tag> findTagListByIds(@Param("tagIdGroup") String[] tagIdGroup);
    Tag findTagIdByName(@Param("tagName") String tagName);
}

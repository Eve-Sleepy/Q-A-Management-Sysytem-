package com.example.faq.persistences;

import com.example.faq.models.Tag;
import com.example.faq.models.TagForDoc;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagForDocDao {
    List<Tag> findTagsByDocId(Integer docId);

    Integer insertTagsForDoc(TagForDoc tagForDoc);

    Integer deleteTagsForDoc(Integer docId);
}

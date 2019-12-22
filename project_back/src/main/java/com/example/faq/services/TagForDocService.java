package com.example.faq.services;

import com.example.faq.models.Tag;
import com.example.faq.models.TagForDoc;
import com.example.faq.persistences.TagForDocDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagForDocService {
    @Autowired
    private TagForDocDao tagForDocDao;

    public ArrayList<String> findTagNameGroupByDocId(Integer id){
        ArrayList<String> tagNameGroup = new ArrayList<>();
        List<Tag> tags = tagForDocDao.findTagsByDocId(id);
        for(Tag tag:tags){
            tagNameGroup.add(tag.getName());
        }
        return  tagNameGroup;
    }

    public Integer addTagForDoc(TagForDoc tagForDoc) {

        return tagForDocDao.insertTagsForDoc(tagForDoc);
    }

    public Integer deleteTagsForDoc(Integer docId) {
        return tagForDocDao.deleteTagsForDoc(docId);
    }
}

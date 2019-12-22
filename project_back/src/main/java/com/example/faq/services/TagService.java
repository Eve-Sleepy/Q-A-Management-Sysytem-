package com.example.faq.services;

import com.example.faq.models.Tag;
import com.example.faq.persistences.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagDao tagDao;

    public ArrayList<String> findTagListByIds(String[] tagIdGroup){
        List<Tag> tags = tagDao.findTagListByIds(tagIdGroup);
        ArrayList<String> tagNameGroup = new ArrayList<>();

        for(Tag tag:tags){
            tagNameGroup.add(tag.getName());
        }
        return tagNameGroup;
    }

    public Integer findTagIdByName(String tagName){
        Tag tag = tagDao.findTagIdByName(tagName);
        if(tag != null){
            return tag.getId();
        }else{
            return null;
        }
    }

    public Integer addTag(Tag tag){
        if(tagDao.addTag(tag)>0){
            return tag.getId();
        }else{
            return null;
        }
    }

    public List<Tag> findTags(){
        return tagDao.getTagList();
    }
}

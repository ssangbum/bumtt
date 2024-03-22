package com.sangbum.bumtt.content.model.dao;

import com.sangbum.bumtt.content.model.dto.ContentAttachmentDTO;
import com.sangbum.bumtt.content.model.dto.ContentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContentDAO {

    int createContent(ContentDTO contentDTO);

    int createContentAttachment(ContentAttachmentDTO contentAttachmentDTO);

    List<ContentDTO> selectAllContentList();

    ContentDTO selectContentByContentNo(int contentNo);

    int updateContentStatusYnByContentNo(Map<String, Object> contentMap);
}

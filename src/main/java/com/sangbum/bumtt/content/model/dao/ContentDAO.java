package com.sangbum.bumtt.content.model.dao;

import com.sangbum.bumtt.content.model.dto.ContentAttachmentDTO;
import com.sangbum.bumtt.content.model.dto.ContentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentDAO {

    int createContent(ContentDTO contentDTO);

    int createContentAttachment(ContentAttachmentDTO contentAttachmentDTO);
}

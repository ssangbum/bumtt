package com.sangbum.bumtt.content.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContentAttachmentDTO {

    private int contentAttachmentNo;
    private String contentAttachmentOriginalName;
    private String contentAttachmentSaveName;
    private String contentAttachmentSavePath;
    private String contentAttachmentCategory;
}

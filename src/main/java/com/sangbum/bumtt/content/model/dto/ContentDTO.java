package com.sangbum.bumtt.content.model.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContentDTO {

    private int contentNo;
    private String contentTitle;
    private String contentStartDate;
    private String contentComment;
    private String contentDirector;
    private String contentActors;
    private String contentGrade;
    private String contentType;
    private String contentStatusYn;
}

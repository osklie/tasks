package com.crud.task.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BadgesDto {

    @JsonProperty("votes")
    private int votes;

    @JsonProperty("attachmentsByType")
    private AttachmentsByTypeDto attachments;
}

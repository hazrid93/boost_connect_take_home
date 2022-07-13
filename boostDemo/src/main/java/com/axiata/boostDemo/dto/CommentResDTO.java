package com.axiata.boostDemo.dto;

import lombok.Data;
import lombok.ToString;
import java.util.List;

@Data
@ToString
public class CommentResDTO {
    private List<CommentDTO> data;
    private String total;
    private String page;
    private String limit;
}

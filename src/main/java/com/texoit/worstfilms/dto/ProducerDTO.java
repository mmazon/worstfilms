package com.texoit.worstfilms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProducerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    String producer;
    int interval;
    int previousWin;
    int followingWin;

}

package com.texoit.worstfilms.rest;

import com.texoit.worstfilms.dto.ProducerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProducerResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ProducerDTO> min;
    private List<ProducerDTO> max;
}

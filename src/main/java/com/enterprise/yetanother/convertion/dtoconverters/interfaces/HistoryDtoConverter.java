package com.enterprise.yetanother.convertion.dtoconverters.interfaces;

import com.enterprise.yetanother.dto.history.HistoryDto;
import com.enterprise.yetanother.entities.History;

import java.util.List;

public interface HistoryDtoConverter {

    HistoryDto entityToDto(History history);
    List<HistoryDto> entitiesToDtos(List<History> histories);
}

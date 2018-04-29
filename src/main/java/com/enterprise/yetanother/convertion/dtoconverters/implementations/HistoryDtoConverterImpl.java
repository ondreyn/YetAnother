package com.enterprise.yetanother.convertion.dtoconverters.implementations;

import com.enterprise.yetanother.dto.history.HistoryDto;
import com.enterprise.yetanother.entities.History;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.HistoryDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *@author andrey
 */
@Component
public class HistoryDtoConverterImpl implements HistoryDtoConverter {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(HistoryDtoConverterImpl.class);

    @Override
    public HistoryDto entityToDto(History history) {
        if (history != null) {
            try {
                HistoryDto historyDto = new HistoryDto();
                historyDto.setAction(history.getAction());
                historyDto.setDate(history.getDate());
                historyDto.setDescription(history.getDescription());
                historyDto.setId(history.getId());
                historyDto.setTicket(history.getTicket());
                historyDto.setUser(history.getUser());
                return historyDto;
            } catch (Exception e) {
                LOGGER.error("[entityToDto: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[entityToDto: history is null!]");
        }
        return null;
    }

    @Override
    public List<HistoryDto> entitiesToDtos(List<History> histories) {
        if (histories != null) {
            try {
                List<HistoryDto> historyDtos = new ArrayList<>();
                for (History history: histories) {
                    historyDtos.add(entityToDto(history));
                }
                return historyDtos;
            } catch (Exception e) {
                LOGGER.error("[entitiesToDtos: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[entitiesToDtos: histories is null!]");
        }
        return null;
    }
}

package com.enterprise.yetanother.controllers.history;

import com.enterprise.yetanother.dto.history.HistoryDto;
import com.enterprise.yetanother.init.Properties;
import com.enterprise.yetanother.services.interfaces.HistoryService;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.HistoryDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *@author andrey
 */
@RestController
@RequestMapping(value = "/tickets/{id}")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private HistoryDtoConverter historyDtoConverter;

    @RequestMapping(value = "/histories/" + Properties.HISTORY_LENGTH,
                    method = RequestMethod.GET,
                    produces = "application/json")
    public List<HistoryDto> getLatestHistory(@PathVariable("id") Long id) {
        return historyDtoConverter.entitiesToDtos(
                historyService.getLatestHistory(id)
        );
    }

    @RequestMapping(value = "/histories", method = RequestMethod.GET,
                    produces = "application/json")
    public List<HistoryDto> getCompleteHistory(@PathVariable("id") Long id) {
        return historyDtoConverter.entitiesToDtos(
                historyService.getCompleteHistory(id)
        );
    }
}

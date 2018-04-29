package com.enterprise.yetanother.convertion.dtoconverters.implementations;

import com.enterprise.yetanother.convertion.dtovalidation.interfaces.FeedbackValidator;
import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.FeedbackDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@author andrey
 */
@Component
public class FeedbackDtoConverterImpl implements FeedbackDtoConverter {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(FeedbackDtoConverterImpl.class);

    @Autowired
    private FeedbackValidator feedbackValidator;

    @Override
    public Feedback dtoToEntity(FeedbackDto feedbackDto) {
        if (feedbackDto != null) {
            try {
                String text = feedbackDto.getText();
                Integer rate = feedbackDto.getRate();
                if (feedbackValidator.validate(feedbackDto)) {
                    Feedback feedback = new Feedback();
                    feedback.setDate(new Date());
                    feedback.setRate(rate);
                    feedback.setText(text);
                    return feedback;
                }
            } catch (Exception e) {
                LOGGER.error("[dtoToEntity: Exception thrown!]");
            }
        } else {
            LOGGER.warn("[dtoToEntity: feedbackDto is null!]");
        }
        return null;
    }

    @Override
    public FeedbackDto entityToDto(Feedback feedback) {
        if (feedback != null) {
            try {
                if (feedbackValidator.validate(feedback)) {
                    FeedbackDto feedbackDto = new FeedbackDto();
                    feedbackDto.setId(feedback.getId());
                    feedbackDto.setUser(feedback.getUser());
                    feedbackDto.setDate(feedback.getDate());
                    feedbackDto.setRate(feedback.getRate());
                    feedbackDto.setText(feedback.getText());
                    feedbackDto.setTicket(feedback.getTicket());
                    return feedbackDto;
                }
            } catch (Exception e) {
                LOGGER.error("[dtoToEntity: Exception thrown!]");
            }
        } else {
            LOGGER.warn("[dtoToEntity: feedback is null!]");
        }
        return null;
    }

    @Override
    public List<FeedbackDto> entitiesToDtos(List<Feedback> feedbacks) {
        if (feedbacks != null) {
            try {
                List<FeedbackDto> feedbackDtos = new ArrayList<>();
                for (Feedback feedback: feedbacks) {
                    feedbackDtos.add(entityToDto(feedback));
                }
                return feedbackDtos;
            } catch (Exception e) {
                LOGGER.error("[entitiesToDtos: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[entitiesToDtos: feedbacks is null!]");
        }
        return null;
    }
}

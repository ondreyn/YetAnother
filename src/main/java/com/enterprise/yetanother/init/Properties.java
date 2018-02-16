package com.enterprise.yetanother.init;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 *@author andrey
 */
@Component
public class Properties {

    public final static String[] FILE_FORMATS = {
        "image/jpeg",
        "image/png",
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/pdf",
        "application/x-pdf"
    };

    public final static String[] FILE_EXTENSIONS = {
        "jpeg", "png", "doc", "docx", "pdf"
    };

    private final static String[] CATEGORIES = {
            "APPLICATION_SERVICES",
            "BENEFITS_PAPERWORK",
            "HARDWARE_SOFTWARE",
            "PEOPLE_MANAGEMENT",
            "SECURITY_ACCESS",
            "WORKPLACES_FACILITIES"
    };
    public final static List<String> CATEGORY_NAMES = Arrays.asList(CATEGORIES);

    public final static int FILE_SIZE_LIMIT_MB = 5;
    public final static int FILE_SIZE_LIMIT = FILE_SIZE_LIMIT_MB*1024*1024;

    public final static String TEXT_REGEXP = "^[\\w\\d\\s!-/:-@\\[\\]-`{-~]{0,500}$";
    public final static String NAME_REGEXP = "^[\\w\\d!-/:-@\\[\\]-`{-~]{1,100}$";
    public final static String EMAIL_REGEXP = "^[^@.].*@.+\\..*[^@.]$";
    public final static int EMAIL_LENGTH = 100;
    public final static String PASSWORD_REGEXP = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!-/:-@\\[\\]-`\\{-~])(?!.*\\s).*$";
    public final static int PASSWORD_LENGTH_MAX = 20;
    public final static int PASSWORD_LENGTH_MIN = 6;

    public static final String[] NEW_TICKET = {
            "New ticket for approval", "newTicket"
    };
    public static final String[] APPROVED_BY_MANAGER = {
            "Ticket was approved", "approvedByManager"
    };
    public static final String[] DECLINED_BY_MANAGER = {
            "Ticket was declined", "declinedByManager"
    };
    public static final String[] CANCELLED_BY_MANAGER = {
            "Ticket was cancelled", "cancelledByManager"
    };
    public static final String[] CANCELLED_BY_ENGINEER = {
            "Ticket was cancelled", "cancelledByEngineer"
    };
    public static final String[] DONE_BY_ENGINEER = {
            "Ticket was done", "doneByEngineer"
    };
    public static final String[] FEEDBACK_PROVIDED = {
            "Feedback was provided", "feedbackProvided"
    };

    public final static int HISTORY_LENGTH = 5;
    public final static int COMMENTS_LENGTH = 5;
}

package com.enterprise.yetanother.utilities;

import com.enterprise.yetanother.entities.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 *@author andrey
 */
@Component
public class Commons {

    final static Logger LOGGER = LoggerFactory.getLogger(Commons.class);

    public static MultipartFile[] getAttachments(
            MultipartHttpServletRequest request) {

        try {
            int length = request.getFiles("file").size();
            MultipartFile[] uploaded = new MultipartFile[length];
            request.getFiles("file").toArray(uploaded);

            LOGGER.info(String.format("[getAttachments: attachmentsModel " +
                        "worked, got: %d file/s]", request.
                        getFiles("file").size()));

            return uploaded;
        } catch (Exception e) {
            LOGGER.warn("[getAttachments: attachmentsModel came empty!]");
            return null;
        }
    }

    public static List<String> getAttachmentsFileNames(
                               List<Attachment> attachments) {

        List<String> fileNames = new ArrayList<>();
        try {
            if (attachments != null && !attachments.isEmpty()) {
                for (Attachment attachment: attachments) {
                    fileNames.add(attachment.getFileName());
                }
                LOGGER.info(String.format("[getAttachmentsFileNames: got " +
                            "attachments %s]", fileNames));

                return fileNames;
            } else {
                LOGGER.warn("[getAttachmentsFileNames: attachments are null" +
                            " or empty]");
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("[getAttachmentsFileNames: exception]", e);
            return null;
        }
    }

    public static String defineContentType(String fileName) {
        String extension;

        try {
            extension = fileName.split("\\.")[1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            extension = "";
        }

        if (extension.equalsIgnoreCase("pdf")) {
            return "application/pdf";
        }
        if (extension.equalsIgnoreCase("png")) {
            return "image/png";
        }
        if (extension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (extension.equalsIgnoreCase("docx")) {
            return "application/vnd.openxmlformats-" +
                    "officedocument.wordprocessingml.document";
        }
        if (extension.equalsIgnoreCase("jpg")) {
            return "image/jpeg";
        }
        if (extension.equalsIgnoreCase("jpeg")) {
            return "image/jpeg";
        }

        return "application/octet-stream";
    }

    public static String getDescriptionOnFilesAttach(List<String> fileNames,
                                                     String action) {
        String description;

        if (fileNames.size() == 1) {
            description = action + ": \"" + fileNames.get(0) + "\"";
        } else {
            description = action + ": \"";

            for (int i = 0; i < fileNames.size() - 1; i++) {
                description += fileNames.get(i) + ", \"";
            }
            description += fileNames.get(fileNames.size() - 1) + "\"";
        }
        return description;
    }

    public static boolean isNulls(Object... objects) {
        for (Object object: objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }
}

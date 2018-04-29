package com.enterprise.yetanother.test.utilities;

import com.enterprise.yetanother.entities.Attachment;
import com.enterprise.yetanother.utilities.Commons;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommonsTest {

    final static Logger LOGGER = LoggerFactory.getLogger(CommonsTest.class);

    @Test
    public void getAttachmentsTest() throws IOException {
        LOGGER.info("[getAttachmentsTest GO]");

        MockMultipartHttpServletRequest request =
                                        new MockMultipartHttpServletRequest();
        request.addFile(new MockMultipartFile("file", new byte[]{1,2,3}));
        MultipartFile[] files = Commons.getAttachments(request);

        Assert.assertArrayEquals(new byte[]{1,2,3}, files[0].getBytes());
    }

    @Test(expected = AssertionError.class)
    public void getAttachmentsFailedTest() throws IOException {
        LOGGER.info("[getAttachmentsFailedTest GO]");

        MockMultipartHttpServletRequest request =
                                        new MockMultipartHttpServletRequest();
        request.addFile(new MockMultipartFile("file", new byte[]{1,2,4}));
        MultipartFile[] files = Commons.getAttachments(request);

        Assert.assertArrayEquals(new byte[]{1,2}, files[0].getBytes());
    }

    @Test
    public void getAttachmentsFileNamesTest() {
        LOGGER.info("[getAttachmentsFileNamesTest GO]");

        List<Attachment> attachments = new ArrayList<>();
        Attachment attachment = new Attachment();
        attachment.setFileName("file.doc");
        attachment.setBlob(new byte[]{1,2,3});
        attachments.add(attachment);
        List<String> fileNames = Commons.getAttachmentsFileNames(attachments);

        Assert.assertArrayEquals(new String[]{"file.doc"}, fileNames.toArray());
    }

    @Test(expected = AssertionError.class)
    public void getAttachmentsFileNamesFailedTest() {
        LOGGER.info("[getAttachmentsFileNamesFailedTest GO]");

        List<Attachment> attachments = new ArrayList<>();
        Attachment attachment = new Attachment();
        attachment.setFileName("files.docx");
        attachment.setBlob(new byte[]{1,2,3});
        attachments.add(attachment);
        List<String> fileNames = Commons.getAttachmentsFileNames(attachments);

        Assert.assertArrayEquals(new String[]{"file.doc"}, fileNames.toArray());
    }

    @Test
    public void defineContentTypeTest() {
        LOGGER.info("[defineContentTypeTest GO]");

        String file = "file.jpeg";
        String result = Commons.defineContentType(file);

        Assert.assertEquals("image/jpeg", result);
    }

    @Test(expected = AssertionError.class)
    public void defineContentTypeFailedTest() {
        LOGGER.info("[defineContentTypeFailedTest GO]");

        String file = "file.png";
        String result = Commons.defineContentType(file);

        Assert.assertEquals("image/jpeg", result);
    }
}
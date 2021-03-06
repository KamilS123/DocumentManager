package com.kamil.DocumentManager.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ModeratorServiceTest {
    @Spy
    private ModeratorService moderatorService;

    @Test
    public void should_return_showAllDocs_from_switch() {
        String modChoose = "showAllDocs";
        Assert.assertTrue(moderatorService.moderatorRadioChoose(modChoose).equals("redirect:" + modChoose));

    }
}
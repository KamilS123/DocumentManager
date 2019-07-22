package com.kamil.DocumentManager.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModeratorServiceTest {
    @Mock
    private ModeratorService moderatorService;

    @Test
    public void should_return_showAllDocs_from_switch() {
        //given
        String modChoose = "showAllDocs";
        //when
        when(moderatorService.moderatorRadioChoose(modChoose)).thenReturn("redirect:"+modChoose);
        //than
        Assert.assertTrue(moderatorService.moderatorRadioChoose(modChoose).equals("redirect:"+modChoose));

    }
}
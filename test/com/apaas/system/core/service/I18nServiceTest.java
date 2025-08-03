package com.apaas.system.core.service;

import com.apaas.system.core.domain.Language;
import com.apaas.system.core.domain.Translation;
import com.apaas.system.core.mapper.LanguageMapper;
import com.apaas.system.core.mapper.TranslationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class I18nServiceTest {

    @Mock
    private LanguageMapper languageMapper;

    @Mock
    private TranslationMapper translationMapper;

    @InjectMocks
    private I18nServiceImpl i18nService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLanguage() {
        // Given
        Language language = new Language();
        language.setLangCode("zh-CN");
        language.setLangName("中文");
        language.setIsDefault(1);
        language.setStatus(1);

        // When
        when(languageMapper.insertLanguage(language)).thenReturn(1);
        boolean result = i18nService.createLanguage(language);

        // Then
        assertTrue(result);
        verify(languageMapper, times(1)).insertLanguage(language);
    }

    @Test
    void testCreateLanguageWithExistingCode() {
        // Given
        Language language = new Language();
        language.setLangCode("en-US");
        language.setLangName("English");
        language.setStatus(1);

        // When
        when(languageMapper.selectLanguageByCode("en-US")).thenReturn(new Language());
        boolean result = i18nService.createLanguage(language);

        // Then
        assertFalse(result);
        verify(languageMapper, never()).insertLanguage(any(Language.class));
    }

    @Test
    void testGetLanguageById() {
        // Given
        Long id = 1L;
        Language expected = new Language();
        expected.setId(id);
        expected.setLangCode("zh-CN");
        expected.setLangName("中文");

        // When
        when(languageMapper.selectLanguageById(id)).thenReturn(expected);
        Language result = i18nService.getLanguageById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("zh-CN", result.getLangCode());
        assertEquals("中文", result.getLangName());
    }

    @Test
    void testUpdateLanguage() {
        // Given
        Language language = new Language();
        language.setId(1L);
        language.setLangCode("zh-CN");
        language.setLangName("简体中文");
        language.setStatus(1);

        // When
        when(languageMapper.updateLanguage(language)).thenReturn(1);
        boolean result = i18nService.updateLanguage(language);

        // Then
        assertTrue(result);
        verify(languageMapper, times(1)).updateLanguage(language);
    }

    @Test
    void testDeleteLanguage() {
        // Given
        Long id = 1L;
        String langCode = "zh-CN";
        Language language = new Language();
        language.setId(id);
        language.setLangCode(langCode);
        language.setIsDefault(0);

        Translation translation1 = new Translation();
        translation1.setId(1L);
        translation1.setLangCode(langCode);
        translation1.setResourceKey("key1");

        Translation translation2 = new Translation();
        translation2.setId(2L);
        translation2.setLangCode(langCode);
        translation2.setResourceKey("key2");

        List<Translation> translations = Arrays.asList(translation1, translation2);

        // When
        when(languageMapper.selectLanguageById(id)).thenReturn(language);
        when(translationMapper.selectTranslationsByLangCode(langCode)).thenReturn(translations);
        when(translationMapper.deleteTranslation(1L)).thenReturn(1);
        when(translationMapper.deleteTranslation(2L)).thenReturn(1);
        when(languageMapper.deleteLanguage(id)).thenReturn(1);
        boolean result = i18nService.deleteLanguage(id);

        // Then
        assertTrue(result);
        verify(translationMapper, times(1)).deleteTranslation(1L);
        verify(translationMapper, times(1)).deleteTranslation(2L);
        verify(languageMapper, times(1)).deleteLanguage(id);
    }

    @Test
    void testListLanguages() {
        // Given
        Language lang1 = new Language();
        lang1.setId(1L);
        lang1.setLangCode("zh-CN");

        Language lang2 = new Language();
        lang2.setId(2L);
        lang2.setLangCode("en-US");

        List<Language> expected = Arrays.asList(lang1, lang2);

        // When
        when(languageMapper.selectLanguageList(any())).thenReturn(expected);
        List<Language> result = i18nService.listLanguages(new Language());

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("zh-CN", result.get(0).getLangCode());
        assertEquals("en-US", result.get(1).getLangCode());
    }

    @Test
    void testSetDefaultLanguage() {
        // Given
        Long id = 1L;
        Language language = new Language();
        language.setId(id);
        language.setLangCode("zh-CN");

        // When
        when(languageMapper.selectLanguageById(id)).thenReturn(language);
        when(languageMapper.updateDefaultLanguage(0)).thenReturn(1); // Reset all defaults
        when(languageMapper.updateLanguageIsDefault(id, 1)).thenReturn(1); // Set new default
        boolean result = i18nService.setDefaultLanguage(id);

        // Then
        assertTrue(result);
        verify(languageMapper, times(1)).updateDefaultLanguage(0);
        verify(languageMapper, times(1)).updateLanguageIsDefault(id, 1);
    }

    @Test
    void testCreateTranslation() {
        // Given
        Translation translation = new Translation();
        translation.setResourceKey("menu.home");
        translation.setLangCode("zh-CN");
        translation.setTranslatedValue("首页");
        translation.setModule("system");
        translation.setType("menu");

        // When
        when(translationMapper.insertTranslation(translation)).thenReturn(1);
        boolean result = i18nService.createTranslation(translation);

        // Then
        assertTrue(result);
        verify(translationMapper, times(1)).insertTranslation(translation);
    }

    @Test
    void testUpdateTranslation() {
        // Given
        Translation translation = new Translation();
        translation.setId(1L);
        translation.setResourceKey("menu.home");
        translation.setLangCode("zh-CN");
        translation.setTranslatedValue("主页");

        // When
        when(translationMapper.updateTranslation(translation)).thenReturn(1);
        boolean result = i18nService.updateTranslation(translation);

        // Then
        assertTrue(result);
        verify(translationMapper, times(1)).updateTranslation(translation);
    }

    @Test
    void testDeleteTranslation() {
        // Given
        Long id = 1L;

        // When
        when(translationMapper.deleteTranslation(id)).thenReturn(1);
        boolean result = i18nService.deleteTranslation(id);

        // Then
        assertTrue(result);
        verify(translationMapper, times(1)).deleteTranslation(id);
    }

    @Test
    void testGetTranslationByKeyAndLang() {
        // Given
        String resourceKey = "menu.home";
        String langCode = "zh-CN";
        Translation expected = new Translation();
        expected.setResourceKey(resourceKey);
        expected.setLangCode(langCode);
        expected.setTranslatedValue("首页");

        // When
        when(translationMapper.selectTranslationByKeyAndLang(resourceKey, langCode)).thenReturn(expected);
        Translation result = i18nService.getTranslationByKeyAndLang(resourceKey, langCode);

        // Then
        assertNotNull(result);
        assertEquals(resourceKey, result.getResourceKey());
        assertEquals(langCode, result.getLangCode());
        assertEquals("首页", result.getTranslatedValue());
    }

    @Test
    void testGetTranslationsByModule() {
        // Given
        String module = "system";
        Translation translation1 = new Translation();
        translation1.setId(1L);
        translation1.setResourceKey("menu.home");
        translation1.setModule(module);

        Translation translation2 = new Translation();
        translation2.setId(2L);
        translation2.setResourceKey("menu.user");
        translation2.setModule(module);

        List<Translation> expected = Arrays.asList(translation1, translation2);

        // When
        when(translationMapper.selectTranslationsByModule(module)).thenReturn(expected);
        List<Translation> result = i18nService.getTranslationsByModule(module);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(module, result.get(0).getModule());
        assertEquals(module, result.get(1).getModule());
    }

    @Test
    void testGetTranslationsByLangCode() {
        // Given
        String langCode = "zh-CN";
        Map<String, String> expected = new HashMap<>();
        expected.put("menu.home", "首页");
        expected.put("menu.user", "用户");

        // When
        when(translationMapper.selectTranslationsByLangCode(langCode)).thenReturn(expected);
        Map<String, String> result = i18nService.getTranslationsByLangCode(langCode);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("首页", result.get("menu.home"));
        assertEquals("用户", result.get("menu.user"));
    }

    @Test
    void testDisableLanguage() {
        // Given
        Long id = 1L;
        Language language = new Language();
        language.setId(id);
        language.setStatus(1);
        language.setIsDefault(0);

        // When
        when(languageMapper.selectLanguageById(id)).thenReturn(language);
        when(languageMapper.updateLanguageStatus(id, 0)).thenReturn(1);
        boolean result = i18nService.disableLanguage(id);

        // Then
        assertTrue(result);
        assertEquals(0, language.getStatus());
        verify(languageMapper, times(1)).updateLanguageStatus(id, 0);
    }

    @Test
    void testEnableLanguage() {
        // Given
        Long id = 1L;
        Language language = new Language();
        language.setId(id);
        language.setStatus(0);

        // When
        when(languageMapper.selectLanguageById(id)).thenReturn(language);
        when(languageMapper.updateLanguageStatus(id, 1)).thenReturn(1);
        boolean result = i18nService.enableLanguage(id);

        // Then
        assertTrue(result);
        assertEquals(1, language.getStatus());
        verify(languageMapper, times(1)).updateLanguageStatus(id, 1);
    }
        verify(languageMapper, times(1)).updateLanguageStatus(id, 0);
    }

    @Test
    void testEnableLanguage() {
        // Given
        Long id = 1L;
        Language language = new Language();
        language.setId(id);
        language.setStatus(0);

        // When
        when(languageMapper.selectLanguageById(id)).thenReturn(language);
        when(languageMapper.updateLanguageStatus(id, 1)).thenReturn(1);
        boolean result = i18nService.enableLanguage(id);

        // Then
        assertTrue(result);
        assertEquals(1, language.getStatus());
        verify(languageMapper, times(1)).updateLanguageStatus(id, 1);
    }
}
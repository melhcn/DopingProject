package com.dopingProject.service.impl;

import com.dopingProject.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestServiceImplTest {

    private TestRepository testRepository;
    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        testRepository = mock(TestRepository.class);
        testService = new TestServiceImpl(testRepository);
    }

    @Test
    void getAllTests_returnsList() {
        com.dopingProject.model.Test t1 = new com.dopingProject.model.Test();
        t1.setId(1L);

        when(testRepository.findAll()).thenReturn(List.of(t1));

        List<com.dopingProject.model.Test> result = testService.getAllTests();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void getTestById_found_returnsTest() {
        com.dopingProject.model.Test t = new com.dopingProject.model.Test();
        t.setId(2L);

        when(testRepository.findById(2L)).thenReturn(Optional.of(t));

        com.dopingProject.model.Test found = testService.getTestById(2L);

        assertNotNull(found);
        assertEquals(2L, found.getId());
    }

    @Test
    void getTestById_notFound_returnsNull() {
        when(testRepository.findById(3L)).thenReturn(Optional.empty());

        com.dopingProject.model.Test found = testService.getTestById(3L);

        assertNull(found);
    }

    @Test
    void createTest_savesTest() {
        com.dopingProject.model.Test t = new com.dopingProject.model.Test();
        t.setTestName("Test Name");

        when(testRepository.save(any(com.dopingProject.model.Test.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        com.dopingProject.model.Test saved = testService.createTest(t);

        assertNotNull(saved);
        assertEquals("Test Name", saved.getTestName());

        ArgumentCaptor<com.dopingProject.model.Test> captor = ArgumentCaptor.forClass(com.dopingProject.model.Test.class);
        verify(testRepository).save(captor.capture());
        assertEquals("Test Name", captor.getValue().getTestName());
    }
}

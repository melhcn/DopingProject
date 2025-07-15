package com.dopingProject.service;

import com.dopingProject.model.Test;
import java.util.List;

public interface TestService {
    List<Test> getAllTests();
    Test getTestById(Long id);
    Test createTest(Test test);
}

package com.example.services;

import com.example.domain.Education;

public interface EducationService {
    Iterable<Education> listAllEducations();
    Education getEducationById(Integer id);
    Education saveEducation(Education education);
}

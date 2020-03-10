package com.example.services;

import com.example.domain.Education;

public interface EducationService {

    Iterable<Education> listAllEducations();

    Iterable<Education> listEducationsByPersonId(Integer id);

    Education getEducationById(Integer id);

    Education saveEducation(Education education);
}

package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.stream.Collector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Joshua.H.Brooks
 * @description
 * @date 2024-03-22 22:53
 */
@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {
    @Autowired
    ApplicationContext context;
    @Autowired
    StudentGrades studentGrades;

    @Autowired
    CollegeStudent collegeStudent;

    //@Mock
    @MockBean
    ApplicationDao dao;

    //@InjectMocks
    @Autowired
    ApplicationService service;

    @BeforeEach

    public void beforeEach() {
        collegeStudent.setFirstname("Eric");
        collegeStudent.setLastname("Roby");
        collegeStudent.setEmailAddress("eric.roby@luv2code_school.com");
        collegeStudent.setStudentGrades(studentGrades);
        System.out.println("collegeStudent = " + collegeStudent);
    }

    @Test
    @DisplayName("Mocking When & then, with verify")
    public void createStudentWithoutGradesInit() {
        //set up expectation
        when(dao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.00);
        // call the method
        assertEquals(100.00, service.addGradeResultsForSingleClass(studentGrades.getMathGradeResults()));
        //verify the dao method is called
        verify(dao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
        //verify the dao method is called wantedNumberOfInvocation times
        verify(dao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @Test
    @DisplayName("throw Runtime exception")
    void throwRuntimeException() throws Exception {
        CollegeStudent nullStudent = (CollegeStudent) context.getBean(CollegeStudent.class);
        when(dao.checkNull(nullStudent)).thenThrow(new RuntimeException());
        // call the method
        assertThrows(RuntimeException.class, () -> service.checkNull(nullStudent));
        //verify the dao method is called
        verify(dao).checkNull(nullStudent);
        //verify the dao method is called wantedNumberOfInvocation times
        verify(dao,times(1)).checkNull(nullStudent);
    }

    @Test
    @DisplayName("Multiple stubbing")
    void multipleStubbing() throws Exception {
        CollegeStudent nullStudent = (CollegeStudent) context.getBean(CollegeStudent.class);
        when(dao.checkNull(nullStudent)).thenThrow(new RuntimeException()).thenReturn("Now we're returning something after thrown");
        //stubbing one: assert throw
        assertThrows(RuntimeException.class, () -> service.checkNull(nullStudent));
        //stubbing two: assert returned string equals the expected string
        assertEquals("Now we're returning something after thrown",service.checkNull(nullStudent));
    }

}

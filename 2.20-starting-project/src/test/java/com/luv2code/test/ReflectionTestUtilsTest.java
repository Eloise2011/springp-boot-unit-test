package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Joshua.H.Brooks
 * @description
 * @date 2024-03-26 08:47
 */
@SpringBootTest(classes= MvcTestingExampleApplication.class)
public class ReflectionTestUtilsTest {
    @Autowired
    ApplicationContext context;
    @Autowired
    StudentGrades studentGrades;
    @Autowired
    CollegeStudent cStudent;

    @BeforeEach
    public void beforeEach() {
        cStudent.setFirstname("Eric");
        cStudent.setLastname("Roby");
        cStudent.setEmailAddress("eric.roby@luv2code_school.com");
        ReflectionTestUtils.setField(cStudent, "id",1);
        ReflectionTestUtils.setField(cStudent, "studentGrades",new StudentGrades(new ArrayList<>(Arrays.asList(
                100.00, 85.0, 76.50, 91.75
        ))));
        cStudent.setStudentGrades(studentGrades);
        System.out.println("collegeStudent = " + cStudent);
    }
    
    @Test
    public void test() {
        Object id = ReflectionTestUtils.getField(cStudent, "id");
        assert id != null; // add an assertion here, if id not null, AssertionError will be thrown, otherwise proceed to next line.
        System.out.println("id.toString() = " + id.toString());
        System.out.println(cStudent.toString());

        assertEquals(1,ReflectionTestUtils.getField(cStudent, "id"));
    }

    @Test
    public void testInvokePrivateMethod() {
        assertEquals("1 Eric",ReflectionTestUtils.invokeMethod(cStudent,"getIdAndFirstName"),"Even though getFirstName is a private method, can be accessed by ReflectionTestUtils");
    }
}

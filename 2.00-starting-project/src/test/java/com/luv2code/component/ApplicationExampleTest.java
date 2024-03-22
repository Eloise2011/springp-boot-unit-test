package com.luv2code.component;

import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Joshua.H.Brooks
 * @description
 * @date 2024-03-22 12:54
 */
//@SpringBootTest(classes = MvcTestingExampleApplication.class) // Method 2
//@SpringBootConfiguration // Method 3
@SpringBootTest
public class ApplicationExampleTest {
    static int count = 0;
    @Value("${info.school.name}")
    String schoolName;
    @Value("${info.app.name}")
    String appInfo;
    @Value("${info.app.description}")
    String appDescription;
    @Value("${info.app.version}")
    String appVersion;


    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent student;
    @Autowired
    StudentGrades studentGrades;

    @BeforeEach

    public void beforeEach() {
        count = count + 1;
        System.out.println("Testing: " + appInfo + " which is " + appDescription + "Version: " + appVersion + ", and Execution of method: " + count);
        student.setFirstname("Eric");
        student.setLastname("Roby");
        student.setEmailAddress("eric.roby@luv2code_school.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
        student.setStudentGrades(studentGrades);
    }

    @Test
    void basicTest() throws Exception {
        System.out.println("Hi testing");
    }

    @Test
    void advancedTest() throws Exception {
        System.out.println("Hi testing");
    }

    @Test
    @DisplayName("add results")
    void testAddResults() throws Exception {
        assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        ), "Correct grade results: 353.25");
    }

    @Test
    @DisplayName("add results for grade results")
    void testAddResults2() throws Exception {
        assertNotEquals(32, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        ), "The expected sum 32 shall not be same to actual result");
    }

    @Test
    @DisplayName("isGreater than")
    void testIsGreater() throws Exception {
        assertTrue(studentGrades.isGradeGreater(100.0, 85.0), "100.0 is greater than 85.0");
    }

    ;

    @Test
    @DisplayName("isLess than")
    void testIsLess() throws Exception {
        assertFalse(studentGrades.isGradeGreater(80, 85.0), "80.0 is less than 85.0");
    }

    @Test
    @DisplayName("grade not null")
    void testGradeNotNull() throws Exception {
        assertNotNull(student.getStudentGrades().getMathGradeResults(),"grade must not be null");
    }


    @Test
    @DisplayName("create a student without grade init")
    void testCreateStudentWithoutGradeInit() throws Exception {
        CollegeStudent stu2 = context.getBean("collegeStudent",CollegeStudent.class);
        System.out.println("stu2 = " + stu2);
        stu2.setFirstname("John");
        stu2.setLastname("Smith");
        stu2.setEmailAddress("John@smith.com");
        assertNotNull(stu2.getFirstname(), "Firstname must not be null");
        assertNotNull(stu2.getLastname(), "Lastname must not be null");
        assertNotNull(stu2.getEmailAddress(), "Email address must not be null");
        assertNull(studentGrades.checkNull(stu2.getStudentGrades()),"we have NOT init the grade, shall PASS here");
        System.out.println("stu2 = " + stu2);
    }

    @Test
    @DisplayName("Verify students are prototypes")
    void testVerifyStudentsArePrototypes() throws Exception {
        CollegeStudent stu2 = context.getBean("collegeStudent", CollegeStudent.class);
        /** student is autowired above,
         * stu2 is just gotten from the context by calling the getBean method,
         * since we've defined the scope of this bean in the app class as prototype,
         * we now got two different objects
         * Hence, this test passed.
         */
        assertNotSame(stu2,student,"shall not be the same one");
    }
}
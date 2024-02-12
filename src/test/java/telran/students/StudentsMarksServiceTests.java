package telran.students;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import telran.students.dto.*;
import telran.students.exceptions.*;
import telran.students.model.StudentDoc;
import telran.students.repo.StudentRepo;
import telran.students.service.StudentsService;

@SpringBootTest

class StudentsMarksServiceTests {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    StudentsService studentService;
    
    Student student = new Student(123l, "0555555555");
    Student student1 = new Student(124l, "0555555555");
    Mark mark = new Mark("History", 5, LocalDate.now());
    
    @AfterEach
    void clearRepo() {
        studentRepo.deleteAll(); 
    }
    @Test 
    void testAddStudent() {
        assertEquals(student, studentService.addStudent(student));
        assertThrowsExactly(StudentIllegalStateException.class,
				()->studentService.addStudent(student));
		StudentDoc studentDoc = studentRepo.findById(student.id()).orElse(null);
		assertEquals(student, studentDoc.build());
    }
    @Test
    void testUpdatePhoneNumber() {
    	studentService.addStudent(student);
    	Student studentUpdated = new Student(123l, "0566666666");
		assertEquals(studentUpdated, studentService.updatePhoneNumber(123l, "0566666666"));
		assertEquals("0566666666", studentRepo.findById(studentUpdated.id()).get().getPhone());
		assertThrowsExactly(StudentNotFoundException.class,
				() -> studentService.updatePhoneNumber(124l, "0566666666"));
	}
    @Test
    void testAddMark() {
    	studentService.addStudent(student);
    	assertEquals(mark, studentService.addMark(123l, mark));
    	assertEquals(mark, studentRepo.findById(student.id()).get().getMarks().get(0));
    	assertThrowsExactly(StudentNotFoundException.class,
				() -> studentService.addMark(124l, mark));
    	assertThrowsExactly(MarkIllegalStateException.class,
				() -> studentService.addMark(123l, mark));
    	
    }
}

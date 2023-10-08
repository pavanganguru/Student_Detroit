package com.example.student.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.controller.exceptions.BadFormatException;
import com.example.student.controller.exceptions.NameValidator;
import com.example.student.controller.exceptions.RequiredFieldException;
import com.example.student.controller.exceptions.ResourceNotFoundException;
import com.example.student.dto.StudentDTO;
import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;

@RestController
@RequestMapping("/api")
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	@PostMapping("/student")
	//@PreAuthorize("hasAuthority('ADMIN')")
	public StudentDTO createStudent(@RequestBody StudentDTO studentRequestDTO) throws ParseException {
		
		
		Student studentEntity = new Student();
		studentEntity.setFirstName(studentRequestDTO.getFirstName());
		studentEntity.setLastName(studentRequestDTO.getLastName());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(studentRequestDTO.getDob());
		studentEntity.setDOB(date);
		
		boolean isFirstNameFormatted = NameValidator.isValidName(studentRequestDTO.getFirstName());
		boolean isLastNameFormatted = NameValidator.isValidName(studentRequestDTO.getLastName());
		boolean isDOBFormatted = false;
		
		if(studentRequestDTO.getFirstName().length()==0 || studentRequestDTO.getLastName().length()==0)
			throw new RequiredFieldException("Please provide the Required fields");
		
		if(isFirstNameFormatted || isLastNameFormatted || isDOBFormatted) {
			throw new BadFormatException("Please provide the details in proper format, make sure no numbers");
		}
		
		studentEntity=studentRepository.save(studentEntity);
		
		StudentDTO studentResponseDTO = new StudentDTO();
		
		
		studentResponseDTO.setFirstName(studentEntity.getFirstName());
		studentResponseDTO.setLastName(studentEntity.getLastName());
		studentResponseDTO.setDob(studentEntity.getDOB().toString());
		studentResponseDTO.setId(studentEntity.getId());
		
		
		return studentResponseDTO;
	}
	
	//Get all Students
	@GetMapping("/student")
	public List<StudentDTO> getStudents(){
		List<Student> studentList = studentRepository.findAll(); 
		List<StudentDTO> studentResponseList =  studentList.stream().map(student->
			new StudentDTO(student.getFirstName(),student.getId(),student.getLastName(),student.getDOB().toString())
		).collect(Collectors.toList());
		return studentResponseList;
	}
	
	@DeleteMapping("/student/{id}")
	public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id) {
		if(studentRepository.existsById(id)) {
			studentRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			throw new ResourceNotFoundException("No Record Found with ID "+id);
		}
	}
	
	@GetMapping("/student/{id}")
	public ResponseEntity<StudentDTO> findById(@PathVariable("id") long id) {
		if(studentRepository.existsById(id)) {
			Student temp_Student = studentRepository.findById(id).get();
			StudentDTO studentDTO =  new StudentDTO(temp_Student.getFirstName(), (int)id, temp_Student.getLastName(), temp_Student.getDOB().toString());
			return new ResponseEntity<StudentDTO>(studentDTO,HttpStatus.ACCEPTED);
		}
		else 
		throw new ResourceNotFoundException("No Record Found with ID "+id);
	}
	
	@PutMapping("/student/{id}")
	public ResponseEntity<StudentDTO> updateStudent(@PathVariable long id, @RequestBody StudentDTO studentRequestDTO) throws ParseException {
		Optional<Student> student = studentRepository.findById(id);
		if(student.isPresent()) {
			Student tempStudent = student.get();
			tempStudent.setFirstName(studentRequestDTO.getFirstName());
			tempStudent.setLastName(studentRequestDTO.getLastName());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(studentRequestDTO.getDob());
			tempStudent.setDOB(date);
		    studentRepository.save(tempStudent);
		} else {
			throw new ResourceNotFoundException("No Record Found with ID "+id);
		}
		return findById(id);
	}
	

}

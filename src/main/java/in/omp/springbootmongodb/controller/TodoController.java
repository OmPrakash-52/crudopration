package in.omp.springbootmongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.omp.springbootmongodb.model.TodoDTO;
import in.omp.springbootmongodb.repository.TodoRepository;

@RestController
public class TodoController {
	
	@Autowired
	private TodoRepository todorespo;
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos(){
		List<TodoDTO> todos = todorespo.findAll();
		if(todos.size()>0) {
		
			return new ResponseEntity<List>(todos,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
			
		}
	}

}

package in.omp.springbootmongodb.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
		try {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			todorespo.save(todo);
			return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
		}
		catch (Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getsingleTodo(@PathVariable("id") String id){
		Optional<TodoDTO> todoOptional = todorespo.findById(id);
		if(todoOptional.isPresent()) {
		return new ResponseEntity<>(todoOptional.get(),HttpStatus.OK);	
		}
		else {
			return new ResponseEntity<>("Todo is not found with"+id,HttpStatus.NOT_FOUND); 
		}
	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<?> UpdateById(@PathVariable("id") String id,@RequestBody TodoDTO todo){
		Optional<TodoDTO> todoOptional = todorespo.findById(id);
		if(todoOptional.isPresent()) {
		TodoDTO todoToSave = todoOptional.get();
		todoToSave.setCompleted(todo.getCompleted()!=null ? todo.getCompleted():todoToSave.getCompleted());
		todoToSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToSave.getTodo());
		todoToSave.setDeclaration(todo.getDeclaration()  != null ? todo.getDeclaration() : todoToSave.getDeclaration());
		todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
		todorespo.save(todoToSave);
		return new ResponseEntity<>(todoToSave,HttpStatus.OK);
		
		}
		else {
			return new ResponseEntity<>("Todo is not found with"+id,HttpStatus.NOT_FOUND); 
		}
	}
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id){
		try {
			todorespo.deleteById(id);
			return new ResponseEntity<>("Successfully Deleted with id"+id,HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	
	
}

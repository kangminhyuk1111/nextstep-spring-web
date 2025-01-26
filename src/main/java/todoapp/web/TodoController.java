package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.todo.application.FindTodos;
import todoapp.core.todo.domain.support.SpreadsheetConverter;

import java.util.Objects;

@Controller
public class TodoController {

    private final FindTodos findTodos;

    public TodoController(final FindTodos findTodos) {
        this.findTodos = Objects.requireNonNull(findTodos);
    }

    @RequestMapping("/todos")
    public String todos() {
        return "todos";
    }

    @RequestMapping(path = "/todos", produces = "text/csv")
    public void downloadTodos(Model model) {
        model.addAttribute("todos", SpreadsheetConverter.convert(findTodos.all()));
    }
}

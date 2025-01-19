package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.todo.application.FindTodos;
import todoapp.core.todo.domain.support.SpreadsheetConverter;
import todoapp.web.model.SiteProperties;

import java.util.Objects;

@Controller
public class TodoController {

    private final FindTodos findTodos;
    private final SiteProperties siteProperties;

    public TodoController(final FindTodos findTodos, final SiteProperties siteProperties) {
        this.findTodos = Objects.requireNonNull(findTodos);
        this.siteProperties = Objects.requireNonNull(siteProperties);
    }

    @RequestMapping("/todos")
    public void todos(Model model) {
        model.addAttribute("site", siteProperties);
    }

    @RequestMapping(path = "/todos", produces = "text/csv")
    public void downloadTodos(Model model) {
        model.addAttribute("todos", SpreadsheetConverter.convert(findTodos.all()));
    }
}

package todoapp.data;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoIdGenerator;
import todoapp.core.todo.domain.TodoRepository;

import java.util.Objects;

@ConditionalOnProperty(name = "todoapp.data.initialize", havingValue = "true")
@Component
public class TodoDataInitializer implements InitializingBean, ApplicationRunner, CommandLineRunner {

    private final TodoIdGenerator todoIdGenerator;
    private final TodoRepository todoRepository;

    public TodoDataInitializer(final TodoIdGenerator todoIdGenerator, final TodoRepository todoRepository) {
        this.todoIdGenerator = Objects.requireNonNull(todoIdGenerator);
        this.todoRepository = Objects.requireNonNull(todoRepository);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 1. InitializingBean
        todoRepository.save(Todo.create("Task one", todoIdGenerator));
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        // 2. ApplicationRunner
        todoRepository.save(Todo.create("Task two", todoIdGenerator));
    }

    @Override
    public void run(final String... args) throws Exception {
        // 3. CommandLineRunner
        todoRepository.save(Todo.create("Task three", todoIdGenerator));
    }
}

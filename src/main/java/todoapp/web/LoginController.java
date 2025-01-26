package todoapp.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import todoapp.core.user.application.RegisterUser;
import todoapp.core.user.application.VerifyUserPassword;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserNotFoundException;
import todoapp.core.user.domain.UserPasswordNotMatchedException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionHolder;

import java.util.Objects;

@Controller
@SessionAttributes("user")
public class LoginController {

    private final VerifyUserPassword verifyUserPassword;
    private final RegisterUser registerUser;
    private final UserSessionHolder userSessionHolder;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public LoginController(final VerifyUserPassword verifyUserPassword, final RegisterUser registerUser, final UserSessionHolder userSessionHolder) {
        this.verifyUserPassword = Objects.requireNonNull(verifyUserPassword);
        this.registerUser = Objects.requireNonNull(registerUser);
        this.userSessionHolder = userSessionHolder;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@Valid LoginCommand loginCommand, BindingResult bindingResult, Model model) {
        logger.debug("request username: {}, password: {}", loginCommand.username(), loginCommand.password());

        // 1. 사용자 저장소에 사용자가 있을 경우: 비밀번호 확인 후 로그인 처리
        // 2. 사용자 저장소에 사용자가 없는 경우: 회원가입 처리 후 로그인 처리

        User user;
        try {
            user = verifyUserPassword.verify(loginCommand.username(), loginCommand.password());
        } catch (UserNotFoundException e) {
            user = registerUser.register(loginCommand.username(), loginCommand.password());
        }
        userSessionHolder.set(new UserSession(user));

        return "redirect:/todos";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException error, Model model) {
        model.addAttribute("errors", error.getBindingResult());
        model.addAttribute("message", "입력값이 없거나 올바르지 않습니다.");
        return "login";
    }

    @ExceptionHandler(UserPasswordNotMatchedException.class)
    public String handleUserPasswordNotMatchedException(UserPasswordNotMatchedException error, Model model) {
        model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
        return "login";
    }

    record LoginCommand(@Size(min = 4, max = 20) String username, String password) {
    }
}

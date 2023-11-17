package hello.login.web.login;

import hello.login.domain.login.Login;
import hello.login.domain.login.LoginService;
import hello.login.web.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

  private final LoginService loginService;


  @GetMapping("/login")
  public String loginForm(@ModelAttribute("loginForm") Login login) {

    return "login/loginForm";
  }

  @PostMapping("/login")
  public String login(@Validated @ModelAttribute("loginForm") Login login,
      BindingResult bindingResult) {

    if (login == null) {
      return "login/loginForm";
    }

    Member member = loginService.loginAct(login.getLoginId(), login.getPassword());
    log.info("login data = {}", member);
    if (member == null) {
      bindingResult.reject("loginError", "아이디 또는 비밀번호가 잘못되었습니다.");
      return "login/loginForm";
    }

    return "redirect:/";
  }

}

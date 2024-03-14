package fa.training.controller;

import fa.training.dto.ChangePassword;
import fa.training.javamail.JavaMail;
import fa.training.model.User;
import fa.training.service.UserService;
import fa.training.spring_security.PasswordGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@Controller
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMail javaMail;

    @GetMapping("/createUser")
    public String getCreateUser() {
        return "pages/user/create-user";
    }

    @PostMapping("/createUser")
    @ResponseBody
    public ResponseEntity<Object> createInterview(@RequestBody User user) {
        // xử lý thông tin candidate được gửi lên từ form

        String fullName = user.getFullName();
        String[] word = fullName.split(" ");
        StringBuffer userName = new StringBuffer();
        userName.append(word[word.length - 1]);
        for (int i = 0; i < word.length - 1; i++) {
            userName.append(word[i].toUpperCase().charAt(0));
        }

        Integer numberUserNameExist = userService.getNumberUserNameExist(userName.toString());
        if (numberUserNameExist > 0) {
            numberUserNameExist++;
            userName.append(numberUserNameExist);
        }

        user.setUserName(userName.toString());
        user.setActive(true);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = PasswordGenerate.generatePassword();
        user.setPassword(bCryptPasswordEncoder.encode(rawPassword));

        int status = userService.createUser(user);
        if (status == -1) {
            return new ResponseEntity<>("Create user failed", HttpStatus.BAD_REQUEST);
        }

        String emailContent = "<html>" +
                "<body>" +
                "<p>This email is from IMS system,</p>" +
                "<p>Your account has been created. Please use the following credential to login:</p>" +
                "<ul>" +
                "<li>User name: <b><i>" + user.getUserName() + "</i></b></li>" +
                "<li>Password: <b><i>" + rawPassword + "</i></b></li>" +
                "</ul>" +
                "<p>If anything wrong, please reach-out recruiter <a href=\"mailto:recruiter@example.com\">offer recruiter owner account</a>. We are so sorry for this inconvenience.</p>" +
                "</body>" +
                "</html>";
        try {
            javaMail.sendEmailHTML(user.getEmail(), "no-reply-email-IMS-system <Account created>", emailContent);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
        return new ResponseEntity<>("Created user successfully", HttpStatus.OK);
    }

    @GetMapping("/userList")
    public String viewListUser(Model model) {
        model.addAttribute("userList", userService.getAll());
        return "pages/user/user-list";
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateCandidate(@RequestBody User user) {
        // xử lý thông tin candidate được gửi lên từ form
        int status = userService.updateUser(user);
        if (status == -1) {
            return new ResponseEntity<>("Update candidate failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Update candidate successfully", HttpStatus.OK);
    }

    @GetMapping("/getAllRecruiter")
    public ResponseEntity<List<User>> getRecruiter() {
        List<User> userList = userService.findByRole("recruiter");
        if (userList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteUser(@PathVariable Integer id) {
        int deleteStatus = userService.deleteUser(id);
        if (deleteStatus == -1) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(deleteStatus);
    }

    @GetMapping("/changePassword")
    public String changePasswordView() {
        return "pages/user/change-password";
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public ResponseEntity<Object> changePassword(@RequestBody ChangePassword changePassword) {
        // xử lý thông tin candidate được gửi lên từ form
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(changePassword.getNewPassword()));
        int status = userService.updatePassword(user);
        if (status == -1) {
            return new ResponseEntity<>("Update password failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Update password successfully", HttpStatus.OK);
    }
}

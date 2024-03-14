package fa.training.controller;

import fa.training.dto.ForgotPassword;
import fa.training.dto.ResetPassword;
import fa.training.exception.UserNotFoundException;
import fa.training.javamail.JavaMail;
import fa.training.model.User;
import fa.training.service.UserService;
import fa.training.utility.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ForgotPasswordController {

    @Autowired
    private JavaMail javaMail;

    @Autowired
    private UserService userService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "pages/user/forgot-password";
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<String> processForgotPassword(@RequestBody ForgotPassword forgotPassword, HttpServletRequest request) {
        String email = forgotPassword.getEmail();
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>("Email not found", HttpStatus.BAD_REQUEST);
        }
        String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
        ;
        String body = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        try {
            javaMail.sendEmailHTML(email, "Here's the link to reset your password", body);
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Send email error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Send email success", HttpStatus.OK);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm() {
        return "pages/user/reset-password";
    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> processResetPassword(@RequestBody ResetPassword resetPassword) {
        String token = resetPassword.getToken();
        String password = resetPassword.getNewPassword();

        User user = userService.getByResetPasswordToken(token);

        if (user == null) {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        } else {
            user.setPassword(password);
            user.setResetPasswordToken(null);
            userService.updatePassword(user);
            return new ResponseEntity<>("Reset password successfully", HttpStatus.OK);
        }
    }

}

package com.example.yangiliklarwebsite.service;

import com.example.yangiliklarwebsite.entity.Role;
import com.example.yangiliklarwebsite.entity.Users;
import com.example.yangiliklarwebsite.entity.template.RoleConstanta;
import com.example.yangiliklarwebsite.payload.ApiRespons;
import com.example.yangiliklarwebsite.payload.LoginDto;
import com.example.yangiliklarwebsite.payload.UserRoleEditorDto;
import com.example.yangiliklarwebsite.payload.UsersDto;
import com.example.yangiliklarwebsite.repository.RoleRepository;
import com.example.yangiliklarwebsite.repository.UsersRepository;
import com.example.yangiliklarwebsite.token.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;


    public ApiRespons register(UsersDto usersDto) {
        boolean b = usersRepository.existsByUsername(usersDto.getUsername());
        if (b){
            return new ApiRespons("Bunday username ro'yxatdan o'tgan",false);
        }
        if (usersDto.getPassword().equals(usersDto.getRepassword())){
            Users users = new Users();
            users.setIsm(usersDto.getIsm());
            users.setFam(usersDto.getFam());
            users.setUsername(usersDto.getUsername());
            users.setPassword(passwordEncoder.encode(usersDto.getPassword()));
            users.setRole(roleRepository.findByNomi(RoleConstanta.USER).get());
            String code = UUID.randomUUID().toString().substring(0,6);
            users.setEmailCode(code);
            if (XabarYuborish(usersDto.getUsername(),code)){
                usersRepository.save(users);
                return new ApiRespons("Ro'yxatdan muvaffaqiyatli o'tdingiz. Xisobni faollashtirish " +
                        "uchun email pochtangizga xabar yuborildi",true);
            }
            return new ApiRespons("Ro'yxatdan o'tolmadingiz. Emailizni tekshirib ko'ring",false);
        }
        return new ApiRespons("Parollar mos emas",false);
    }

    public boolean XabarYuborish(String email,String emailCode){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setFrom("Asqar");
            simpleMailMessage.setSubject("Tasdiqlash linki:");
            simpleMailMessage.setText("<a href='http://localhost:8080/users/tasdiqlash?email="+email+"&emailCode="+emailCode+"'>EMAIL_TASDIQLASH</a>");
            javaMailSender.send(simpleMailMessage);
            return true;
        }catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }

    public ApiRespons faollashtirish(String email, String emailCode) {
        System.out.println(email + " " + emailCode);
        Optional<Users> byUsernameAndEmailCode = usersRepository.findByUsernameAndEmailCode(email, emailCode);
        if (byUsernameAndEmailCode.isPresent()){
            Users users = byUsernameAndEmailCode.get();
            users.setEnabled(true);
            users.setEmailCode(null);
            usersRepository.save(users);
            return new ApiRespons("Hisobingiz faollashtirildi",true);
        }
        return new ApiRespons("Hisob allaqachon faollashtirilgan",false);
    }

    public ApiRespons userLogin(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        if (authenticate.isAuthenticated()){
            Optional<Users> byUsernameAndEmailCode = usersRepository.findByUsernameAndEmailCode(loginDto.getUsername(), null);
            if (byUsernameAndEmailCode.isPresent()){
                Users principal = (Users) authenticate.getPrincipal();
                return new ApiRespons("Profilga xush kelibsiz" + "\t" + tokenGenerator.TokenOlish(principal.getUsername(),principal.getRole()),true);
            }
            return new ApiRespons("Akkountingiz faollashtirilmagan",false);
        }
        return new ApiRespons("Login yoki parol xato",false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> byUsername = usersRepository.findByUsername(username);
        if (byUsername.isPresent()){
            return byUsername.get();
        }
        throw new UsernameNotFoundException("Bunday foydalanuvchi topilmadi");
    }

    public ApiRespons editRole(String username, UserRoleEditorDto userRoleEditorDto) {
        Optional<Users> byUsername = usersRepository.findByUsername(username);
        if (byUsername.isPresent()){
            Users users = byUsername.get();
            Optional<Role> byNomi = roleRepository.findByNomi(userRoleEditorDto.getRoleName());
            if (byNomi.isPresent()){
                users.setRole(byNomi.get());
                usersRepository.save(users);
                return new ApiRespons("Foydalanuvchi lavozimi muvaffaqiyatli tahrirlandi",true);
            }
            return new ApiRespons("Bunday lavozim mavjud emas",false);
        }
        return new ApiRespons("Bunday foydalanuvchi mavjud emas",false);
    }

    public ApiRespons editmyinfo(Integer id, UsersDto usersDto) {
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Users> byId = usersRepository.findById(id);
        if (byId.isPresent()){
            if (users.getId().equals(id)){
                Users users1 = byId.get();
                users1.setIsm(usersDto.getIsm());
                users1.setFam(usersDto.getFam());
                users1.setPassword(usersDto.getPassword());
                usersRepository.save(users1);
                return new ApiRespons("Ma'lumotlaringiz tahrirlandi",true);
            }
            return new ApiRespons("Kechirasiz siz ushbu profilni tahrirlay olmaysiz!",false);
        }
        return new ApiRespons("Bunday foydalanuvchi mavjud emas!",false);
    }
}

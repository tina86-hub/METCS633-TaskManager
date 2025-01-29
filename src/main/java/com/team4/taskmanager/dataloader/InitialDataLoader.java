package com.team4.taskmanager.dataloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.team4.taskmanager.model.Role;
import com.team4.taskmanager.model.Task;
import com.team4.taskmanager.model.User;
import com.team4.taskmanager.service.RoleService;
import com.team4.taskmanager.service.TaskService;
import com.team4.taskmanager.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private TaskService taskService;
    private RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Value("${default.admin.mail}")
    private String defaultAdminMail;
    @Value("${default.admin.name}")
    private String defaultAdminName;
    @Value("${default.admin.password}")
    private String defaultAdminPassword;
    @Value("${default.admin.image}")
    private String defaultAdminImage;

    @Autowired
    public InitialDataLoader(UserService userService, TaskService taskService, RoleService roleService) {
        this.userService = userService;
        this.taskService = taskService;
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //ROLES --------------------------------------------------------------------------------------------------------
        roleService.createRole(new Role("ADMIN"));
        roleService.createRole(new Role("USER"));
        roleService.findAll().stream().map(role -> "saved role: " + role.getRole()).forEach(logger::info);

        //USERS --------------------------------------------------------------------------------------------------------
        //1
        User admin = new User(
                defaultAdminMail,
                defaultAdminName,
                defaultAdminPassword);
        userService.createUser(admin);
        userService.changeRoleToAdmin(admin);

        //2
        User manager = new User(
                "manager@mail.com",
                "Manager",
                "112233");
        userService.createUser(manager);
        userService.changeRoleToAdmin(manager);

        //3
        userService.createUser(new User(
                "tatum@mail.com",
                "Tatum",
                "tatum"));

        //4
        userService.createUser(new User(
                "tina@mail.com",
                "Tina",
                "tina"));

        //5
        userService.createUser(new User(
                "mikhail@mail.com",
                "Mikhail",
                "mikhail"));

        //6
        userService.createUser(new User(
                "jeffery@mail.com",
                "jeffery",
                "jeffery"));

        //7
        userService.createUser(new User(
                "tom@mail.com",
                "Tom",
                "tom"));

        userService.findAll().stream()
                .map(u -> "saved user: " + u.getName())
                .forEach(logger::info);


        //TASKS --------------------------------------------------------------------------------------------------------

        LocalDate today = LocalDate.now();

        //1
        taskService.createTask(new Task(
                "Define project scope ",
                "Setup first meeting with group for introduction. Decide the project and roles.",
                today.minusDays(16),
                true,
                userService.getUserByEmail("tatum@mail.com").getName(),
                userService.getUserByEmail("tatum@mail.com")
        ));

        //2
        taskService.createTask(new Task(
                "Define project requirements and  ",
                "Define and send project requirements to the group and instructor. Iterate on doubts you have until everybody is in agreement. Finalize project requirements.",
                today.minusDays(8),
                true,
                userService.getUserByEmail("tina@mail.com").getName(),
                userService.getUserByEmail("tina@mail.com")
        ));

        //3
        taskService.createTask(new Task(
                "Research and create Personasy",
                "Research and create Personas to define how the end user will benefit from group project.",
                today.minusDays(1),
                true,
                userService.getUserByEmail("mikhail@mail.com").getName(),
                userService.getUserByEmail("mikhail@mail.com")
        ));

        //4
        taskService.createTask(new Task(
                "Get quotation for project",
                "Get quotation for development effort for project. Estimate design work with designers.",
                today.minusDays(1),
                true,
                userService.getUserByEmail("jeffery@mail.com").getName(),
                userService.getUserByEmail("jeffery@mail.com")
        ));

        //5
        taskService.createTask(new Task(
                "Finalize  project hosting and domain",
                "Decide and finalize for hosting and domain, particularly if specialized hosting is involved such as VPS hosting, cloud hosting, or special hosting or environment requirements.",
                today.plusDays(10),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("jeffery@mail.com")
        ));

        //6
        taskService.createTask(new Task(
                "Check the quality of each content element",
                "Quality assure each part of project. Populate the website content with the various content.",
                today.minusDays(2),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("tatum@mail.com")
        ));

       



        taskService.findAll().stream().map(t -> "saved task: '" + t.getName()
                + "' for owner: " + getOwnerNameOrNoOwner(t)).forEach(logger::info);
    }

    private String getOwnerNameOrNoOwner(Task task) {
        return task.getOwner() == null ? "no owner" : task.getOwner().getName();
    }
}

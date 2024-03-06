package com.vettica.gestioncursos.controller;

import com.vettica.gestioncursos.entity.Course;
import com.vettica.gestioncursos.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    private String home(){
        return "redirect:/courses";
    }

    @GetMapping("/courses")
    public String listCourses(Model model){
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/courses/new")
    public String newCourse(Model model) {
        Course course = new Course();
        course.setActive(true);

        model.addAttribute("course", course);
        model.addAttribute("pageTitle", "Nuevo curso");

        return "course-form";
    }

    @PostMapping("/courses/save")
    public String saveCourse(Course course, RedirectAttributes redirectAttributes){
        try {
            boolean isNew = course.getId() == null;
            courseRepository.save(course);
            if (isNew) {
                redirectAttributes.addFlashAttribute("message", "El curso ha sido guardado con éxito");
            } else {
                redirectAttributes.addFlashAttribute("message", "El curso ha sido actualizado con éxito");
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            Course course = courseRepository.findById(id).get();
            model.addAttribute("pageTitle", "Editar curso: " + id);
            model.addAttribute("course", course);
            return "course-form";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/courses";
    }

    @GetMapping("courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes){
        try{
            courseRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "El curso ha sido eliminado con éxito");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/courses";
    }
}

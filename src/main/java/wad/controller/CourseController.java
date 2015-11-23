package wad.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Course;
import wad.domain.CourseType;
import wad.service.CourseService;


@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    
    @RequestMapping(method = RequestMethod.POST)
    public String add(@ModelAttribute @Valid Course course,
            BindingResult br, Model model) {
        if(br.hasErrors()) {
            model.addAttribute("coursetypes", CourseType.values());
            return "coursefail";
        }
        courseService.addCourse(course);
        return "redirect:/exams";
    }
    
}

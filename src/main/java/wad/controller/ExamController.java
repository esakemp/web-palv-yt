package wad.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Course;
import wad.domain.CourseType;
import wad.domain.Exam;
import wad.domain.ExamType;
import wad.domain.SearchDates;
import wad.service.CourseService;
import wad.service.ExamService;


@Controller
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private CourseService courseService;
    
    

    @ModelAttribute
    public Exam getExam() {
        return new Exam();
    }

    @ModelAttribute
    public Course getCourse() {
        return new Course();
    }

    @ModelAttribute
    public SearchDates getSearchDates() {
        return new SearchDates();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listAll(Model model, @ModelAttribute Exam exam, @ModelAttribute Course course) {
        model.addAttribute("exams", examService.getExams());
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("examtypes", ExamType.values());
        model.addAttribute("coursetypes", CourseType.values());
        return "index";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String listOne(@PathVariable Long id, Model model) {
        model.addAttribute("exam", examService.getExam(id));
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("examtypes", ExamType.values());
        model.addAttribute("coursetypes", CourseType.values());

        return "exam";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.PUT)
    public String save(@ModelAttribute @Valid Exam exam,
            BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("courses", courseService.getCourses());
            model.addAttribute("examtypes", ExamType.values());

            return "examfail";
        }
        examService.save(exam);

        return "redirect:/exams/";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
        examService.delete(id);
        return "redirect:/exams";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchByCourseName(@RequestParam String search, Model model) {
        model.addAttribute("exams", examService.searchByCourseName(search));
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("examtypes", ExamType.values());
        model.addAttribute("coursetypes", CourseType.values());

        return "index";
    }

    @RequestMapping(value = "/datesearch", method = RequestMethod.POST)
    public String searchByDateRange(@ModelAttribute SearchDates dates,
            BindingResult br, Model model) {

        if (br.hasErrors()) {
            model.addAttribute("courses", courseService.getCourses());
            model.addAttribute("examtypes", ExamType.values());
            return "index";
        }
        model.addAttribute("exams", examService.searchByDateRange(dates.getStart(), dates.getEnd()));
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("examtypes", ExamType.values());
        model.addAttribute("coursetypes", CourseType.values());

        return "index";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

}


package wad.domain;


public enum CourseType {
    Basic("Perusopinnot"), 
    Advanced("Syventävät opinnot"), 
    Subject("Aineopinnot");
    
    private final String name;
    
    private CourseType(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}

package web.engineering.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StudentBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String faculty;
	private String facultyNumber;
	private String educationType;

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getFacultyNumber() {
		return facultyNumber;
	}

	public void setFacultyNumber(String facultyNumber) {
		this.facultyNumber = facultyNumber;
	}

	public String getEducationType() {
		return educationType;
	}

	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}

}

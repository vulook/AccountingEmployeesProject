package edu.cbsystematics.com.accountingemployeesproject.config;

import edu.cbsystematics.com.accountingemployeesproject.model.Department;
import edu.cbsystematics.com.accountingemployeesproject.model.Employee;
import edu.cbsystematics.com.accountingemployeesproject.model.Period;
import edu.cbsystematics.com.accountingemployeesproject.model.Position;
import edu.cbsystematics.com.accountingemployeesproject.service.DepartmentService;
import edu.cbsystematics.com.accountingemployeesproject.service.EmployeeService;
import edu.cbsystematics.com.accountingemployeesproject.service.PeriodService;
import edu.cbsystematics.com.accountingemployeesproject.service.PositionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class DatabaseInitializer {

    private final DepartmentService departmentService;

    private final PositionService positionService;

    private final EmployeeService employeeService;

    private final PeriodService periodService;

    @Autowired
    public DatabaseInitializer(DepartmentService departmentService, PositionService positionService, EmployeeService employeeService, PeriodService periodService) {
        this.departmentService = departmentService;
        this.positionService = positionService;
        this.employeeService = employeeService;
        this.periodService = periodService;
    }


    @PostConstruct
    public void init() {

        // Creating and Saving the Department in the database
        Department noneDepartment = new Department("NONE");
        Department sales = new Department("Sales department");
        Department marketing = new Department("Marketing department ");
        Department customerService = new Department("Customer service department");
        Department humanResources = new Department("Human resources department");
        Department informationTechnology = new Department("Information technology department");
        Department engineering = new Department("Engineering department");
        Department researchAndDevelopment = new Department("Research and development department");

        departmentService.saveDepartment(noneDepartment);
        departmentService.saveDepartment(sales);
        departmentService.saveDepartment(marketing);
        departmentService.saveDepartment(customerService);
        departmentService.saveDepartment(humanResources);
        departmentService.saveDepartment(informationTechnology);
        departmentService.saveDepartment(engineering);
        departmentService.saveDepartment(researchAndDevelopment);

        // Creating and Saving the Position in the database
        Position nonePosition = new Position("NONE");
        Position juniorSales = new Position("Junior Sales Representative");
        Position seniorSales = new Position("Senior Sales Representative");
        Position leadSales = new Position("Lead Sales Representative");
        Position juniorMarketing = new Position("Junior Marketing Associate");
        Position seniorMarketing = new Position("Senior Marketing Associate");
        Position leadMarketing = new Position("Lead Marketing Associate");
        Position juniorCustomer = new Position("Junior Customer Service Representative");
        Position seniorCustomer = new Position("Senior Customer Service Representative");
        Position leadCustomer = new Position("Lead Customer Service Representative");
        Position juniorHuman = new Position("Junior Human Resources Associate");
        Position seniorHuman = new Position("Senior Human Resources Associate");
        Position leadHuman = new Position("Lead Human Resources Associate");
        Position juniorIT = new Position("Junior Information Technology Support Specialist");
        Position seniorIT = new Position("Senior Information Technology Support Specialist");
        Position leadIT = new Position("Lead Information Technology Support Specialist");
        Position juniorEngineering = new Position("Junior Engineering Associate");
        Position seniorEngineering = new Position("Senior Engineering Associate");
        Position leadEngineering = new Position("Lead Engineering Associate");
        Position juniorResearch = new Position("Junior Research and development Specialist");
        Position seniorResearch = new Position("Senior Research and development Specialist");
        Position leadResearch = new Position("Lead Research and development Specialist");

        positionService.savePosition(nonePosition);
        positionService.savePosition(juniorSales);
        positionService.savePosition(seniorSales);
        positionService.savePosition(leadSales);
        positionService.savePosition(juniorMarketing);
        positionService.savePosition(seniorMarketing);
        positionService.savePosition(leadMarketing);
        positionService.savePosition(juniorCustomer);
        positionService.savePosition(seniorCustomer);
        positionService.savePosition(leadCustomer);
        positionService.savePosition(juniorHuman);
        positionService.savePosition(seniorHuman);
        positionService.savePosition(leadHuman);
        positionService.savePosition(juniorIT);
        positionService.savePosition(seniorIT);
        positionService.savePosition(leadIT);
        positionService.savePosition(juniorEngineering);
        positionService.savePosition(seniorEngineering);
        positionService.savePosition(leadEngineering);
        positionService.savePosition(juniorResearch);
        positionService.savePosition(seniorResearch);
        positionService.savePosition(leadResearch);

        // Creating and Saving the Employee in the database
        Employee employee01 = new Employee("George", "Orwell", LocalDate.of(1972, 1, 15), "employee01@example.com", "+38097559012");
        Employee employee02 = new Employee("John", "Doe", LocalDate.of(1985, 5, 20), "employee02@example.com", "+38097559013");
        Employee employee03 = new Employee("Jane", "Doe", LocalDate.of(1990, 8, 10), "employee03@example.com", "+38097559014");
        Employee employee04 = new Employee("Alice", "Johnson", LocalDate.of(1988, 3, 25), "employee04@example.com", "+38097559015");
        Employee employee05 = new Employee("Bob", "Smith", LocalDate.of(1995, 7, 5), "employee05@example.com", "+38097559016");
        Employee employee06 = new Employee("Emma", "Jones", LocalDate.of(1989, 9, 12), "employee06@example.com", "+38097559017");
        Employee employee07 = new Employee("Michael", "Davis", LocalDate.of(1993, 12, 8), "employee07@example.com", "+38097559018");
        Employee employee08 = new Employee("Sophia", "Brown", LocalDate.of(1987, 2, 18), "employee08@example.com", "+38097559019");
        Employee employee09 = new Employee("Daniel", "Clark", LocalDate.of(1990, 4, 30), "employee09@example.com", "+38097559020");
        Employee employee10 = new Employee("Olivia", "Taylor", LocalDate.of(1986, 6, 22), "employee10@example.com", "+38097559021");
        Employee employee11 = new Employee("Liam", "Miller", LocalDate.of(1992, 10, 3), "employee11@example.com", "+38097559022");
        Employee employee12 = new Employee("Ava", "White", LocalDate.of(1984, 11, 14), "employee12@example.com", "+38097559023");
        Employee employee13 = new Employee("Noah", "Anderson", LocalDate.of(1996, 2, 7), "employee13@example.com", "+38097559024");
        Employee employee14 = new Employee("Isabella", "Wilson", LocalDate.of(1985, 4, 19), "employee14@example.com", "+38097559025");
        Employee employee15 = new Employee("Mason", "Moore", LocalDate.of(1991, 8, 28), "employee15@example.com", "+38097559026");
        Employee employee16 = new Employee("Sophie", "Harris", LocalDate.of(1988, 1, 1), "employee16@example.com", "+38097559027");
        Employee employee17 = new Employee("Logan", "Martin", LocalDate.of(1994, 3, 9), "employee17@example.com", "+38097559028");
        Employee employee18 = new Employee("Ella", "Thompson", LocalDate.of(1986, 5, 17), "employee18@example.com", "+38097559029");
        Employee employee19 = new Employee("Ethan", "Walker", LocalDate.of(1993, 7, 26), "employee19@example.com", "+38097559030");
        Employee employee20 = new Employee("Avery", "Hall", LocalDate.of(1989, 9, 4), "employee20@example.com", "+38097559031");
        Employee employee21 = new Employee("Jackson", "Turner", LocalDate.of(1995, 11, 11), "employee21@example.com", "+38097559032");
        Employee employee22 = new Employee("Madison", "Wright", LocalDate.of(1987, 12, 22), "employee22@example.com", "+38097559033");
        Employee employee23 = new Employee("Bacon", "Wolfe", LocalDate.of(2002, 9, 8), "employee23@example.com", "+38097559034");
        Employee employee24 = new Employee("Anthony", "Newton", LocalDate.of(2002, 12, 1), "employee24@example.com", "+38097559035");

        employeeService.saveEmployee(employee01);
        employeeService.saveEmployee(employee02);
        employeeService.saveEmployee(employee03);
        employeeService.saveEmployee(employee04);
        employeeService.saveEmployee(employee05);
        employeeService.saveEmployee(employee06);
        employeeService.saveEmployee(employee07);
        employeeService.saveEmployee(employee08);
        employeeService.saveEmployee(employee09);
        employeeService.saveEmployee(employee10);
        employeeService.saveEmployee(employee11);
        employeeService.saveEmployee(employee12);
        employeeService.saveEmployee(employee13);
        employeeService.saveEmployee(employee14);
        employeeService.saveEmployee(employee15);
        employeeService.saveEmployee(employee16);
        employeeService.saveEmployee(employee17);
        employeeService.saveEmployee(employee18);
        employeeService.saveEmployee(employee19);
        employeeService.saveEmployee(employee20);
        employeeService.saveEmployee(employee21);
        employeeService.saveEmployee(employee22);
        employeeService.saveEmployee(employee23);
        employeeService.saveEmployee(employee24);

        // Creating and Saving the Periods in the database
        periodService.savePeriod(new Period(LocalDate.of(2006, 6, 1), LocalDate.of(2008, 10, 23), sales, juniorSales, employee01));
        periodService.savePeriod(new Period(LocalDate.of(2008, 10, 23), LocalDate.of(2010, 1, 5), marketing, juniorMarketing, employee01));
        periodService.savePeriod(new Period(LocalDate.of(2011, 5, 1), null, engineering, seniorEngineering, employee01));

        periodService.savePeriod(new Period(LocalDate.of(2005, 1, 30), LocalDate.of(2010, 10, 23), informationTechnology, juniorIT, employee02));
        periodService.savePeriod(new Period(LocalDate.of(2010, 10, 23), LocalDate.of(2018, 6, 15), informationTechnology, seniorIT, employee02));
        periodService.savePeriod(new Period(LocalDate.of(2018, 7, 1), null, informationTechnology, leadIT, employee02));

        periodService.savePeriod(new Period(LocalDate.of(2005, 5, 30), LocalDate.of(2011, 12, 22), humanResources, juniorHuman, employee03));
        periodService.savePeriod(new Period(LocalDate.of(2011, 12, 28), LocalDate.of(2020, 3, 15), humanResources, seniorHuman, employee03));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee03));

        periodService.savePeriod(new Period(LocalDate.of(2011, 1, 30), LocalDate.of(2014, 10, 22), humanResources, juniorHuman, employee04));
        periodService.savePeriod(new Period(LocalDate.of(2014, 10, 23), LocalDate.of(2021, 3, 15), humanResources, seniorHuman, employee04));
        periodService.savePeriod(new Period(LocalDate.of(2021, 3, 10), null, humanResources, leadHuman, employee04));

        periodService.savePeriod(new Period(LocalDate.of(2005, 1, 30), LocalDate.of(2006, 10, 22), humanResources, juniorHuman, employee05));
        periodService.savePeriod(new Period(LocalDate.of(2006, 10, 23), LocalDate.of(2008, 3, 15), humanResources, seniorHuman, employee05));
        periodService.savePeriod(new Period(LocalDate.of(2008, 3, 10), null, humanResources, leadHuman, employee05));

        periodService.savePeriod(new Period(LocalDate.of(2011, 1, 30), LocalDate.of(2011, 10, 22), humanResources, juniorHuman, employee06));
        periodService.savePeriod(new Period(LocalDate.of(2012, 10, 23), LocalDate.of(2021, 3, 15), humanResources, seniorHuman, employee06));
        periodService.savePeriod(new Period(LocalDate.of(2021, 3, 10), null, humanResources, leadHuman, employee06));

        periodService.savePeriod(new Period(LocalDate.of(2010, 12, 1), LocalDate.of(2013, 10, 22), humanResources, juniorHuman, employee07));
        periodService.savePeriod(new Period(LocalDate.of(2013, 10, 23), LocalDate.of(2020, 3, 15), humanResources, seniorHuman, employee07));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee07));

        periodService.savePeriod(new Period(LocalDate.of(2005, 1, 30), LocalDate.of(2010, 10, 22), humanResources, juniorHuman, employee08));
        periodService.savePeriod(new Period(LocalDate.of(2010, 10, 23), LocalDate.of(2023, 6, 15), humanResources, seniorHuman, employee08));
        periodService.savePeriod(new Period(LocalDate.of(2023, 6, 16), null, humanResources, leadHuman, employee08));

        periodService.savePeriod(new Period(LocalDate.of(2006, 1, 30), LocalDate.of(2010, 10, 22), humanResources, juniorHuman, employee09));
        periodService.savePeriod(new Period(LocalDate.of(2010, 10, 23), LocalDate.of(2020, 3, 15), humanResources, seniorHuman, employee09));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee09));

        periodService.savePeriod(new Period(LocalDate.of(2007, 1, 30), LocalDate.of(2010, 10, 22), humanResources, juniorHuman, employee10));
        periodService.savePeriod(new Period(LocalDate.of(2010, 10, 23), LocalDate.of(2020, 3, 15), humanResources, seniorHuman, employee10));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee10));

        periodService.savePeriod(new Period(LocalDate.of(2008, 1, 30), LocalDate.of(2010, 10, 22), humanResources, juniorHuman, employee11));
        periodService.savePeriod(new Period(LocalDate.of(2010, 10, 23), LocalDate.of(2020, 3, 15), humanResources, seniorHuman, employee11));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee11));

        periodService.savePeriod(new Period(LocalDate.of(2009, 1, 30), LocalDate.of(2010, 10, 22), humanResources, juniorHuman, employee12));
        periodService.savePeriod(new Period(LocalDate.of(2010, 10, 23), LocalDate.of(2020, 3, 15), humanResources, seniorHuman, employee12));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee12));

        periodService.savePeriod(new Period(LocalDate.of(2010, 1, 30), LocalDate.of(2010, 10, 22), humanResources, juniorHuman, employee13));
        periodService.savePeriod(new Period(LocalDate.of(2010, 10, 23), LocalDate.of(2020, 3, 15), humanResources, seniorHuman, employee13));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee13));

        periodService.savePeriod(new Period(LocalDate.of(2005, 1, 30), LocalDate.of(2010, 10, 22), humanResources, juniorHuman, employee14));
        periodService.savePeriod(new Period(LocalDate.of(2010, 10, 23), LocalDate.of(2020, 3, 15), humanResources, seniorHuman, employee14));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee14));

        periodService.savePeriod(new Period(LocalDate.of(2014, 1, 30), LocalDate.of(2019, 10, 22), humanResources, juniorHuman, employee15));
        periodService.savePeriod(new Period(LocalDate.of(2019, 10, 23), LocalDate.of(2020, 3, 15), humanResources, seniorHuman, employee15));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee15));

        periodService.savePeriod(new Period(LocalDate.of(2015, 1, 30), LocalDate.of(2020, 3, 22), humanResources, juniorHuman, employee16));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee16));

        periodService.savePeriod(new Period(LocalDate.of(2016, 1, 30), LocalDate.of(2018, 10, 22), humanResources, juniorHuman, employee17));
        periodService.savePeriod(new Period(LocalDate.of(2018, 10, 23), LocalDate.of(2023, 3, 15), humanResources, seniorHuman, employee17));
        periodService.savePeriod(new Period(LocalDate.of(2023, 3, 19), null, humanResources, leadHuman, employee17));

        periodService.savePeriod(new Period(LocalDate.of(2017, 1, 30), LocalDate.of(2020, 10, 20), humanResources, juniorHuman, employee18));
        periodService.savePeriod(new Period(LocalDate.of(2020, 10, 23), LocalDate.of(2020, 3, 15), humanResources, seniorHuman, employee18));
        periodService.savePeriod(new Period(LocalDate.of(2020, 4, 10), null, humanResources, leadHuman, employee18));

        periodService.savePeriod(new Period(LocalDate.of(2017, 8, 30), LocalDate.of(2021, 10, 22), humanResources, juniorHuman, employee19));
        periodService.savePeriod(new Period(LocalDate.of(2021, 11, 1), null, humanResources, leadHuman, employee19));

        periodService.savePeriod(new Period(LocalDate.of(2018, 1, 30), LocalDate.of(2018, 12, 15), humanResources, juniorHuman, employee20));
        periodService.savePeriod(new Period(LocalDate.of(2018, 12, 23), LocalDate.of(2024, 1, 8), humanResources, seniorHuman, employee20));
        periodService.savePeriod(new Period(LocalDate.of(2024, 1, 10), null, humanResources, leadHuman, employee20));

        periodService.savePeriod(new Period(LocalDate.of(2019, 1, 30), LocalDate.of(2020, 10, 1), humanResources, juniorHuman, employee21));
        periodService.savePeriod(new Period(LocalDate.of(2020, 10, 23), LocalDate.of(2024, 1, 12), humanResources, seniorHuman, employee21));
        periodService.savePeriod(new Period(LocalDate.of(2024, 1, 20), null, humanResources, leadHuman, employee21));

        periodService.savePeriod(new Period(LocalDate.of(2017, 1, 30), LocalDate.of(2020, 3, 1), humanResources, juniorHuman, employee22));
        periodService.savePeriod(new Period(LocalDate.of(2020, 3, 10), null, humanResources, leadHuman, employee22));

        periodService.savePeriod(new Period(LocalDate.of(2018, 1, 30), LocalDate.of(2021, 10, 22), customerService, seniorCustomer, employee23));
        periodService.savePeriod(new Period(LocalDate.of(2021, 10, 23), null, customerService, leadCustomer, employee23));

        periodService.savePeriod(new Period(LocalDate.of(2020, 1, 30), LocalDate.of(2020, 2, 2), researchAndDevelopment, juniorResearch, employee24));
        periodService.savePeriod(new Period(LocalDate.of(2020, 2, 21), null, researchAndDevelopment, leadResearch, employee24));
    }

}
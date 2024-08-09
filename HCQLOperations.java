package HCQLDemo;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class HCQLOperations 
{
   public static void main(String args[])
   {
	   HCQLOperations operations = new HCQLOperations(); 
	  //operations.addStudent();
	  //operations.restrictionsdemo();
	  //operations.orderdemo();
	  //operations.aggregatefunctions();
	   operations.hcqldemo(); // use case
   }
   // add student by using Persistent Object(PO)
   public void addStudent()
   {
	   Configuration configuration = new Configuration();
   	   configuration.configure("hibernate.cfg.xml");
   	   
   	   SessionFactory sf = configuration.buildSessionFactory();
   	   Session session = sf.openSession();
   	
       Transaction t =  session.beginTransaction();
   	
   	    Student student = new Student();
   	    student.setId(104);
   	    student.setName("MSWD");
   	    student.setGender("FEMALE");
   	    student.setAge(50.5);   	    
   	    student.setDepartment("ECE");
   	    student.setEmail("mswd@gmail.com");
   	    student.setContact("7890763667");
   	
   	    session.persist(student);
   	    t.commit();
   	    
   	    System.out.println("Student Added Successfully");
   	
      	session.close();
   	    sf.close();
   }
   public void restrictionsdemo()
   {   	   
   	   CriteriaBuilder cb = session.getCriteriaBuilder();
	   CriteriaQuery<Student> cq = cb.createQuery(Student.class);
	   // from Student; [Complete Object]
	   Root<Student> root = cq.from(Student.class);
	   
           cq.select(root).where(cb.equal(root.get("gender"), "FEMALE"));
	   //cq.select(root).where(cb.lessThan(root.get("age"), 30));
	   //cq.select(root).where(cb.greaterThan(root.get("age"), 40));
	   //cq.select(root).where(cb.le(root.get("age"), 50)); // less than or equal to
	   //cq.select(root).where(cb.ge(root.get("age"), 40)); //greater than or equal to
	   //cq.select(root).where(cb.notEqual(root.get("department"), "CSE")); //not equal to
	   //cq.select(root).where(cb.between(root.get("age"), 20, 50)); // between 20 and 50	   
	   
	   // not with any existing criteria
	   //cq.select(root).where(cb.not(cb.equal(root.get("department"), "ECE")));
	   
	   List<String> depts = Arrays.asList("CSE","ECE","ME");
	   cq.select(root).where(root.get("department").in(depts)); // set of values
	   
	   List<Student> students =  session.createQuery(cq).getResultList();
	   System.out.println("Students Count="+students.size());
	   for(Student s : students)
	   {
		   // use getter methods to print every property in Student object (s)
		   System.out.println(s.toString());  // Generate toString() method in POJO Class
	   }
   	   session.close();
   	   sf.close();   
   }
   public void orderdemo() // ascending / descending
   {
	   Configuration configuration = new Configuration();
   	   configuration.configure("hibernate.cfg.xml");
   	   
   	   SessionFactory sf = configuration.buildSessionFactory();
   	   Session session = sf.openSession();
   	   
       CriteriaBuilder cb = session.getCriteriaBuilder();
	   CriteriaQuery<Student> cq = cb.createQuery(Student.class);
	   Root<Student> root = cq.from(Student.class);
	   
	   // ascending order based on age
	   //cq.orderBy(cb.asc(root.get("age")));
	   
	   //descending order based on name
	   cq.orderBy(cb.desc(root.get("name")));
	   
	   System.out.println("****Order by Demo****");
	   List<Student> students =  session.createQuery(cq).getResultList();
	   System.out.println("Students Count="+students.size());
	   for(Student s : students)
	   {
		   System.out.println(s.toString());
	   }
	   
	   session.close();
	   sf.close();
   }

}

package com.studentmanagement;

public class InvalidMarksException extends Exception
{
	public InvalidMarksException()
	{
		super();
	}
	
	public InvalidMarksException(String message)
	{
		super(message);
	}
}


package com.studentmanagement;


import java.text.DecimalFormat;

public class Student 
{
	private String studentName;
	private String rollNumber;
	private int[] marks;
	private double percentage;
	private String grade;
	private String percentageString; // percentage in String format
	
	public Student(String studentName, String rollNumber, int mathsMarks, int physicsMarks, int chemistryMarks)
	{
		super();
		this.studentName = studentName;
		this.rollNumber = rollNumber;
		this.marks = new int[] {mathsMarks, physicsMarks, chemistryMarks};
		this.calculatePercentage();
		this.assignGrade();
	}
	
	public void setStudentName(String name)
	{
		this.studentName = name;
	}

	public String getStudentName()
	{
		return this.studentName;
	}
	
	public void setRollNumber(String rollNumber)
	{
		this.rollNumber = rollNumber;
	}
	
	public String getRollNumber()
	{
		return this.rollNumber;
	}
	
	public void setMarks(int[] marks)
	{
		this.marks = marks;
		this.calculatePercentage();
		this.assignGrade();
	}
	
	public int[] getMarks()
	{
		return this.marks;
	}
		
	public double getPercentage()
	{
		return this.percentage;
	}
		
	public String getGrade()
	{
		return this.grade;
	}
	
	@Override
	public String toString()
	{
		return "Student [name = "+this.studentName+", rollNumber = "+this.rollNumber+
", mathsMarks = "+this.marks[0]+", physicsMarks ="+this.marks[1]+", chemistryMarks = " +this.marks[2]+", percentage = "+this.percentageString+"%, grade = "+this.grade+"]";
	}
	
	public void calculatePercentage()
	{
		int totalMarks = 0;
		
		for(int score : this.marks)
		{
			totalMarks += score;
		}
		
		this.percentage = totalMarks/(double)this.marks.length;
		
		DecimalFormat df = new DecimalFormat("00.0");
		this.percentageString = df.format(this.percentage);
	}
	
	public void assignGrade()
	{
		if(this.percentage>=90)
		{
			this.grade = "A+";
		}
		else if(this.percentage>=80 && this.percentage<90)
		{
			this.grade = "A";
		}
		else if(this.percentage>=70 && this.percentage<80)
		{
			this.grade = "B+";
		}
		else if(this.percentage>=60 && this.percentage<70)
		{
			this.grade = "B";
		}
		else if(this.percentage>=55 && this.percentage<60)
		{
			this.grade = "C+";
		}
		else if(this.percentage>=45 && this.percentage<55)
		{
			this.grade = "C";
		}
		else if(this.percentage>=40 && this.percentage<45)
		{
			this.grade = "D";
		}
		else
		{
			this.grade="F";		}
	}
}


package com.studentmanagement;

import java.util.Scanner;

public class StudentManagementSystem 
{
	static
	{
		System.out.println("Student Management System");
		System.out.println("-------------------------");
	}
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of student records you want to store :");//record size
		int size = 0;
		
		try
		{
			size = Integer.parseInt(sc.nextLine());
		}
		catch(NumberFormatException e)
		{
			System.err.println("Error : Only integers are allowed here");
			sc.close();
			return;
		}

		StudentPortal portal = new StudentPortal(size); 

		while(true)
		{
			System.out.println("Menu");
			System.out.println("1. Add student record");
			System.out.println("2. Update student record");
			System.out.println("3. Delete student record");
			System.out.println("4. Display student records");
			System.out.println("5. Quit");
			
			System.out.println("Enter your choice :");
			try
			{			
				int choice = Integer.parseInt(sc.nextLine());
				
				switch(choice)
				{
					case 1 : System.out.println("Enter student name :"); // user input
					 		 String name = sc.nextLine();
					 		 System.out.println("Enter student roll number :");
					 		 String rollNumber = sc.nextLine();
					 		 System.out.println("Enter 1st subject marks :");
					 		 int marks1 = Integer.parseInt(sc.nextLine());
					 		 portal.validateMarks(marks1); // validates marks
					 		 System.out.println("Enter 2nd subject marks :");
					 		 int marks2 = Integer.parseInt(sc.nextLine());
					 		 portal.validateMarks(marks2);
					 		 System.out.println("Enter 3rd subject marks :");
					 		 int marks3 = Integer.parseInt(sc.nextLine());
					 		 portal.validateMarks(marks3);
					 		 
					 		 boolean result = portal.addStudent(name, rollNumber, marks1, marks2, marks3);
					 		 if(result)
					 		 {
					 			 System.out.println("Student record inserted successfully");
					 		 }
					 		 else
					 		 {
					 			 System.err.println("Sorry! Student file is full. Cannot insert new student.");
					 		 }
					 		 break;
					 		 
					case 2 : System.out.println("What do you want to update ?");
					 		 System.out.println("a. Name of the student");
					 		 System.out.println("b. Marks");
					 		 
					 		 System.out.println("Enter your choice :");
					 		 String choiceChar = sc.nextLine();
					 		 
					 		 switch(choiceChar)
					 		 {
					 		 	case "a" : System.out.println("Enter roll number of the student :");
					 		 	 		   rollNumber = sc.nextLine();
					 		 	 		   System.out.println("Enter updated student name :");
					 		 	 		   name = sc.nextLine();
					 		 	 		   
					 		 	 		   result = portal.updateStudent(rollNumber, name);
					 		 	 		   if(result)
					 		 	 		   {
					 		 	 			   System.out.println("Record updated successfully");
					 		 	 		   }
					 		 	 		   else
					 		 	 		   {
					 		 	 			   if(portal.isEmpty())
					 		 	 			   {
					 		 	 				   System.err.println("Sorry! Nothing to update. Student file is empty");
					 		 	 			   }
					 		 	 			   else if(!portal.isStudentPresent(rollNumber))
					 		 	 			   {
					 		 	 				   System.err.println("Sorry! Student record is not available in the file");
					 		 	 			   }
					 		 	 		   }
					 		 	 		   break;
					 		 	 		   
					 		 	case "b" : System.out.println("Enter roll number of the student :");
					 		 	 		   rollNumber = sc.nextLine();
					 		 	 		   System.out.println("Enter updated 1st subject marks :");
					 		 	 		   marks1 = Integer.parseInt(sc.nextLine());
					 		 	 		   portal.validateMarks(marks1);
					 		 	 		   System.out.println("Enter updated 2nd subject marks :");
					 		 	 		   marks2 = Integer.parseInt(sc.nextLine());
					 		 	 		   portal.validateMarks(marks2);
					 		 	 		   System.out.println("Enter updated 3rd subject marks :");
					 		 	 		   marks3 = Integer.parseInt(sc.nextLine());
					 		 	 		   portal.validateMarks(marks3);
					 		 	 		   
					 		 	 		   result = portal.updateStudent(rollNumber, marks1, marks2, marks3);
					 		 	 		   if(result)
					 		 	 		   {
					 		 	 			   System.out.println("Student record updated");
					 		 	 		   }
					 		 	 		   else
					 		 	 		   {
					 		 	 			   if(portal.isEmpty())
					 		 	 			   {
					 		 	 				   System.err.println("Sorry! Nothing to update. Student file is empty");
					 		 	 			   }
					 		 	 			   else if(!portal.isStudentPresent(rollNumber))
					 		 	 			   {
					 		 	 				   System.err.println("Sorry! Student record is not available in the file");
					 		 	 			   }
					 		 	 		   }
					 		 	 		   break;
					 		 	 		   
					 		 	 default : System.err.println("Invalid choice");
					 		 }
					 		 break;
					 		 
					case 3 : System.out.println("Enter roll number of student you want to delete :");
							 rollNumber = sc.nextLine();
							 result = portal.deleteStudent(rollNumber);
							 if(result)
							 {
								 System.out.println("Student record deleted successfully");
							 }
							 else
							 {
		 		 	 			   if(portal.isEmpty())
		 		 	 			   {
		 		 	 				   System.err.println("Sorry! Nothing to delete. Student file is empty");
		 		 	 			   }
		 		 	 			   else if(!portal.isStudentPresent(rollNumber))
		 		 	 			   {
		 		 	 				   System.err.println("Sorry! Student record is not available in the file");
		 		 	 			   }							 
		 		 	 		}
							 break;
							 
					case 4 : portal.displayStudents(); 
							 break;
					
					case 5 : System.out.println("Thankyou!!! visit again");
					 		 sc.close();
					 		 System.exit(0);
					 
					default : System.err.println("Invalid choice");
				}			
			}
			catch(NumberFormatException e)
			{
				System.err.println("Error : Only integers are allowed here");
			}
			catch(InvalidMarksException e)
			{
				System.err.println("Error : "+e.getMessage());
			}	
			System.out.println();
		}
	}
}



package com.studentmanagement;


public class StudentPortal 
{
	private Student[] students;
	private int studentCount;
	
	public StudentPortal(int size)
	{
		super();
		this.students = new Student[size];
	}
	
	public void validateMarks(int marks) throws InvalidMarksException
	{
		if(marks<0 || marks>100)
		{
			throw new InvalidMarksException("Marks cannot be negative or greater than zero");
		}
	}
	
	public boolean addStudent(String studentName, String rollNumber, int marks1, int marks2, int marks3)
	{
		if(this.isFull())
		{
			return false;
		}
		else
		{
			this.students[this.studentCount++] = new Student(studentName, rollNumber, marks1, marks2, marks3);
			return true;
		}
	}

	public boolean isFull()
	{
		if(this.studentCount==this.students.length)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean updateStudent(String rollNumber, String studentName)
	{
		if(this.isStudentPresent(rollNumber))
		{
			for(Student student : this.students)
			{
				if(student.getRollNumber().equals(rollNumber))
				{
					student.setStudentName(studentName);
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean updateStudent(String rollNumber, int marks1, int marks2, int marks3)
	{
		if(this.isStudentPresent(rollNumber))
		{
			int marks[] = new int[] {marks1, marks2, marks3};
			
			for(Student student : this.students)
			{
				if(student.getRollNumber().equals(rollNumber))
				{
					student.setMarks(marks);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean isStudentPresent(String rollNumber)
	{
		if(!this.isEmpty())
		{
			for(Student student : this.students)
			{
				if(student.getRollNumber().equals(rollNumber))
				{
					return true;
				}
			}			
		}
		return false;
	}
	
	public boolean deleteStudent(String rollNumber)
	{
		if(this.isStudentPresent(rollNumber))
		{
			Student newStudents[] = new Student[this.students.length-1];
			
			int index = 0;
			for(Student student : this.students)
			{
				if(!student.getRollNumber().equals(rollNumber))
				{
					newStudents[index++] = student;
				}
			}
			this.students = newStudents;
			this.studentCount--; // one student record deleted 
			return true;
		}
		return false;
	}
	
	public boolean isEmpty()
	{
		if(this.studentCount==0)
		{
			return true;
		}
		return false;
	}
	
	public void displayStudents()
	{
		if(!this.isEmpty())
		{
			System.out.println("Student records :");
			
			for(Student student : this.students)
			{
				System.out.println(student);
			}
		}
		else
		{
			System.err.println("No student records inserted");
		}
	}
}

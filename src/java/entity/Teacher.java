/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author hxy_zuki
 */
public class Teacher {
   public void offer() {
       // teacher can offer class
       // setting time, course name
       // generate course id
       // connection to sql and update sql
   } 
   
   public void uploadScore(int[] scores, String c_name) {
       // teacher can upload student score
       // search for offered course
       // get input from GUI
   }
   
   public void updateScore(int score, String stu_name) {
       // if the student retest, teacher should update score
   }
   
   public void deleteCourse(String c_name) {
       // delete the course c_name if no one choose the course
   }
}

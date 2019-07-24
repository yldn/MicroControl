/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liuyang
 */
public class Relatives {
        
        Relatives rel;
        private String name;
        private String relation;
        private int age;

        public String getName() {
           return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Relatives(String name, String relation, int age) {
            super();
            this.name = name;
            this.relation = relation;
            this.age = age;
        }
    }


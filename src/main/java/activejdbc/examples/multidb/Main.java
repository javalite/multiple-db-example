/*
Copyright 2009-2010 Igor Polevoy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package activejdbc.examples.multidb;

import org.javalite.activejdbc.DB;

public class Main {
    public static void main(String[] args) {
        DB corpDb = new DB("corporation");
        DB univDb = new DB("university");
        try {
            corpDb.open("org.h2.Driver", "jdbc:h2:mem:corp;DB_CLOSE_DELAY=-1", "sa", "");
            corpDb.exec("DROP TABLE IF EXISTS employees");
            corpDb.exec("CREATE TABLE employees (id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(56), last_name VARCHAR(56))");

            univDb.open("org.h2.Driver", "jdbc:h2:mem:univ;DB_CLOSE_DELAY=-1", "sa", "");
            univDb.exec("DROP TABLE IF EXISTS students");
            univDb.exec("CREATE TABLE students (id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, first_name VARCHAR(56), last_name VARCHAR(56))");

            Employee.deleteAll();
            Student.deleteAll();

            Employee.createIt("first_name", "John", "last_name", "Doe");
            Employee.createIt("first_name", "Jane", "last_name", "Smith");

            Student.createIt("first_name", "Mike", "last_name", "Myers");
            Student.createIt("first_name", "Steven", "last_name", "Spielberg");

            System.out.println("*** Employees ***");
            Employee.findAll().dump();
            System.out.println("*** Students ***");
            Student.findAll().dump();
        } finally {
            corpDb.close();
            univDb.close();
        }
    }
}

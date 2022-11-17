/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

public abstract class Person {
    @GetterFunctionName(name="getID")
    protected String ID;
    @GetterFunctionName(name="getName")
    protected String name;


    public String getID(){
        return this.ID;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
